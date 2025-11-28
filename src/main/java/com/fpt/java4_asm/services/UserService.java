package com.fpt.java4_asm.services;

import com.fpt.java4_asm.dto.request.UserRequest;
import com.fpt.java4_asm.dto.response.UserResponse;

import java.util.List;
import java.util.Optional;

// Interface định nghĩa các hợp đồng (method) mà UserService phải thực hiện
// Tất cả các class implement interface này phải viết code cho tất cả method
public interface UserService {
    // Tạo user mới từ request và trả về UserResponse
    UserResponse create(UserRequest request);

    // Cập nhật user theo ID với dữ liệu mới từ request
    // Trả về Optional (có thể null nếu user không tồn tại)
    Optional<UserResponse> update(String id, UserRequest request);

    // Lấy thông tin user theo ID
    // Trả về Optional vì user có thể không tồn tại
    Optional<UserResponse> getById(String id);

    // Lấy danh sách tất cả user
    List<UserResponse> getAll();

    // Xóa user theo ID, trả về true nếu xóa thành công
    boolean delete(String id);

    // Kiểm tra user có tồn tại theo ID, trả về true nếu tồn tại
    boolean exists(String id);

    // Đếm tổng số user trong database
    long count();

    // Xác thực đăng nhập (login) với email và password
    // Trả về Optional<UserResponse> nếu đăng nhập thành công
    Optional<UserResponse> login(String email, String password);
}
