package com.fpt.java4_asm.utils.helpers;

import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;

/**
 * Helper class cho validation
 */
public class ValidationHelper {

    /**
     * Kiểm tra string có rỗng hay không
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Kiểm tra string có hợp lệ hay không
     */
    public static void validateNotEmpty(String value, String fieldName) {
        if (isEmpty(value)) {
            throw new AppException(Error.MISSING_REQUIRED_FIELD, fieldName + " không được để trống");
        }
    }

    /**
     * Kiểm tra email hợp lệ
     */
    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    /**
     * Kiểm tra phone hợp lệ (Việt Nam)
     */
    public static boolean isValidPhoneNumber(String phone) {
        if (isEmpty(phone)) {
            return false;
        }
        String phoneRegex = "^(0|\\+84)[0-9]{9,10}$";
        return phone.matches(phoneRegex);
    }

    /**
     * Kiểm tra ID có hợp lệ hay không
     */
    public static void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new AppException(Error.INVALID_DATA, "ID không hợp lệ");
        }
    }

    /**
     * Kiểm tra ID từ string
     */
    public static Long parseAndValidateId(String idStr) {
        try {
            Long id = Long.parseLong(idStr);
            validateId(id);
            return id;
        } catch (NumberFormatException e) {
            throw new AppException(Error.INVALID_DATA, "ID phải là số");
        }
    }
}
