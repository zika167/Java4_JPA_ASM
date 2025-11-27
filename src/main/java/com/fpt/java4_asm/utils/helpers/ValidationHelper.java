package com.fpt.java4_asm.utils.helpers;

import com.fpt.java4_asm.dto.request.CreateUserRequest;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.repositories.UserRepo;
import com.fpt.java4_asm.repositories.impl.UserRepoImpl;

public class ValidationHelper {
    private static final UserRepo userRepo = new UserRepoImpl();
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    private ValidationHelper() {
        throw new UnsupportedOperationException("Không thể tạo thể hiện của lớp tiện ích");
    }

    public static void validateCreateUserRequest(CreateUserRequest request) {
        if (request == null) {
            throw new AppException(Error.INVALID_DATA, "Request không được null");
        }

        validateNotEmpty(request.getEmail(), "Email");
        validateEmailFormat(request.getEmail());
        validateDuplicateEmail(request.getEmail());

        validateNotEmpty(request.getPassword(), "Password");
        validatePasswordLength(request.getPassword());

        validateNotEmpty(request.getConfirmPassword(), "Confirm Password");
        validatePasswordMatch(request.getPassword(), request.getConfirmPassword());

        if (request.getFullName() != null && request.getFullName().trim().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, "Full Name không được để trắng");
        }
    }

    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, fieldName + " không được để trống");
        }
    }

    public static void validateEmailFormat(String email) {
        if (!email.matches(EMAIL_REGEX)) {
            throw new AppException(Error.INVALID_DATA, "Email không hợp lệ");
        }
    }

    public static void validatePasswordLength(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new AppException(Error.INVALID_DATA, 
                "Password phải có ít nhất " + MIN_PASSWORD_LENGTH + " ký tự");
        }
    }

    public static void validatePasswordMatch(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new AppException(Error.INVALID_DATA, "Password và Confirm Password không khớp");
        }
    }

    public static void validateDuplicateEmail(String email) {
        if (userRepo.findByEmail(email).isPresent()) {
            throw new AppException(Error.INVALID_DATA, "Email đã tồn tại");
        }
    }
}
