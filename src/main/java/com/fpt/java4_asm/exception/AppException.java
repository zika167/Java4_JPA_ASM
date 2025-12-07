package com.fpt.java4_asm.exception;

/**
 * Custom Exception cho ứng dụng
 * Kế thừa RuntimeException để không cần khai báo throws trong method signature
 * 
 * Mỗi exception chứa:
 * - code: Mã lỗi duy nhất (VD: USER_001, VIDEO_002)
 * - message: Thông báo lỗi chi tiết
 * - httpStatus: HTTP status code tương ứng (400, 404, 500...)
 * 
 * @author Java4 ASM
 */
public class AppException extends RuntimeException {
    private final String code;
    private final String message;
    private final int httpStatus;

    /**
     * Constructor với Error enum
     * Sử dụng message mặc định từ Error
     * 
     * @param error Enum Error chứa thông tin lỗi
     */
    public AppException(Error error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.message = error.getMessage();
        this.httpStatus = mapErrorToHttpStatus(error);
    }

    /**
     * Constructor với Error enum và message tùy chỉnh
     * Cho phép ghi đè message mặc định
     * 
     * @param error Enum Error chứa thông tin lỗi
     * @param customMessage Thông báo lỗi tùy chỉnh
     */
    public AppException(Error error, String customMessage) {
        super(customMessage);
        this.code = error.getCode();
        this.message = customMessage;
        this.httpStatus = mapErrorToHttpStatus(error);
    }

    /**
     * Constructor với Error enum và exception gốc
     * Dùng để wrap exception khác
     * 
     * @param error Enum Error chứa thông tin lỗi
     * @param cause Exception gốc gây ra lỗi
     */
    public AppException(Error error, Throwable cause) {
        super(error.getMessage(), cause);
        this.code = error.getCode();
        this.message = error.getMessage();
        this.httpStatus = mapErrorToHttpStatus(error);
    }

    /**
     * Lấy mã lỗi
     * @return Mã lỗi (VD: USER_001)
     */
    public String getCode() {
        return code;
    }

    /**
     * Lấy thông báo lỗi
     * @return Thông báo lỗi chi tiết
     */
    public String getErrorMessage() {
        return message;
    }

    /**
     * Lấy HTTP status code
     * @return HTTP status code (VD: 400, 404, 500)
     */
    public int getHttpStatus() {
        return httpStatus;
    }

    /**
     * Map Error enum sang HTTP status code
     * @param error Enum Error
     * @return HTTP status code
     */
    private int mapErrorToHttpStatus(Error error) {
        return error.getHttpStatus();
    }
}
