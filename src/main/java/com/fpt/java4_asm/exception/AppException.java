package com.fpt.java4_asm.exception;

/**
 * Custom Exception cho ứng dụng
 */
public class AppException extends RuntimeException {
    private final String code;
    private final String message;
    private final int httpStatus;

    public AppException(Error error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.message = error.getMessage();
        this.httpStatus = mapErrorToHttpStatus(error);
    }

    public AppException(Error error, String customMessage) {
        super(customMessage);
        this.code = error.getCode();
        this.message = customMessage;
        this.httpStatus = mapErrorToHttpStatus(error);
    }

    public AppException(Error error, Throwable cause) {
        super(error.getMessage(), cause);
        this.code = error.getCode();
        this.message = error.getMessage();
        this.httpStatus = mapErrorToHttpStatus(error);
    }

    public String getCode() {
        return code;
    }

    public String getErrorMessage() {
        return message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    private int mapErrorToHttpStatus(Error error) {
        return switch (error) {
            case BAD_REQUEST -> 400;
            case UNAUTHORIZED -> 401;
            case FORBIDDEN -> 403;
            case NOT_FOUND -> 404;
            case CONFLICT -> 409;
            case INTERNAL_SERVER_ERROR -> 500;
            default -> 500;
        };
    }
}
