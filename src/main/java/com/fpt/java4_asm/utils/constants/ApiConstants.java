package com.fpt.java4_asm.utils.constants;

/**
 * Hằng số API
 */
public class ApiConstants {
    // API Paths
    public static final String API_BASE_PATH = "/api";
    public static final String API_USERS = "/api/users";
    public static final String API_PRODUCTS = "/api/products";
    public static final String API_ORDERS = "/api/orders";

    // Response Messages
    public static final String MSG_SUCCESS = "Thành công";
    public static final String MSG_CREATED = "Tạo mới thành công";
    public static final String MSG_UPDATED = "Cập nhật thành công";
    public static final String MSG_DELETED = "Xóa thành công";
    public static final String MSG_ERROR = "Có lỗi xảy ra";
    public static final String MSG_NOT_FOUND = "Không tìm thấy dữ liệu";
    public static final String MSG_INVALID_INPUT = "Dữ liệu đầu vào không hợp lệ";

    // Pagination
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 100;

    // Status
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";
    public static final String STATUS_DELETED = "DELETED";

    private ApiConstants() {
        // Prevent instantiation
    }
}
