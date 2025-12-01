package com.fpt.java4_asm.convert;

import com.fpt.java4_asm.dto.request.UserRequest;
import com.fpt.java4_asm.dto.response.UserResponse;
import com.fpt.java4_asm.models.entities.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UserConvert {
    // Lớp chuyên chuyển đổi giữa User entity (DB) và UserRequest/UserResponse (API)
    // Entity: đối tượng đại diện cho bảng trong database
    // Request: dữ liệu nhận từ client (người dùng gửi lên)
    // Response: dữ liệu trả về cho client (chỉ gửi những gì client cần, không gửi password)
    
    // Chuyển từ User entity sang UserResponse DTO để trả về cho client
    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullname());
        response.setAdmin(user.getAdmin());
        response.setCreatedDate(user.getCreatedDate());
        response.setUpdatedDate(user.getUpdatedDate());
        return response;
    }

    // Chuyển từ UserRequest sang User entity (tạo user mới từ dữ liệu request)
    public User toEntity(UserRequest request) {
        if (request == null) {
            return null;
        }

        User user = new User();
        user.setId(request.getId());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFullname(request.getFullName());
        user.setAdmin(request.getAdmin()); // User mới mặc định không phải admin
        user.setCreatedDate(new Date()); // Ghi nhận thời điểm tạo
        user.setUpdatedDate(new Date()); // Ghi nhận thời điểm cập nhật
        return user;
    }

    // Cập nhật User entity đã có từ dữ liệu UserRequest (overload, để update user)
    // Nếu request có field nào thì update, không thì giữ nguyên giá trị cũ
    public User toEntity(User user, UserRequest request) {
        if (user == null || request == null) {
            return user;
        }

        user.setId(request.getId() != null ? request.getId() : user.getId());
        user.setEmail(request.getEmail() != null ? request.getEmail() : user.getEmail());
        user.setPassword(request.getPassword() != null ? request.getPassword() : user.getPassword());
        user.setFullname(request.getFullName() != null ? request.getFullName() : user.getFullname());
        user.setAdmin(request.getAdmin() != null ? request.getAdmin() : user.getAdmin());
        user.setUpdatedDate(new Date()); // Cập nhật thời gian sửa đổi
        return user;
    }

    // Chuyển danh sách User entities sang danh sách UserResponses
    // Dùng Stream API để xử lý từng user, rồi collect vào List mới
    public List<UserResponse> toResponseList(List<User> users) {
        if (users == null) {
            return null;
        }

        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
