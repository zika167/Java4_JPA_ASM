package com.fpt.java4_asm.exception;

/**
 * Enum định nghĩa các lỗi trong ứng dụng
 */
public enum Error {
    // Lỗi chung
    INTERNAL_SERVER_ERROR("500", "Lỗi máy chủ nội bộ"),
    BAD_REQUEST("400", "Yêu cầu không hợp lệ"),
    UNAUTHORIZED("401", "Chưa xác thực"),
    FORBIDDEN("403", "Không có quyền truy cập"),
    NOT_FOUND("404", "Không tìm thấy"),
    CONFLICT("409", "Xung đột dữ liệu"),

    // Lỗi liên quan đến người dùng
    USER_NOT_FOUND("USER_001", "Người dùng không tồn tại"),
    USER_ALREADY_EXISTS("USER_002", "Người dùng đã tồn tại"),
    INVALID_CREDENTIALS("USER_003", "Thông tin đăng nhập không hợp lệ"),

    // Lỗi liên quan đến dữ liệu
    INVALID_DATA("DATA_001", "Dữ liệu không hợp lệ"),
    MISSING_REQUIRED_FIELD("DATA_002", "Thiếu trường bắt buộc"),
    DATABASE_ERROR("DATA_003", "Lỗi cơ sở dữ liệu");

    private final String code;
    private final String message;

    Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
