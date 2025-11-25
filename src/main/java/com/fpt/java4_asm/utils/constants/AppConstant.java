package com.fpt.java4_asm.utils.constants;

public class AppConstant {
    // Pagination
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";
    
    // Date format
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    
    // File paths
    public static final String UPLOAD_DIR = "uploads";
    
    // Common messages
    public static final String SUCCESS_MESSAGE = "Thành công";
    public static final String ERROR_MESSAGE = "Có lỗi xảy ra";
    public static final String NOT_FOUND_MESSAGE = "Không tìm thấy dữ liệu";
    
    private AppConstant() {
        // Private constructor to prevent instantiation
    }
}