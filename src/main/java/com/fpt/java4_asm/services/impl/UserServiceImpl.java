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
    // Lưu ý để final cho ko bị dùng bừa bãi (private final = static + immutable)
    // 2 biến cục bộ khởi tạo đối tượng để dùng ở các hàm dưới

    // Tạo user mới từ request của client
    @Override
    public UserResponse create(UserRequest request) {
        // Xác thực dữ liệu request trước khi tạo user
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

    // Cập nhật thông tin user theo ID
    @Override
    public Optional<UserResponse> update(String id, UserRequest request) {
        // Xác thực ID và request trước khi update
        UserValidation.validateUserId(id);
        UserValidation.validateCreateUserRequest(request);

        try {
            // Tìm user theo ID
            Optional<User> existingUser = userRepo.findById(id);
            if (existingUser.isEmpty()) {
                throw new AppException(Error.NOT_FOUND, "Không tìm thấy User với ID: " + id);
            }

            User user = existingUser.get();

            // Kiểm tra nếu email thay đổi thì email mới không được trùng với user khác
            if (!user.getEmail().equals(request.getEmail()) && 
                userRepo.findByEmail(request.getEmail()).isPresent()) {
                throw new AppException(Error.INVALID_DATA, "Email đã tồn tại");
            }

            // Cập nhật dữ liệu user từ request
            userConvert.toEntity(user, request);
            Optional<User> updatedUser = userRepo.update(user);
            return updatedUser.map(userConvert::toResponse);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi cập nhật user: " + e.getMessage());
        }
    }

    // Lấy thông tin user theo ID
    @Override
    public Optional<UserResponse> getById(String id) {
        // Xác thực ID không được để trống
        UserValidation.validateUserId(id);

        try {
            // Tìm user theo ID, nếu tìm thấy thì chuyển sang Response
            Optional<User> user = userRepo.findById(id);
            return user.map(userConvert::toResponse);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi lấy user: " + e.getMessage());
        }
    }

    // Lấy danh sách toàn bộ user
    @Override
    public List<UserResponse> getAll() {
        try {
            // Lấy tất cả user từ database rồi chuyển thành UserResponse
            List<User> users = userRepo.findAll();
            return userConvert.toResponseList(users);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi lấy danh sách user: " + e.getMessage());
        }
    }

    // Xóa user theo ID
    @Override
    public boolean delete(String id) {
        // Xác thực ID không được để trống
        UserValidation.validateUserId(id);

        try {
            // Kiểm tra user có tồn tại không
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

    // Kiểm tra user có tồn tại theo ID
    @Override
    public boolean exists(String id) {
        // Xác thực ID không được để trống
        UserValidation.validateUserId(id);

        try {
            return userRepo.existsById(id);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi kiểm tra user: " + e.getMessage());
        }
    }

    // Đếm tổng số user trong database
    @Override
    public long count() {
        try {
            return userRepo.count();
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi đếm user: " + e.getMessage());
        }
    }

    // Xác thực đăng nhập user theo email và password
    @Override
    public Optional<UserResponse> login(String email, String password) {
        // Xác thực email và password không được để trống
        UserValidation.validateLoginCredentials(email, password);

        try {
            // Tìm user với email và password khớp
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
