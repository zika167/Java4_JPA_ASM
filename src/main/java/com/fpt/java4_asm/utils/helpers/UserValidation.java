package com.fpt.java4_asm.utils.helpers;

import com.fpt.java4_asm.dto.request.UserRequest;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.repositories.UserRepo;
import com.fpt.java4_asm.repositories.impl.UserRepoImpl;

public class UserValidation {
    // Lớp để kiểm tra tính hợp lệ của các dữ liệu User trước khi xử lý
    // Ko phải cái nào Lộc cũng biết nên mn hỏi AI nếu thấy Lộc ko cmt nhé
    
    private static final UserRepo userRepo = new UserRepoImpl();
    private static final int MIN_PASSWORD_LENGTH = 6; // Độ dài tối thiểu password
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$"; // Biểu thức chính quy kiểm tra định dạng email

    private UserValidation() {
        throw new UnsupportedOperationException("Không thể tạo thể hiện của lớp tiện ích");
    }

    // Kiểm tra toàn bộ dữ liệu request tạo user (email, password, fullname)
    public static void validateCreateUserRequest(UserRequest request) {
        if (request == null) {
            throw new AppException(Error.INVALID_DATA, "Request không được null");
        }

        validateNotEmpty(request.getEmail(), "Email");
        validateEmailFormat(request.getEmail());
        validateDuplicateEmail(request.getEmail()); // Kiểm tra email chưa tồn tại

        validateNotEmpty(request.getPassword(), "Password");
        validatePasswordLength(request.getPassword());

        if (request.getFullName() != null && request.getFullName().trim().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, "Full Name không được để trắng");
        }
    }

    // Kiểm tra field không null và không phải chuỗi trống
    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, fieldName + " không được để trống");
        }
    }

    // Kiểm tra email có đúng định dạng (chứa @ và domain) không
    public static void validateEmailFormat(String email) {
        if (!email.matches(EMAIL_REGEX)) {
            // EMAIL_REGEX: ^[A-Za-z0-9+_.-]+@(.+)$ kiểm tra ko đúng biểu thức chính quy
            throw new AppException(Error.INVALID_DATA, "Email không hợp lệ");
        }
    }

    // Kiểm tra độ dài password >= 6 ký tự (MIN_PASSWORD_LENGTH)
    public static void validatePasswordLength(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new AppException(Error.INVALID_DATA, 
                "Password phải có ít nhất " + MIN_PASSWORD_LENGTH + " ký tự");
        }
    }

    // Kiểm tra email đã tồn tại trong database chưa
    // isPresent() trả về true nếu Optional có giá trị (email tìm thấy), false nếu không
    public static void validateDuplicateEmail(String email) {
        if (userRepo.findByEmail(email).isPresent()) {
            throw new AppException(Error.INVALID_DATA, "Email đã tồn tại");
        }
    }

    // Kiểm tra User ID không được để trống
    public static void validateUserId(String id) {
        validateNotEmpty(id, "User ID");
    }

    // Kiểm tra dữ liệu request update user (không check duplicate email vì sẽ check riêng trong service)
    public static void validateUpdateUserRequest(UserRequest request) {
        if (request == null) {
            throw new AppException(Error.INVALID_DATA, "Request không được null");
        }

        validateNotEmpty(request.getEmail(), "Email");
        validateEmailFormat(request.getEmail());

        validateNotEmpty(request.getPassword(), "Password");
        validatePasswordLength(request.getPassword());

        if (request.getFullName() != null && request.getFullName().trim().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, "Full Name không được để trắng");
        }
    }

    public static void validatePagination(int page, int size) {
        if (page < 0){
            throw new AppException(Error.INVALID_DATA,"Trang không được nhỏ hơn 1");
        }
        if (size <= 0){
            throw new AppException(Error.INVALID_DATA,"Số lượng phần  không được nhỏ hơn 1");
        }
    }
}
