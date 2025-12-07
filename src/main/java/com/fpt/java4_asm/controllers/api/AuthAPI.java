package com.fpt.java4_asm.controllers.api;

import com.fpt.java4_asm.dto.request.LoginRequest;
import com.fpt.java4_asm.dto.request.RegisterRequest;
import com.fpt.java4_asm.dto.request.ChangePasswordRequest;
import com.fpt.java4_asm.dto.response.LoginResponse;
import com.fpt.java4_asm.dto.response.UserResponse;
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
                sendSuccessResponse(resp, response, ApiConstants.MSG_LOGIN_SUCCESS);
                return;
            }

            // POST /api/auth/logout - Đăng xuất
            if (pathParts.length == 2 && "logout".equals(pathParts[1])) {
                String token = req.getHeader("Authorization");
                
                if (token == null || token.isEmpty()) {
                    throw new AppException(Error.INVALID_INPUT, ApiConstants.VALIDATION_TOKEN_REQUIRED);
                }

                // Loại bỏ "Bearer " prefix nếu có
                if (token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }

                boolean success = authService.logout(token);
                
                if (success) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    sendSuccessResponse(resp, 1, ApiConstants.MSG_LOGOUT_SUCCESS);
                } else {
                    throw new AppException(Error.NOT_FOUND, "Token không hợp lệ");
                }
                return;
            }

            // POST /api/auth/register - Đăng ký
            if (pathParts.length == 2 && "register".equals(pathParts[1])) {
                RegisterRequest request = parseRequestBody(req, RegisterRequest.class);
                UserResponse response = authService.register(request);
                
                resp.setStatus(HttpServletResponse.SC_CREATED);
                sendSuccessResponse(resp, response, ApiConstants.MSG_REGISTER_SUCCESS);
                return;
            }

            // POST /api/auth/change-password - Đổi mật khẩu
            if (pathParts.length == 2 && "change-password".equals(pathParts[1])) {
                String token = req.getHeader("Authorization");
                
                if (token == null || token.isEmpty()) {
                    throw new AppException(Error.INVALID_INPUT, ApiConstants.VALIDATION_TOKEN_REQUIRED);
                }

                // Loại bỏ "Bearer " prefix nếu có
                if (token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }

                String userId = authService.validateToken(token);
                
                if (userId == null) {
                    throw new AppException(Error.INVALID_CREDENTIALS, "Token không hợp lệ");
                }

                ChangePasswordRequest request = parseRequestBody(req, ChangePasswordRequest.class);
                boolean success = authService.changePassword(userId, request);
                
                if (success) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    sendSuccessResponse(resp, 1, ApiConstants.MSG_CHANGE_PASSWORD_SUCCESS);
                } else {
                    throw new AppException(Error.INTERNAL_SERVER_ERROR, "Đổi mật khẩu thất bại");
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
                    throw new AppException(Error.INVALID_INPUT, ApiConstants.VALIDATION_TOKEN_REQUIRED);
                }

                // Loại bỏ "Bearer " prefix nếu có
                if (token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }

                String userId = authService.validateToken(token);
                
                if (userId != null) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    sendSuccessResponse(resp, userId, ApiConstants.MSG_TOKEN_VALID);
                } else {
                    throw new AppException(Error.INVALID_CREDENTIALS, ApiConstants.MSG_TOKEN_INVALID);
                }
                return;
            }

            // GET /api/auth/admin - Kiểm tra quyền admin
            if (pathParts.length == 2 && "admin".equals(pathParts[1])) {
                String token = req.getHeader("Authorization");
                
                if (token == null || token.isEmpty()) {
                    throw new AppException(Error.INVALID_INPUT, ApiConstants.VALIDATION_TOKEN_REQUIRED);
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
                sendSuccessResponse(resp, isAdmin, isAdmin ? ApiConstants.MSG_ADMIN_ACCESS : ApiConstants.MSG_NO_ADMIN_ACCESS);
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
