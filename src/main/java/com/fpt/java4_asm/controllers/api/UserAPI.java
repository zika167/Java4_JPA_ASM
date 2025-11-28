package com.fpt.java4_asm.controllers.api;

import com.fpt.java4_asm.dto.request.UserRequest;
import com.fpt.java4_asm.dto.request.LoginRequest;
import com.fpt.java4_asm.dto.response.UserResponse;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.services.UserService;
import com.fpt.java4_asm.services.impl.UserServiceImpl;
import com.fpt.java4_asm.utils.constants.ApiConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(ApiConstants.API_USERS + "/*")
public class UserAPI extends BaseApiServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/")) {
                List<UserResponse> response = userService.getAll();
                sendSuccessResponse(resp, response, "Lấy danh sách thành công");
                return;
            }

            String[] pathParts = pathInfo.split("/");
            if (pathParts.length == 2) {
                String id = pathParts[1];
                Optional<UserResponse> response = userService.getById(id);
                if (response.isPresent()) {
                    sendSuccessResponse(resp, response.get(), "Lấy thành công");
                } else {
                    throw new AppException(Error.NOT_FOUND, "User không tìm thấy");
                }
                return;
            }

            throw new AppException(Error.INVALID_INPUT, "URL không hợp lệ");
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Lỗi: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getPathInfo();

            if (pathInfo != null && pathInfo.equals("/login")) {
                LoginRequest loginRequest = parseRequestBody(req, LoginRequest.class);
                Optional<UserResponse> response = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
                sendSuccessResponse(resp, response.get(), "Đăng nhập thành công");
                return;
            }

            UserRequest request = parseRequestBody(req, UserRequest.class);
            UserResponse response = userService.create(request);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            sendSuccessResponse(resp, response, "Tạo user thành công");
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Lỗi: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                throw new AppException(Error.INVALID_INPUT, "User ID là bắt buộc");
            }

            String[] pathParts = pathInfo.split("/");
            if (pathParts.length != 2) {
                throw new AppException(Error.INVALID_INPUT, "URL không hợp lệ");
            }

            String id = pathParts[1];
            UserRequest request = parseRequestBody(req, UserRequest.class);
            Optional<UserResponse> response = userService.update(id, request);
            sendSuccessResponse(resp, response, "Cập nhật thành công");
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Lỗi: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                throw new AppException(Error.INVALID_INPUT, "User ID là bắt buộc");
            }

            String[] pathParts = pathInfo.split("/");
            if (pathParts.length != 2) {
                throw new AppException(Error.INVALID_INPUT, "URL không hợp lệ");
            }

            String id = pathParts[1];
            userService.delete(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Lỗi: " + e.getMessage());
        }
    }
}
