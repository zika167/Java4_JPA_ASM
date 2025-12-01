package com.fpt.java4_asm.controllers.api;

import com.fpt.java4_asm.dto.request.ShareRequest;
import com.fpt.java4_asm.dto.response.ShareResponse;
import com.fpt.java4_asm.dto.response.PaginatedResponse;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.services.ShareService;
import com.fpt.java4_asm.services.impl.ShareServiceImpl;
import com.fpt.java4_asm.utils.constants.ApiConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet(ApiConstants.API_SHARES + "/*")
public class ShareAPI extends BaseApiServlet {
    private final ShareService shareService = new ShareServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getPathInfo();
            
            // Xử lý tham số phân trang
            int page = getIntParam(req, "page", 1);
            int size = getIntParam(req, "size", 10);
            
            // GET /api/shares - Lấy tất cả các share (có phân trang)
            if (pathInfo == null || pathInfo.equals("/")) {
                PaginatedResponse<ShareResponse> response = shareService.paginate(page, size);
                sendSuccessResponse(resp, response, ApiConstants.MSG_SUCCESS);
                return;
            }
            
            String[] pathParts = pathInfo.split("/");

            // GET /api/shares/{id} - Lấy thông tin share theo ID
            if (pathParts.length == 2) {
                try {
                    Integer id = Integer.parseInt(pathParts[1]);
                    Optional<ShareResponse> response = shareService.getById(id);
                    if (response.isPresent()) {
                        sendSuccessResponse(resp, response.get(), ApiConstants.MSG_SUCCESS);
                    } else {
                        throw new AppException(Error.NOT_FOUND, "Không tìm thấy share với ID: " + id);
                    }
                    return;
                } catch (NumberFormatException e) {
                    throw new AppException(Error.INVALID_INPUT, "ID share không hợp lệ");
                }
            }
            
            // GET /api/shares/user/{userId} - Lấy danh sách share theo ID người dùng
            if (pathParts.length == 3 && "user".equals(pathParts[1])) {
                String userId = pathParts[2];
                PaginatedResponse<ShareResponse> response = shareService.getByUserId(userId, page, size);
                sendSuccessResponse(resp, response, ApiConstants.MSG_SUCCESS);
                return;
            }
            
            // GET /api/shares/video/{videoId} - Lấy danh sách share theo ID video
            if (pathParts.length == 3 && "video".equals(pathParts[1])) {
                String videoId = pathParts[2];
                PaginatedResponse<ShareResponse> response = shareService.getByVideoId(videoId, page, size);
                sendSuccessResponse(resp, response, ApiConstants.MSG_SUCCESS);
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ShareRequest request = parseRequestBody(req, ShareRequest.class);
            ShareResponse response = shareService.create(request);
            
            resp.setStatus(HttpServletResponse.SC_CREATED);
            sendSuccessResponse(resp, response, ApiConstants.MSG_CREATED);
            
        } catch (AppException e) {
            sendErrorResponse(resp, e);
        } catch (Exception e) {
            sendInternalServerError(resp, "Lỗi khi tạo share: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String[] pathParts = getAndValidatePathParts(req, "ID share là bắt buộc");
            Integer id = Integer.parseInt(pathParts[1]);
            
            ShareRequest request = parseRequestBody(req, ShareRequest.class);
            Optional<ShareResponse> response = shareService.update(id, request);
            
            sendSuccessResponse(resp, response, ApiConstants.MSG_UPDATED);
            
        } catch (NumberFormatException e) {
            sendErrorResponse(resp, new AppException(Error.INVALID_INPUT, "ID share không hợp lệ"));
        } catch (AppException e) {
            sendErrorResponse(resp, e);
        } catch (Exception e) {
            sendInternalServerError(resp, "Lỗi khi cập nhật share: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String[] pathParts = getAndValidatePathParts(req, "ID share là bắt buộc");
            Integer id = Integer.parseInt(pathParts[1]);
            
            shareService.delete(id);
            
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            sendSuccessResponse(resp, 1, "Xóa share thành công");
            
        } catch (NumberFormatException e) {
            sendErrorResponse(resp, new AppException(Error.INVALID_INPUT, "ID share không hợp lệ"));
        } catch (AppException e) {
            sendErrorResponse(resp, e);
        } catch (Exception e) {
            sendInternalServerError(resp, "Lỗi khi xóa share: " + e.getMessage());
        }
    }
    
    private String[] getAndValidatePathParts(HttpServletRequest req, String errorMessage) {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            throw new AppException(Error.INVALID_INPUT, errorMessage);
        }
        
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length != 2) {
            throw new AppException(Error.INVALID_INPUT, "URL không hợp lệ");
        }
        
        return pathParts;
    }
    
    private int getIntParam(HttpServletRequest req, String name, int defaultValue) {
        try {
            String value = req.getParameter(name);
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
