package com.fpt.java4_asm.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.java4_asm.dto.response.ApiResponse;
import com.fpt.java4_asm.exception.AppException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Base Servlet class cho tất cả API endpoints
 * Cung cấp các utility method chung
 */
public abstract class BaseApiServlet extends HttpServlet {
    protected static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Ghi response JSON
     */
    protected <T> void sendJsonResponse(HttpServletResponse response, ApiResponse<T> apiResponse, int statusCode) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }

    /**
     * Ghi response thành công
     */
    protected <T> void sendSuccessResponse(HttpServletResponse response, T data, String message) throws IOException {
        sendJsonResponse(response, ApiResponse.success(data, message), HttpServletResponse.SC_OK);
    }

    /**
     * Ghi response thành công (mặc định message)
     */
    protected <T> void sendSuccessResponse(HttpServletResponse response, T data) throws IOException {
        sendSuccessResponse(response, data, "Success");
    }

    /**
     * Ghi response lỗi
     */
    protected void sendErrorResponse(HttpServletResponse response, String message, int statusCode) throws IOException {
        sendJsonResponse(response, ApiResponse.error(message), statusCode);
    }

    /**
     * Ghi response lỗi từ AppException
     */
    protected void sendErrorResponse(HttpServletResponse response, AppException ex) throws IOException {
        sendJsonResponse(response, ApiResponse.error(ex.getErrorMessage()), ex.getHttpStatus());
    }

    /**
     * Ghi response lỗi 500
     */
    protected void sendInternalServerError(HttpServletResponse response, String message) throws IOException {
        sendErrorResponse(response, message, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    /**
     * Ghi response lỗi 400
     */
    protected void sendBadRequest(HttpServletResponse response, String message) throws IOException {
        sendErrorResponse(response, message, HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Ghi response lỗi 404
     */
    protected void sendNotFound(HttpServletResponse response, String message) throws IOException {
        sendErrorResponse(response, message, HttpServletResponse.SC_NOT_FOUND);
    }

    /**
     * Parse JSON request body
     */
    protected <T> T parseRequestBody(jakarta.servlet.http.HttpServletRequest request, Class<T> valueType) throws IOException {
        return objectMapper.readValue(request.getInputStream(), valueType);
    }

    /**
     * Lấy path parameter từ URL
     * Ví dụ: /api/users/123 -> getPathParameter(request, "/api/users/") = "123"
     */
    protected String getPathParameter(jakarta.servlet.http.HttpServletRequest request, String basePath) {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.startsWith(basePath)) {
            return pathInfo.substring(basePath.length());
        }
        return null;
    }
}
