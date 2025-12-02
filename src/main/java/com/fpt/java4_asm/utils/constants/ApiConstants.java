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
    public static final String EMAIL_PATH_VARIABLE = "/{email}";
    public static final String EMAIL_PATH = "/email" + EMAIL_PATH_VARIABLE;
    public static final String SEARCH_PATH = "/search";
    public static final String VIEW_PATH = "/view";

    // Auth Path Variables
    public static final String AUTH_LOGIN = "/login";
    public static final String AUTH_LOGOUT = "/logout";
    public static final String AUTH_REGISTER = "/register";
    public static final String AUTH_CHANGE_PASSWORD = "/change-password";
    public static final String AUTH_VALIDATE = "/validate";
    public static final String AUTH_ADMIN = "/admin";

    // Complete API Endpoints
    public static final String API_USERS_BY_ID = API_USERS + ID_PATH_VARIABLE;
    public static final String API_USERS_BY_EMAIL = API_USERS + EMAIL_PATH;
    public static final String API_USERS_PAGINATED = API_USERS + "?page={page}&size={size}";

    public static final String API_VIDEOS_BY_ID = API_VIDEOS + ID_PATH_VARIABLE;
    public static final String API_VIDEOS_BY_USER = API_VIDEOS + USER_ID_PATH;
    public static final String API_VIDEOS_BY_USER_PAGINATED = API_VIDEOS_BY_USER + "?page={page}&size={size}";
    public static final String API_VIDEOS_SEARCH = API_VIDEOS + SEARCH_PATH;
    public static final String API_VIDEOS_VIEW = API_VIDEOS + ID_PATH_VARIABLE + VIEW_PATH;
    public static final String API_VIDEOS_PAGINATED = API_VIDEOS + "?page={page}&size={size}";

    public static final String API_FAVORITES_BY_ID = API_FAVORITES + ID_PATH_VARIABLE;
    public static final String API_FAVORITES_BY_USER = API_FAVORITES + USER_ID_PATH;
    public static final String API_FAVORITES_BY_USER_PAGINATED = API_FAVORITES_BY_USER + "?page={page}&size={size}";
    public static final String API_FAVORITES_PAGINATED = API_FAVORITES + "?page={page}&size={size}";

    public static final String API_COMMENTS_BY_ID = API_COMMENTS + ID_PATH_VARIABLE;
    public static final String API_COMMENTS_BY_USER = API_COMMENTS + USER_ID_PATH;
    public static final String API_COMMENTS_BY_USER_PAGINATED = API_COMMENTS_BY_USER + "?page={page}&size={size}";
    public static final String API_COMMENTS_BY_VIDEO = API_COMMENTS + VIDEO_ID_PATH;
    public static final String API_COMMENTS_BY_VIDEO_PAGINATED = API_COMMENTS_BY_VIDEO + "?page={page}&size={size}";
    public static final String API_COMMENTS_PAGINATED = API_COMMENTS + "?page={page}&size={size}";

    public static final String API_SHARES_BY_ID = API_SHARES + ID_PATH_VARIABLE;
    public static final String API_SHARES_BY_USER = API_SHARES + USER_ID_PATH;
    public static final String API_SHARES_BY_USER_PAGINATED = API_SHARES_BY_USER + "?page={page}&size={size}";
    public static final String API_SHARES_BY_VIDEO = API_SHARES + VIDEO_ID_PATH;
    public static final String API_SHARES_BY_VIDEO_PAGINATED = API_SHARES_BY_VIDEO + "?page={page}&size={size}";
    public static final String API_SHARES_PAGINATED = API_SHARES + "?page={page}&size={size}";

    // Auth Endpoints
    public static final String API_AUTH_LOGIN = API_AUTH + AUTH_LOGIN;
    public static final String API_AUTH_LOGOUT = API_AUTH + AUTH_LOGOUT;
    public static final String API_AUTH_REGISTER = API_AUTH + AUTH_REGISTER;
    public static final String API_AUTH_CHANGE_PASSWORD = API_AUTH + AUTH_CHANGE_PASSWORD;
    public static final String API_AUTH_VALIDATE = API_AUTH + AUTH_VALIDATE;
    public static final String API_AUTH_ADMIN = API_AUTH + AUTH_ADMIN;
    
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
    public static final String MSG_REGISTER_SUCCESS = "Đăng ký thành công";
    public static final String MSG_CHANGE_PASSWORD_SUCCESS = "Đổi mật khẩu thành công";
    public static final String MSG_LOGOUT_SUCCESS = "Đăng xuất thành công";
    public static final String MSG_LOGIN_SUCCESS = "Đăng nhập thành công";
    public static final String MSG_TOKEN_VALID = "Token hợp lệ";
    public static final String MSG_TOKEN_INVALID = "Token không hợp lệ hoặc đã hết hạn";
    public static final String MSG_ADMIN_ACCESS = "Bạn có quyền admin";
    public static final String MSG_NO_ADMIN_ACCESS = "Bạn không có quyền admin";

    // Validation Messages
    public static final String VALIDATION_REQUIRED = "không được để trống";
    public static final String VALIDATION_EMAIL_EXISTS = "Email đã được sử dụng";
    public static final String VALIDATION_ID_EXISTS = "ID đã tồn tại";
    public static final String VALIDATION_PASSWORD_MISMATCH = "Mật khẩu xác nhận không khớp";
    public static final String VALIDATION_PASSWORD_DIFFERENT = "Mật khẩu mới phải khác mật khẩu cũ";
    public static final String VALIDATION_USER_NOT_FOUND = "Người dùng không tồn tại";
    public static final String VALIDATION_OLD_PASSWORD_WRONG = "Mật khẩu cũ không chính xác";
    public static final String VALIDATION_TOKEN_REQUIRED = "Token không được để trống";
    public static final String VALIDATION_INVALID_TOKEN = "Token không hợp lệ";
    public static final String VALIDATION_ENDPOINT_NOT_FOUND = "Endpoint không tồn tại";
    public static final String VALIDATION_DATABASE_ERROR = "Lỗi cơ sở dữ liệu";
    public static final String VALIDATION_INVALID_CREDENTIALS = "Email hoặc password không chính xác";
    public static final String VALIDATION_DATA_EMPTY = "Dữ liệu không được để trống";
    public static final String VALIDATION_NOT_EMPTY = "không được để trống";
    public static final String VALIDATION_SIZE = "độ dài phải từ {min} đến {max} ký tự";
    public static final String VALIDATION_EMAIL = "email không hợp lệ";
    public static final String VALIDATION_PATTERN = "không đúng định dạng";

    // Field Names
    public static final String FIELD_ID = "ID";
    public static final String FIELD_FULL_NAME = "Họ tên";
    public static final String FIELD_EMAIL = "Email";
    public static final String FIELD_PASSWORD = "Mật khẩu";
    public static final String FIELD_CONFIRM_PASSWORD = "Xác nhận mật khẩu";
    public static final String FIELD_OLD_PASSWORD = "Mật khẩu cũ";
    public static final String FIELD_NEW_PASSWORD = "Mật khẩu mới";
    public static final String FIELD_ADMIN = "Quyền admin";
    public static final String FIELD_TOKEN = "Token";

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

    // Query Parameters
    public static final String SEARCH_QUERY_PARAM = "q";
    public static final String EMAIL_PARAM = "emails";

    // HTTP Methods
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";

    // HTTP Status Codes
    public static final int STATUS_OK = 200;
    public static final int STATUS_CREATED = 201;
    public static final int STATUS_NO_CONTENT = 204;
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_UNAUTHORIZED = 401;
    public static final int STATUS_FORBIDDEN = 403;
    public static final int STATUS_NOT_FOUND = 404;
    public static final int STATUS_CONFLICT = 409;
    public static final int STATUS_INTERNAL_SERVER_ERROR = 500;

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

    // Content Types
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_XML = "application/xml";
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    public static final String CONTENT_TYPE_MULTIPART = "multipart/form-data";

    // Headers
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String HEADER_ORIGIN = "Origin";
    public static final String HEADER_REFERER = "Referer";

    // File Upload
    public static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    public static final String UPLOAD_DIR = "uploads";
    public static final String[] ALLOWED_FILE_TYPES = {"image/jpeg", "image/png", "image/gif"};

    // Cache
    public static final String CACHE_NAME_FAVORITES = "favorites";
    public static final String CACHE_NAME_VIDEOS = "videos";
    public static final String CACHE_NAME_USERS = "users";

    private ApiConstants() {
        // Prevent instantiation
    }
}
