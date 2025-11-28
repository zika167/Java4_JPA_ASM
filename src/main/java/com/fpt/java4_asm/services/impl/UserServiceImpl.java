package com.fpt.java4_asm.services.impl;

import com.fpt.java4_asm.dto.request.UserRequest;
import com.fpt.java4_asm.dto.response.UserResponse;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.models.entities.User;
import com.fpt.java4_asm.repositories.UserRepo;
import com.fpt.java4_asm.repositories.impl.UserRepoImpl;
import com.fpt.java4_asm.services.UserService;
import com.fpt.java4_asm.convert.UserConvert;
import com.fpt.java4_asm.utils.helpers.UserValidation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private final UserRepo userRepo = new UserRepoImpl();
    private final UserConvert userConvert = new UserConvert();
    // Lưu ý để final cho ko bị dùng bừa bãi
    // 2 biến cục bộ khởi tạo đối tượng trống để xuống dưới hàm dùng
    @Override
    public UserResponse create(UserRequest request) {
        UserValidation.validateCreateUserRequest(request);
    // Hàm tạo. trước khi tạo check
        try {
            User user = userConvert.toEntity(request);
            user.setId(UUID.randomUUID().toString());

            User savedUser = userRepo.save(user);
            return userConvert.toResponse(savedUser);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi tạo user: " + e.getMessage());
        }
    }

    @Override
    public Optional<UserResponse> update(String id, UserRequest request) {
        UserValidation.validateUserId(id);
        UserValidation.validateCreateUserRequest(request);

        try {
            Optional<User> existingUser = userRepo.findById(id);
            if (existingUser.isEmpty()) {
                throw new AppException(Error.NOT_FOUND, "Không tìm thấy User với ID: " + id);
            }

            User user = existingUser.get();

            if (!user.getEmail().equals(request.getEmail()) && 
                userRepo.findByEmail(request.getEmail()).isPresent()) {
                throw new AppException(Error.INVALID_DATA, "Email đã tồn tại");
            }

            userConvert.toEntity(user, request);
            Optional<User> updatedUser = userRepo.update(user);
            return updatedUser.map(userConvert::toResponse);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi cập nhật user: " + e.getMessage());
        }
    }

    @Override
    public Optional<UserResponse> getById(String id) {
        UserValidation.validateUserId(id);

        try {
            Optional<User> user = userRepo.findById(id);
            return user.map(userConvert::toResponse);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi lấy user: " + e.getMessage());
        }
    }

    @Override
    public List<UserResponse> getAll() {
        try {
            List<User> users = userRepo.findAll();
            return userConvert.toResponseList(users);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi lấy danh sách user: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(String id) {
        UserValidation.validateUserId(id);

        try {
            if (!userRepo.existsById(id)) {
                throw new AppException(Error.NOT_FOUND, "Không tìm thấy User với ID: " + id);
            }
            return userRepo.deleteById(id);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi xóa user: " + e.getMessage());
        }
    }

    @Override
    public boolean exists(String id) {
        UserValidation.validateUserId(id);

        try {
            return userRepo.existsById(id);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi kiểm tra user: " + e.getMessage());
        }
    }

    @Override
    public long count() {
        try {
            return userRepo.count();
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi đếm user: " + e.getMessage());
        }
    }

    @Override
    public Optional<UserResponse> login(String email, String password) {
        UserValidation.validateLoginCredentials(email, password);

        try {
            Optional<User> user = userRepo.findByEmailAndPassword(email, password);
            if (user.isEmpty()) {
                throw new AppException(Error.INVALID_DATA, "Email hoặc password không chính xác");
            }
            return Optional.of(userConvert.toResponse(user.get()));
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi đăng nhập: " + e.getMessage());
        }
    }
}
