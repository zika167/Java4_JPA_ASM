package com.fpt.java4_asm.controllers.api;

import com.fpt.java4_asm.dto.request.VideoRequest;
import com.fpt.java4_asm.dto.response.PaginatedResponse;
import com.fpt.java4_asm.dto.response.VideoResponse;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.services.VideoService;
import com.fpt.java4_asm.services.impl.VideoServiceImpl;
import com.fpt.java4_asm.utils.constants.ApiConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * API Controller cho Video
 * 
 * Endpoints:
 * - GET /api/videos - Lấy danh sách video (phân trang)
 * - GET /api/videos/{id} - Lấy video theo ID
 * - GET /api/videos/user/{userId} - Lấy video theo user
 * - GET /api/videos/search?q=keyword - Tìm kiếm video
 * - POST /api/videos - Tạo video mới
 * - PUT /api/videos/{id} - Cập nhật video
 * - DELETE /api/videos/{id} - Xóa video
 * - POST /api/videos/{id}/view - Tăng lượt xem
 */
@WebServlet(ApiConstants.API_VIDEOS + "/*")
public class VideoAPI extends BaseApiServlet {
    
    private final VideoService videoService = new VideoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getPathInfo();
            
            // Xử lý tham số phân trang
            int page = getIntParam(req, "page", 1);
            int size = getIntParam(req, "size", 10);
            
            // GET /api/videos - Lấy tất cả (phân trang)
            if (pathInfo == null || pathInfo.equals("/")) {
                PaginatedResponse<VideoResponse> response = videoService.paginate(page, size);
                sendSuccessResponse(resp, response, ApiConstants.MSG_SUCCESS);
                return;
            }
            
            String[] pathParts = pathInfo.split("/");
            
            // GET /api/videos/search?q=keyword
            if (pathParts.length == 2 && "search".equals(pathParts[1])) {
                String keyword = req.getParameter("q");
                if (keyword == null || keyword.trim().isEmpty()) {
                    throw new AppException(Error.INVALID_INPUT, "Từ khóa tìm kiếm không được để trống");
                }
                List<VideoResponse> results = videoService.searchByTitle(keyword);
                sendSuccessResponse(resp, results, ApiConstants.MSG_SUCCESS);
                return;
            }
            
            // GET /api/videos/{id}
            if (pathParts.length == 2) {
                String id = pathParts[1];
                Optional<VideoResponse> response = videoService.getById(id);
                if (response.isPresent()) {
                    sendSuccessResponse(resp, response.get(), ApiConstants.MSG_SUCCESS);
                } else {
                    throw new AppException(Error.NOT_FOUND, "Không tìm thấy video với ID: " + id);
                }
                return;
            }
            
            // GET /api/videos/user/{userId}
            if (pathParts.length == 3 && "user".equals(pathParts[1])) {
                String userId = pathParts[2];
                List<VideoResponse> videos = videoService.getByUserId(userId);
                sendSuccessResponse(resp, videos, ApiConstants.MSG_SUCCESS);
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
            String pathInfo = req.getPathInfo();
            
            // POST /api/videos/{id}/view - Tăng lượt xem
            if (pathInfo != null) {
                String[] pathParts = pathInfo.split("/");
                if (pathParts.length == 3 && "view".equals(pathParts[2])) {
                    String videoId = pathParts[1];
                    videoService.incrementViews(videoId);
                    sendSuccessResponse(resp, null, "Đã tăng lượt xem");
                    return;
                }
            }
            
            // POST /api/videos - Tạo mới
            VideoRequest request = parseRequestBody(req, VideoRequest.class);
            VideoResponse response = videoService.create(request);
            
            resp.setStatus(HttpServletResponse.SC_CREATED);
            sendSuccessResponse(resp, response, ApiConstants.MSG_CREATED);
            
        } catch (AppException e) {
            sendErrorResponse(resp, e);
        } catch (Exception e) {
            sendInternalServerError(resp, "Lỗi khi tạo video: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id = getPathId(req);
            VideoRequest request = parseRequestBody(req, VideoRequest.class);
            Optional<VideoResponse> response = videoService.update(id, request);
            
            sendSuccessResponse(resp, response.orElse(null), ApiConstants.MSG_UPDATED);
            
        } catch (AppException e) {
            sendErrorResponse(resp, e);
        } catch (Exception e) {
            sendInternalServerError(resp, "Lỗi khi cập nhật video: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id = getPathId(req);
            videoService.delete(id);
            
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            
        } catch (AppException e) {
            sendErrorResponse(resp, e);
        } catch (Exception e) {
            sendInternalServerError(resp, "Lỗi khi xóa video: " + e.getMessage());
        }
    }
    
    // === Helper methods ===
    
    private int getIntParam(HttpServletRequest req, String name, int defaultValue) {
        try {
            String value = req.getParameter(name);
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    private String getPathId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            throw new AppException(Error.INVALID_INPUT, "ID video là bắt buộc");
        }
        String[] parts = pathInfo.split("/");
        if (parts.length < 2) {
            throw new AppException(Error.INVALID_INPUT, "ID video không hợp lệ");
        }
        return parts[1];
    }
}
