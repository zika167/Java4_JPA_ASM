package com.fpt.java4_asm.utils.constants;

/**
 * Hằng số API
 */
public class ApiConstants {
    // API Base Path
    public static final String API_BASE_PATH = "/api";
    
    // API Paths
    public static final String API_USERS = API_BASE_PATH + "/users";
    public static final String API_VIDEOS = API_BASE_PATH + "/videos";
    public static final String API_FAVORITES = API_BASE_PATH + "/favorites";
    public static final String API_SHARES = API_BASE_PATH + "/shares";
    public static final String API_COMMENTS = API_BASE_PATH + "/comments";
    public static final String API_AUTH = API_BASE_PATH + "/auth";
    public static final String API_STATISTICS = API_BASE_PATH + "/statistics";

    // Common Path Variables
    public static final String ID_PATH_VARIABLE = "/{id}";
    public static final String USER_ID_PATH = "/user/{userId}";
    public static final String VIDEO_ID_PATH = "/video/{videoId}";
    
    // Response Messages
    public static final String MSG_SUCCESS = "Thành công";
    public static final String MSG_CREATED = "Tạo mới thành công";
    public static final String MSG_UPDATED = "Cập nhật thành công";
    public static final String MSG_DELETED = "Xóa thành công";
    public static final String MSG_ERROR = "Có lỗi xảy ra";
    public static final String MSG_NOT_FOUND = "Không tìm thấy dữ liệu";
    public static final String MSG_INVALID_INPUT = "Dữ liệu đầu vào không hợp lệ";
    public static final String MSG_UNAUTHORIZED = "Không có quyền truy cập";
    public static final String MSG_FORBIDDEN = "Truy cập bị từ chối";
    public static final String MSG_VALIDATION_ERROR = "Lỗi xác thực dữ liệu";

    // Pagination
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 100;
    public static final String PAGE_PARAM = "page";
    public static final String SIZE_PARAM = "size";
    public static final String SORT_BY_PARAM = "sortBy";
    public static final String SORT_DIRECTION_PARAM = "sortDir";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";

    // Status
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_DELETED = "DELETED";
    public static final String STATUS_BLOCKED = "BLOCKED";

    // Date/Time Formats
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String TIME_ZONE = "Asia/Ho_Chi_Minh";

    // Security
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final long TOKEN_EXPIRATION = 86400000; // 24 hours in milliseconds

    // File Upload
    public static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    public static final String UPLOAD_DIR = "uploads";
    public static final String[] ALLOWED_FILE_TYPES = {"image/jpeg", "image/png", "image/gif"};

    // Cache
    public static final String CACHE_NAME_FAVORITES = "favorites";
    public static final String CACHE_NAME_VIDEOS = "videos";
    public static final String CACHE_NAME_USERS = "users";

    // Validation Messages
    public static final String VALIDATION_NOT_EMPTY = "không được để trống";
    public static final String VALIDATION_SIZE = "độ dài phải từ {min} đến {max} ký tự";
    public static final String VALIDATION_EMAIL = "email không hợp lệ";
    public static final String VALIDATION_PATTERN = "không đúng định dạng";

    private ApiConstants() {
        // Prevent instantiation
    }
}
