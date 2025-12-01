package com.fpt.java4_asm.services.impl;

import com.fpt.java4_asm.convert.UserConvert;
import com.fpt.java4_asm.dto.request.LoginRequest;
import com.fpt.java4_asm.dto.response.LoginResponse;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.models.entities.User;
import com.fpt.java4_asm.repositories.UserRepo;
import com.fpt.java4_asm.repositories.impl.UserRepoImpl;
import com.fpt.java4_asm.services.AuthService;

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
    
    // Lưu trữ token và user ID (trong thực tế nên dùng Redis hoặc database)
    private static final Map<String, String> tokenStore = new HashMap<>();

    @Override
    public LoginResponse login(LoginRequest request) {
        // Validate request
        if (request == null || request.getEmail() == null || request.getPassword() == null) {
            throw new AppException(Error.INVALID_DATA, "Email và password không được để trống");
        }

        try {
            // Tìm user theo email
            Optional<User> userOpt = userRepo.findByEmail(request.getEmail());
            
            if (!userOpt.isPresent()) {
                throw new AppException(Error.INVALID_CREDENTIALS, "Email hoặc password không chính xác");
            }

            User user = userOpt.get();
            
            // Kiểm tra password (so sánh trực tiếp)
            if (!request.getPassword().equals(user.getPassword())) {
                throw new AppException(Error.INVALID_CREDENTIALS, "Email hoặc password không chính xác");
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
}
