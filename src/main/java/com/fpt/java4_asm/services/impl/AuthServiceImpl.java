package com.fpt.java4_asm.services.impl;

import com.fpt.java4_asm.convert.UserConvert;
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
import com.fpt.java4_asm.services.impl.UserServiceImpl;
import com.fpt.java4_asm.utils.constants.ApiConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Triển khai cụ thể của AuthService
 * 
 * Lớp này cung cấp các phương thức xác thực người dùng
 * Bao gồm: đăng nhập, đăng xuất, xác thực token
 */
public class AuthServiceImpl implements AuthService {
    private final UserRepo userRepo = new UserRepoImpl();
    private final UserConvert userConvert = new UserConvert();
    private final UserService userService = new UserServiceImpl();
    
    // Lưu trữ token và user ID (trong thực tế nên dùng Redis hoặc database)
    private static final Map<String, String> tokenStore = new HashMap<>();

    @Override
    public LoginResponse login(LoginRequest request) {
        // Validate request
        if (request == null || request.getEmail() == null || request.getPassword() == null) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.VALIDATION_DATA_EMPTY);
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

            // Tạo token
            String token = UUID.randomUUID().toString();
            tokenStore.put(token, user.getId());

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
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi đăng nhập: " + e.getMessage());
        }
    }

    @Override
    public boolean logout(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        
        try {
            return tokenStore.remove(token) != null;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi đăng xuất: " + e.getMessage());
        }
    }

    @Override
    public String validateToken(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }
        
        try {
            return tokenStore.get(token);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi xác thực token: " + e.getMessage());
        }
    }

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

    @Override
    public UserResponse register(RegisterRequest request) {
        // Validate request
        if (request == null) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.VALIDATION_DATA_EMPTY);
        }

        if (request.getId() == null || request.getId().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.FIELD_ID + " " + ApiConstants.VALIDATION_REQUIRED);
        }

        if (request.getFullName() == null || request.getFullName().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.FIELD_FULL_NAME + " " + ApiConstants.VALIDATION_REQUIRED);
        }

        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.FIELD_EMAIL + " " + ApiConstants.VALIDATION_REQUIRED);
        }

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, ApiConstants.FIELD_PASSWORD + " " + ApiConstants.VALIDATION_REQUIRED);
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
            return userService.create(userRequest);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi đăng ký: " + e.getMessage());
        }
    }

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

            return true;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi đổi mật khẩu: " + e.getMessage());
        }
    }
}
