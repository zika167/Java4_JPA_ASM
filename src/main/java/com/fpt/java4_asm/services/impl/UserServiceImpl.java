package com.fpt.java4_asm.services.impl;

import com.fpt.java4_asm.dto.request.CreateUserRequest;
import com.fpt.java4_asm.dto.response.UserResponse;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.models.entities.User;
import com.fpt.java4_asm.repositories.UserRepo;
import com.fpt.java4_asm.repositories.impl.UserRepoImpl;
import com.fpt.java4_asm.services.UserService;
import com.fpt.java4_asm.utils.helpers.UserValidation;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private final UserRepo userRepo = new UserRepoImpl();

    @Override
    public UserResponse create(CreateUserRequest request) {
        UserValidation.validateCreateUserRequest(request);

        try {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setFullname(request.getFullName());
            user.setAdmin(false);
            user.setCreatedDate(new Date());
            user.setUpdatedDate(new Date());

            User savedUser = userRepo.save(user);
            return toUserResponse(savedUser);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi tạo user: " + e.getMessage());
        }
    }

    @Override
    public Optional<UserResponse> update(String id, CreateUserRequest request) {
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

            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setFullname(request.getFullName());
            user.setUpdatedDate(new Date());

            Optional<User> updatedUser = userRepo.update(user);
            return updatedUser.map(this::toUserResponse);
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
            return user.map(this::toUserResponse);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi lấy user: " + e.getMessage());
        }
    }

    @Override
    public List<UserResponse> getAll() {
        try {
            List<User> users = userRepo.findAll();
            return users.stream()
                    .map(this::toUserResponse)
                    .collect(Collectors.toList());
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
            return Optional.of(toUserResponse(user.get()));
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi đăng nhập: " + e.getMessage());
        }
    }

    private UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullname());
        response.setCreatedDate(user.getCreatedDate());
        return response;
    }
}
