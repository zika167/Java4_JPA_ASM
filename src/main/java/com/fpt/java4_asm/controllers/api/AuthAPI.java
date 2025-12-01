package com.fpt.java4_asm.controllers.api;

import com.fpt.java4_asm.dto.request.LoginRequest;
import com.fpt.java4_asm.dto.response.LoginResponse;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.services.AuthService;
import com.fpt.java4_asm.services.impl.AuthServiceImpl;
import com.fpt.java4_asm.utils.constants.ApiConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * API Controller cho Authentication (Đăng nhập/Đăng xuất)
 * 
 * Endpoints:
 * - POST /api/auth/login - Đăng nhập
 * - POST /api/auth/logout - Đăng xuất
 * - GET /api/auth/validate - Xác thực token
 * - GET /api/auth/admin - Kiểm tra quyền admin
 */
@WebServlet("/api/auth/*")
public class AuthAPI extends BaseApiServlet {
    private final AuthService authService = new AuthServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getPathInfo();
            
            if (pathInfo == null || pathInfo.equals("/")) {
                throw new AppException(Error.INVALID_INPUT, "Endpoint không hợp lệ");
            }

            String[] pathParts = pathInfo.split("/");

            // POST /api/auth/login - Đăng nhập
            if (pathParts.length == 2 && "login".equals(pathParts[1])) {
                LoginRequest request = parseRequestBody(req, LoginRequest.class);
                LoginResponse response = authService.login(request);
                
                resp.setStatus(HttpServletResponse.SC_OK);
                sendSuccessResponse(resp, response, "Đăng nhập thành công");
                return;
            }

            // POST /api/auth/logout - Đăng xuất
            if (pathParts.length == 2 && "logout".equals(pathParts[1])) {
                String token = req.getHeader("Authorization");
                
                if (token == null || token.isEmpty()) {
                    throw new AppException(Error.INVALID_INPUT, "Token không được để trống");
                }

                // Loại bỏ "Bearer " prefix nếu có
                if (token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }

                boolean success = authService.logout(token);
                
                if (success) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    sendSuccessResponse(resp, 1, "Đăng xuất thành công");
                } else {
                    throw new AppException(Error.NOT_FOUND, "Token không hợp lệ");
                }
                return;
            }

            throw new AppException(Error.NOT_FOUND, "Endpoint không tồn tại");

        } catch (AppException e) {
            sendErrorResponse(resp, e);
        } catch (Exception e) {
            sendInternalServerError(resp, "Lỗi server: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getPathInfo();
            
            if (pathInfo == null || pathInfo.equals("/")) {
                throw new AppException(Error.INVALID_INPUT, "Endpoint không hợp lệ");
            }

            String[] pathParts = pathInfo.split("/");

            // GET /api/auth/validate - Xác thực token
            if (pathParts.length == 2 && "validate".equals(pathParts[1])) {
                String token = req.getHeader("Authorization");
                
                if (token == null || token.isEmpty()) {
                    throw new AppException(Error.INVALID_INPUT, "Token không được để trống");
                }

                // Loại bỏ "Bearer " prefix nếu có
                if (token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }

                String userId = authService.validateToken(token);
                
                if (userId != null) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    sendSuccessResponse(resp, userId, "Token hợp lệ");
                } else {
                    throw new AppException(Error.INVALID_CREDENTIALS, "Token không hợp lệ hoặc đã hết hạn");
                }
                return;
            }

            // GET /api/auth/admin - Kiểm tra quyền admin
            if (pathParts.length == 2 && "admin".equals(pathParts[1])) {
                String token = req.getHeader("Authorization");
                
                if (token == null || token.isEmpty()) {
                    throw new AppException(Error.INVALID_INPUT, "Token không được để trống");
                }

                // Loại bỏ "Bearer " prefix nếu có
                if (token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }

                String userId = authService.validateToken(token);
                
                if (userId == null) {
                    throw new AppException(Error.INVALID_CREDENTIALS, "Token không hợp lệ");
                }

                boolean isAdmin = authService.isAdmin(userId);
                
                resp.setStatus(HttpServletResponse.SC_OK);
                sendSuccessResponse(resp, isAdmin, isAdmin ? "Bạn có quyền admin" : "Bạn không có quyền admin");
                return;
            }

            throw new AppException(Error.NOT_FOUND, "Endpoint không tồn tại");

        } catch (AppException e) {
            sendErrorResponse(resp, e);
        } catch (Exception e) {
            sendInternalServerError(resp, "Lỗi server: " + e.getMessage());
        }
    }
}
