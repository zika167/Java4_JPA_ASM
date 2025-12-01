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
    INVALID_INPUT("400", "Dữ liệu không hợp lệ"),

    // Lỗi liên quan đến người dùng
    USER_NOT_FOUND("USER_001", "Người dùng không tồn tại"),
    USER_ALREADY_EXISTS("USER_002", "Người dùng đã tồn tại"),
    INVALID_CREDENTIALS("USER_003", "Thông tin đăng nhập không hợp lệ"),

    //Lỗi liên quan đến Favorite
    FAVORITE_NOT_FOUND("FAVORITE_001", "Thông tin yêu thích không tồn tại"),
    FAVORITE_ALREADY_EXISTS("FAVORITE_002", "Thông tin yêu thích đã tồn tại"),
    FAVORITE_ID_NOT_ALREADY_EXISTS("FAVORITE_003", "ID yêu thích không tồn tại"),
    FAVORITE_ID_ALREADY_EXISTS("FAVORITE_004", "ID yêu thích đã tồn tại"),
    FAVORITE_UPDATE_FAILED("FAVORITE_005", "Cập nhật thông tin yêu thích thất bại"),
    FAVORITE_DELETE_FAILED("FAVORITE_006", "Xóa thông tin yêu thích thất bại"),
    FAVORITE_INVALID_INPUT("FAVORITE_007", "Dữ liệu yêu thích không hợp lệ"),
    FAVORITE_ALREADY_DELETED("FAVORITE_008", "Thông tin yêu thích đã bị xóa trước đó"),

    // Lỗi liên quan đến Video
    VIDEO_NOT_FOUND("VIDEO_001", "Video không tồn tại"),
    VIDEO_ALREADY_EXISTS("VIDEO_002", "Video đã tồn tại"),

    // Lỗi liên quan đến dữ liệu
    ALREADY_EXISTS("DATA_000", "Dữ liệu đã tồn tại"),
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
