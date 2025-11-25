package com.fpt.java4_asm.utils.constants;

public class MessageConstant {
    // Common messages
    public static final String CREATE_SUCCESS = "Tạo mới thành công";
    public static final String UPDATE_SUCCESS = "Cập nhật thành công";
    public static final String DELETE_SUCCESS = "Xóa thành công";
    public static final String GET_SUCCESS = "Lấy dữ liệu thành công";
    
    // Validation messages
    public static final String VALIDATION_ERROR = "Dữ liệu không hợp lệ";
    public static final String REQUIRED_FIELD = "Trường này là bắt buộc";
    public static final String INVALID_EMAIL = "Email không đúng định dạng";
    public static final String INVALID_PHONE = "Số điện thoại không hợp lệ";
    
    // Auth messages
    public static final String LOGIN_SUCCESS = "Đăng nhập thành công";
    public static final String LOGIN_FAILED = "Sai tên đăng nhập hoặc mật khẩu";
    public static final String UNAUTHORIZED = "Bạn không có quyền truy cập";
    public static final String ACCESS_DENIED = "Truy cập bị từ chối";
    public static final String TOKEN_EXPIRED = "Phiên đăng nhập đã hết hạn";
    
    private MessageConstant() {
        // Private constructor to prevent instantiation
    }
}