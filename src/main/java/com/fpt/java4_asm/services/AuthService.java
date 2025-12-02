package com.fpt.java4_asm.services;

import com.fpt.java4_asm.dto.request.LoginRequest;
import com.fpt.java4_asm.dto.request.RegisterRequest;
import com.fpt.java4_asm.dto.request.ChangePasswordRequest;
import com.fpt.java4_asm.dto.response.LoginResponse;
import com.fpt.java4_asm.dto.response.UserResponse;

/**
 * Service interface cho Authentication (Đăng nhập/Đăng xuất)
 * Định nghĩa các phương thức xác thực người dùng
 */
public interface AuthService {
    /**
     * Đăng nhập người dùng
     * 
     * @param request Chứa email và password
     * @return LoginResponse chứa thông tin user và token
     * @throws AppException nếu email/password không hợp lệ
     */
    LoginResponse login(LoginRequest request);

    /**
     * Đăng xuất người dùng
     * 
     * @param token Token xác thực cần xóa
     * @return true nếu đăng xuất thành công
     */
    boolean logout(String token);

    /**
     * Xác thực token
     * 
     * @param token Token cần xác thực
     * @return User ID nếu token hợp lệ, null nếu không
     */
    String validateToken(String token);

    /**
     * Kiểm tra quyền admin
     * 
     * @param userId User ID cần kiểm tra
     * @return true nếu user là admin
     */
    boolean isAdmin(String userId);

    /**
     * Đăng ký người dùng mới
     * 
     * @param request Chứa thông tin đăng ký
     * @return UserResponse chứa thông tin user vừa tạo
     * @throws AppException nếu dữ liệu không hợp lệ
     */
    UserResponse register(RegisterRequest request);

    /**
     * Đổi mật khẩu
     * 
     * @param userId ID của user
     * @param request Chứa mật khẩu cũ và mới
     * @return true nếu đổi thành công
     * @throws AppException nếu mật khẩu cũ sai hoặc dữ liệu không hợp lệ
     */
    boolean changePassword(String userId, ChangePasswordRequest request);
}
