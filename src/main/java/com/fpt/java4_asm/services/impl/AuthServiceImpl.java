package com.fpt.java4_asm.services.impl;

import com.fpt.java4_asm.dto.request.LoginRequest;
import com.fpt.java4_asm.dto.request.RegisterRequest;
import com.fpt.java4_asm.dto.request.ChangePasswordRequest;
import com.fpt.java4_asm.dto.response.LoginResponse;
import com.fpt.java4_asm.dto.response.UserResponse;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.models.entities.User;
import com.fpt.java4_asm.repositories.UserRepo;
import com.fpt.java4_asm.repositories.impl.UserRepoImpl;
import com.fpt.java4_asm.services.AuthService;
import com.fpt.java4_asm.services.UserService;
import com.fpt.java4_asm.utils.constants.ApiConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Triển khai cụ thể của AuthService
 * Xử lý các nghiệp vụ xác thực: đăng nhập, đăng ký, đổi mật khẩu, quản lý token
 * 
 * Token được lưu trong memory (ConcurrentHashMap) với thời hạn 24 giờ
 * Mỗi user chỉ có 1 token active tại một thời điểm
 * 
 * @author Java4 ASM
 */
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    
    private final UserRepo userRepo = new UserRepoImpl();
    private final UserService userService = new UserServiceImpl();
    
    /**
     * Inner class lưu thông tin token
     * Bao gồm userId và thời gian tạo để kiểm tra hết hạn
     */
    private static class TokenInfo {
        final String userId;
        final long createdAt;
        
        TokenInfo(String userId) {
            this.userId = userId;
            this.createdAt = System.currentTimeMillis();
        }
        
        /**
         * Kiểm tra token đã hết hạn chưa
         * @param expirationMs Thời gian hết hạn (milliseconds)
         * @return true nếu đã hết hạn
         */
        boolean isExpired(long expirationMs) {
            return System.currentTimeMillis() - createdAt > expirationMs;
        }
    }
    
    // Lưu trữ token -> TokenInfo (thread-safe cho multi-thread)
    private static final Map<String, TokenInfo> tokenStore = new ConcurrentHashMap<>();
    // Map userId -> token để xóa token cũ khi user login lại
    private static final Map<String, String> userTokenMap = new ConcurrentHashMap<>();
    
    // Cấu hình validation
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final long TOKEN_EXPIRATION_MS = 24 * 60 * 60 * 1000; // 24 giờ

    /**
     * Đăng nhập user
     * 
     * @param request Thông tin đăng nhập (email, password)
     * @return LoginResponse chứa thông tin user và token
     * @throws AppException nếu thông tin đăng nhập không hợp lệ
     */
    @Override
    public LoginResponse login(LoginRequest request) {
        // Validate request
        if (request == null) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.VALIDATION_DATA_EMPTY);
        }
        
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.FIELD_EMAIL + " " + ApiConstants.VALIDATION_REQUIRED);
        }
        
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.FIELD_PASSWORD + " " + ApiConstants.VALIDATION_REQUIRED);
        }

        try {
            // Tìm user theo email
            Optional<User> userOpt = userRepo.findByEmail(request.getEmail());
            
            if (!userOpt.isPresent()) {
                throw new AppException(Error.INVALID_CREDENTIALS, ApiConstants.VALIDATION_INVALID_CREDENTIALS);
            }

            User user = userOpt.get();
            
            // Kiểm tra password (so sánh trực tiếp)
            if (!request.getPassword().equals(user.getPassword())) {
                throw new AppException(Error.INVALID_CREDENTIALS, ApiConstants.VALIDATION_INVALID_CREDENTIALS);
            }

            // Xóa token cũ nếu user đã login trước đó
            String oldToken = userTokenMap.get(user.getId());
            if (oldToken != null) {
                tokenStore.remove(oldToken);
            }
            
            // Tạo token mới
            String token = UUID.randomUUID().toString();
            tokenStore.put(token, new TokenInfo(user.getId()));
            userTokenMap.put(user.getId(), token);
            
            log.info("User đăng nhập thành công: {}", user.getEmail());

            // Tạo response
            LoginResponse response = new LoginResponse();
            response.setId(user.getId());
            response.setEmail(user.getEmail());
            response.setFullname(user.getFullname());
            response.setAdmin(user.getAdmin());
            response.setToken(token);
            response.setCreatedDate(user.getCreatedDate());

            return response;
        } catch (AppException e) {
            log.warn("Đăng nhập thất bại cho email: {} - {}", request.getEmail(), e.getErrorMessage());
            throw e;
        } catch (Exception e) {
            log.error("Lỗi khi đăng nhập: " + e.getMessage(), e);
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi đăng nhập: " + e.getMessage());
        }
    }

    /**
     * Đăng xuất user
     * Xóa token khỏi tokenStore và userTokenMap
     * 
     * @param token Token cần đăng xuất
     * @return true nếu đăng xuất thành công, false nếu token không hợp lệ
     */
    @Override
    public boolean logout(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        
        try {
            TokenInfo info = tokenStore.remove(token);
            if (info != null) {
                userTokenMap.remove(info.userId);
                log.info("User đăng xuất thành công: {}", info.userId);
                return true;
            }
            log.warn("Logout với token không hợp lệ");
            return false;
        } catch (Exception e) {
            log.error("Lỗi khi đăng xuất: " + e.getMessage(), e);
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi đăng xuất: " + e.getMessage());
        }
    }

    /**
     * Xác thực token và trả về userId
     * Kiểm tra token có tồn tại và chưa hết hạn
     * 
     * @param token Token cần xác thực
     * @return userId nếu token hợp lệ, null nếu không hợp lệ hoặc hết hạn
     */
    @Override
    public String validateToken(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }
        
        try {
            TokenInfo info = tokenStore.get(token);
            if (info == null) {
                return null;
            }
            
            // Kiểm tra token hết hạn
            if (info.isExpired(TOKEN_EXPIRATION_MS)) {
                // Xóa token hết hạn
                tokenStore.remove(token);
                userTokenMap.remove(info.userId);
                return null;
            }
            
            return info.userId;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi xác thực token: " + e.getMessage());
        }
    }
    
    /**
     * Dọn dẹp các token hết hạn (có thể gọi định kỳ)
     */
    public void cleanupExpiredTokens() {
        Iterator<Map.Entry<String, TokenInfo>> iterator = tokenStore.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, TokenInfo> entry = iterator.next();
            if (entry.getValue().isExpired(TOKEN_EXPIRATION_MS)) {
                userTokenMap.remove(entry.getValue().userId);
                iterator.remove();
            }
        }
    }

    /**
     * Kiểm tra user có quyền admin không
     * 
     * @param userId ID của user cần kiểm tra
     * @return true nếu là admin, false nếu không
     */
    @Override
    public boolean isAdmin(String userId) {
        if (userId == null || userId.isEmpty()) {
            return false;
        }
        
        try {
            Optional<User> userOpt = userRepo.findById(userId);
            return userOpt.isPresent() && userOpt.get().getAdmin();
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi kiểm tra quyền admin: " + e.getMessage());
        }
    }

    /**
     * Đăng ký user mới
     * Validate đầy đủ: email format, password length, confirm password
     * 
     * @param request Thông tin đăng ký
     * @return UserResponse chứa thông tin user đã tạo
     * @throws AppException nếu email/ID đã tồn tại hoặc dữ liệu không hợp lệ
     */
    @Override
    public UserResponse register(RegisterRequest request) {
        // Validate request
        if (request == null) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.VALIDATION_DATA_EMPTY);
        }

        if (request.getId() == null || request.getId().trim().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.FIELD_ID + " " + ApiConstants.VALIDATION_REQUIRED);
        }

        if (request.getFullName() == null || request.getFullName().trim().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.FIELD_FULL_NAME + " " + ApiConstants.VALIDATION_REQUIRED);
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.FIELD_EMAIL + " " + ApiConstants.VALIDATION_REQUIRED);
        }
        
        // Validate email format
        if (!request.getEmail().matches(EMAIL_REGEX)) {
            throw new AppException(Error.INVALID_DATA, "Email không hợp lệ");
        }

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.FIELD_PASSWORD + " " + ApiConstants.VALIDATION_REQUIRED);
        }
        
        // Validate password length
        if (request.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new AppException(Error.INVALID_DATA, "Mật khẩu phải có ít nhất " + MIN_PASSWORD_LENGTH + " ký tự");
        }

        if (request.getConfirmPassword() == null || request.getConfirmPassword().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.FIELD_CONFIRM_PASSWORD + " " + ApiConstants.VALIDATION_REQUIRED);
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.VALIDATION_PASSWORD_MISMATCH);
        }

        // Kiểm tra email đã tồn tại
        Optional<User> existingUser = userRepo.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new AppException(Error.ALREADY_EXISTS, ApiConstants.VALIDATION_EMAIL_EXISTS);
        }

        // Kiểm tra ID đã tồn tại
        if (userRepo.existsById(request.getId())) {
            throw new AppException(Error.ALREADY_EXISTS, ApiConstants.VALIDATION_ID_EXISTS);
        }

        try {
            // Tạo UserRequest từ RegisterRequest
            com.fpt.java4_asm.dto.request.UserRequest userRequest = new com.fpt.java4_asm.dto.request.UserRequest();
            userRequest.setId(request.getId());
            userRequest.setFullName(request.getFullName());
            userRequest.setEmail(request.getEmail());
            userRequest.setPassword(request.getPassword());
            userRequest.setAdmin(request.getAdmin() != null ? request.getAdmin() : false);

            // Sử dụng UserService để tạo user
            UserResponse response = userService.create(userRequest);
            log.info("Đăng ký thành công user: {}", request.getEmail());
            return response;
        } catch (AppException e) {
            log.warn("Đăng ký thất bại: {}", e.getErrorMessage());
            throw e;
        } catch (Exception e) {
            log.error("Lỗi khi đăng ký: " + e.getMessage(), e);
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi đăng ký: " + e.getMessage());
        }
    }

    /**
     * Đổi mật khẩu cho user
     * Yêu cầu: mật khẩu cũ đúng, mật khẩu mới khác mật khẩu cũ
     * 
     * @param userId ID của user cần đổi mật khẩu
     * @param request Thông tin đổi mật khẩu (oldPassword, newPassword, confirmPassword)
     * @return true nếu đổi thành công
     * @throws AppException nếu mật khẩu cũ sai hoặc dữ liệu không hợp lệ
     */
    @Override
    public boolean changePassword(String userId, ChangePasswordRequest request) {
        // Validate request
        if (request == null) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.VALIDATION_DATA_EMPTY);
        }

        if (request.getOldPassword() == null || request.getOldPassword().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.FIELD_OLD_PASSWORD + " " + ApiConstants.VALIDATION_REQUIRED);
        }

        if (request.getNewPassword() == null || request.getNewPassword().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.FIELD_NEW_PASSWORD + " " + ApiConstants.VALIDATION_REQUIRED);
        }
        
        // Validate new password length
        if (request.getNewPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new AppException(Error.INVALID_DATA, "Mật khẩu mới phải có ít nhất " + MIN_PASSWORD_LENGTH + " ký tự");
        }

        if (request.getConfirmPassword() == null || request.getConfirmPassword().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.FIELD_CONFIRM_PASSWORD + " " + ApiConstants.VALIDATION_REQUIRED);
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.VALIDATION_PASSWORD_MISMATCH);
        }

        if (request.getOldPassword().equals(request.getNewPassword())) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.VALIDATION_PASSWORD_DIFFERENT);
        }

        try {
            // Lấy user theo ID
            Optional<User> userOpt = userRepo.findById(userId);
            if (!userOpt.isPresent()) {
                throw new AppException(Error.NOT_FOUND, ApiConstants.VALIDATION_USER_NOT_FOUND);
            }

            User user = userOpt.get();

            // Kiểm tra mật khẩu cũ
            if (!request.getOldPassword().equals(user.getPassword())) {
                throw new AppException(Error.INVALID_CREDENTIALS, ApiConstants.VALIDATION_OLD_PASSWORD_WRONG);
            }

            // Cập nhật mật khẩu mới
            user.setPassword(request.getNewPassword());
            userRepo.update(user);
            
            log.info("Đổi mật khẩu thành công cho user: {}", userId);
            return true;
        } catch (AppException e) {
            log.warn("Đổi mật khẩu thất bại cho user {}: {}", userId, e.getErrorMessage());
            throw e;
        } catch (Exception e) {
            log.error("Lỗi khi đổi mật khẩu: " + e.getMessage(), e);
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi đổi mật khẩu: " + e.getMessage());
        }
    }
}
