package com.fpt.java4_asm.exception;

/**
 * Enum định nghĩa tất cả các loại lỗi trong ứng dụng
 * 
 * Mỗi error bao gồm:
 * - code: Mã lỗi duy nhất (VD: USER_001, VIDEO_002)
 * - message: Thông báo lỗi mặc định bằng tiếng Việt
 * - httpStatus: HTTP status code tương ứng
 * 
 * Phân loại lỗi:
 * - HTTP Status Errors: Lỗi HTTP chuẩn (400, 401, 403, 404, 500)
 * - Validation Errors (VAL_xxx): Lỗi validate dữ liệu đầu vào
 * - Authentication Errors (AUTH_xxx): Lỗi xác thực
 * - User Errors (USER_xxx): Lỗi liên quan đến User
 * - Video Errors (VIDEO_xxx): Lỗi liên quan đến Video
 * - Comment Errors (CMT_xxx): Lỗi liên quan đến Comment
 * - Favorite Errors (FAV_xxx): Lỗi liên quan đến Favorite
 * - Share Errors (SHARE_xxx): Lỗi liên quan đến Share
 * - Database Errors (DB_xxx): Lỗi database
 * - General Errors (GEN_xxx): Lỗi chung
 * 
 * @author Java4 ASM
 */
public enum Error {
    // ==================== HTTP Status Errors ====================
    BAD_REQUEST("400", "Yêu cầu không hợp lệ", 400),
    UNAUTHORIZED("401", "Chưa xác thực", 401),
    FORBIDDEN("403", "Không có quyền truy cập", 403),
    NOT_FOUND("404", "Không tìm thấy", 404),
    CONFLICT("409", "Xung đột dữ liệu", 409),
    INTERNAL_SERVER_ERROR("500", "Lỗi máy chủ nội bộ", 500),

    // ==================== Validation Errors ====================
    INVALID_INPUT("VAL_001", "Dữ liệu đầu vào không hợp lệ", 400),
    INVALID_DATA("VAL_002", "Dữ liệu không hợp lệ", 400),
    MISSING_REQUIRED_FIELD("VAL_003", "Thiếu trường bắt buộc", 400),
    INVALID_FORMAT("VAL_004", "Định dạng không hợp lệ", 400),
    INVALID_EMAIL("VAL_005", "Email không hợp lệ", 400),
    INVALID_PASSWORD("VAL_006", "Mật khẩu không hợp lệ", 400),
    INVALID_PAGINATION("VAL_007", "Tham số phân trang không hợp lệ", 400),

    // ==================== Authentication Errors ====================
    INVALID_CREDENTIALS("AUTH_001", "Thông tin đăng nhập không hợp lệ", 401),
    TOKEN_EXPIRED("AUTH_002", "Token đã hết hạn", 401),
    TOKEN_INVALID("AUTH_003", "Token không hợp lệ", 401),
    TOKEN_MISSING("AUTH_004", "Thiếu token xác thực", 401),
    SESSION_EXPIRED("AUTH_005", "Phiên đăng nhập đã hết hạn", 401),
    ACCESS_DENIED("AUTH_006", "Truy cập bị từ chối", 403),

    // ==================== User Errors ====================
    USER_NOT_FOUND("USER_001", "Người dùng không tồn tại", 404),
    USER_ALREADY_EXISTS("USER_002", "Người dùng đã tồn tại", 409),
    USER_EMAIL_EXISTS("USER_003", "Email đã được sử dụng", 409),
    USER_ID_EXISTS("USER_004", "ID người dùng đã tồn tại", 409),
    USER_INACTIVE("USER_005", "Tài khoản đã bị vô hiệu hóa", 403),
    USER_CREATE_FAILED("USER_006", "Tạo người dùng thất bại", 500),
    USER_UPDATE_FAILED("USER_007", "Cập nhật người dùng thất bại", 500),
    USER_DELETE_FAILED("USER_008", "Xóa người dùng thất bại", 500),

    // ==================== Video Errors ====================
    VIDEO_NOT_FOUND("VIDEO_001", "Video không tồn tại", 404),
    VIDEO_ALREADY_EXISTS("VIDEO_002", "Video đã tồn tại", 409),
    VIDEO_CREATE_FAILED("VIDEO_003", "Tạo video thất bại", 500),
    VIDEO_UPDATE_FAILED("VIDEO_004", "Cập nhật video thất bại", 500),
    VIDEO_DELETE_FAILED("VIDEO_005", "Xóa video thất bại", 500),
    VIDEO_INACTIVE("VIDEO_006", "Video đã bị ẩn", 403),

    // ==================== Comment Errors ====================
    COMMENT_NOT_FOUND("CMT_001", "Bình luận không tồn tại", 404),
    COMMENT_ALREADY_EXISTS("CMT_002", "Bình luận đã tồn tại", 409),
    COMMENT_CREATE_FAILED("CMT_003", "Tạo bình luận thất bại", 500),
    COMMENT_UPDATE_FAILED("CMT_004", "Cập nhật bình luận thất bại", 500),
    COMMENT_DELETE_FAILED("CMT_005", "Xóa bình luận thất bại", 500),
    COMMENT_EMPTY("CMT_006", "Nội dung bình luận không được để trống", 400),

    // ==================== Favorite Errors ====================
    FAVORITE_NOT_FOUND("FAV_001", "Mục yêu thích không tồn tại", 404),
    FAVORITE_ALREADY_EXISTS("FAV_002", "Đã thêm vào yêu thích trước đó", 409),
    FAVORITE_CREATE_FAILED("FAV_003", "Thêm yêu thích thất bại", 500),
    FAVORITE_UPDATE_FAILED("FAV_004", "Cập nhật yêu thích thất bại", 500),
    FAVORITE_DELETE_FAILED("FAV_005", "Xóa yêu thích thất bại", 500),
    FAVORITE_ID_NOT_ALREADY_EXISTS("FAV_006", "ID yêu thích không tồn tại", 404),
    FAVORITE_ID_ALREADY_EXISTS("FAV_007", "ID yêu thích đã tồn tại", 409),
    FAVORITE_INVALID_INPUT("FAV_008", "Dữ liệu yêu thích không hợp lệ", 400),
    FAVORITE_ALREADY_DELETED("FAV_009", "Mục yêu thích đã bị xóa trước đó", 404),

    // ==================== Share Errors ====================
    SHARE_NOT_FOUND("SHARE_001", "Chia sẻ không tồn tại", 404),
    SHARE_ALREADY_EXISTS("SHARE_002", "Đã chia sẻ trước đó", 409),
    SHARE_CREATE_FAILED("SHARE_003", "Chia sẻ thất bại", 500),
    SHARE_UPDATE_FAILED("SHARE_004", "Cập nhật chia sẻ thất bại", 500),
    SHARE_DELETE_FAILED("SHARE_005", "Xóa chia sẻ thất bại", 500),
    SHARE_INVALID_EMAIL("SHARE_006", "Email chia sẻ không hợp lệ", 400),

    // ==================== Database Errors ====================
    DATABASE_ERROR("DB_001", "Lỗi cơ sở dữ liệu", 500),
    DATABASE_CONNECTION_FAILED("DB_002", "Không thể kết nối cơ sở dữ liệu", 500),
    DATABASE_QUERY_FAILED("DB_003", "Truy vấn thất bại", 500),
    DATABASE_TRANSACTION_FAILED("DB_004", "Giao dịch thất bại", 500),
    DATABASE_CONSTRAINT_VIOLATION("DB_005", "Vi phạm ràng buộc dữ liệu", 409),

    // ==================== General Errors ====================
    ALREADY_EXISTS("GEN_001", "Dữ liệu đã tồn tại", 409),
    OPERATION_FAILED("GEN_002", "Thao tác thất bại", 500),
    UNKNOWN_ERROR("GEN_999", "Lỗi không xác định", 500);

    private final String code;
    private final String message;
    private final int httpStatus;

    Error(String code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
