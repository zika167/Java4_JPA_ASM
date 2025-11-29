package com.fpt.java4_asm.controllers.api;

import com.fpt.java4_asm.dto.request.CommentRequest;
import com.fpt.java4_asm.dto.response.CommentResponse;
import com.fpt.java4_asm.dto.response.PaginatedResponse;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.services.CommentService;
import com.fpt.java4_asm.services.impl.CommentServiceImpl;
import com.fpt.java4_asm.utils.constants.ApiConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Lớp Controller xử lý các API liên quan đến chức năng Comment
 * 
 * Các chức năng chính:
 * - Lấy danh sách comment
 * - Thêm mới comment
 * - Cập nhật thông tin comment
 * - Xóa comment
 * - Lấy comment theo user/video
 * 
 * @version 1.0
 * @since 2025-11-29
 */
@WebServlet(ApiConstants.API_COMMENTS + "/*")
public class CommentAPI extends BaseApiServlet {
    
    // Khởi tạo đối tượng service để xử lý nghiệp vụ liên quan đến comment
    private final CommentService commentService = new CommentServiceImpl();

    /**
     * Xử lý yêu cầu HTTP GET
     * 
     * Các endpoint hỗ trợ:
     * - GET /api/comments: Lấy tất cả các comment (có phân trang)
     * - GET /api/comments/{id}: Lấy thông tin comment theo ID
     * - GET /api/comments/user/{userId}: Lấy danh sách comment theo ID người dùng
     * - GET /api/comments/video/{videoId}: Lấy danh sách comment theo ID video
     * 
     * @param req Đối tượng HttpServletRequest chứa thông tin yêu cầu
     * @param resp Đối tượng HttpServletResponse để trả về kết quả
     * @throws ServletException nếu có lỗi xử lý servlet
     * @throws IOException nếu có lỗi nhập/xuất
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getPathInfo();
            
            // Xử lý tham số phân trang
            int page = 1;
            int size = 10;
            
            try {
                String pageParam = req.getParameter("page");
                String sizeParam = req.getParameter("size");
                
                if (pageParam != null && !pageParam.isEmpty()) {
                    page = Integer.parseInt(pageParam);
                }
                if (sizeParam != null && !sizeParam.isEmpty()) {
                    size = Integer.parseInt(sizeParam);
                }
            } catch (NumberFormatException e) {
                throw new AppException(Error.INVALID_INPUT, "Tham số phân trang không hợp lệ");
            }
            
            // GET /api/comments - Lấy tất cả các comment (có phân trang)
            if (pathInfo == null || pathInfo.equals("/")) {
                PaginatedResponse<CommentResponse> response = commentService.paginate(page, size);
                sendSuccessResponse(resp, response, ApiConstants.MSG_SUCCESS);
                return;
            }
            
            String[] pathParts = pathInfo.split("/");

            // GET /api/comments/{id} - Lấy thông tin comment theo ID
            if (pathParts.length == 2) {
                long id = Long.parseLong(pathParts[1]);
                Optional<CommentResponse> response = commentService.getById(id);
                if (response.isPresent()) {
                    sendSuccessResponse(resp, response.get(), ApiConstants.MSG_SUCCESS);
                } else {
                    throw new AppException(Error.NOT_FOUND, "Không tìm thấy comment với ID: " + id);
                }
                return;
            }
            
            // GET /api/comments/user/{userId} - Lấy danh sách comment theo ID người dùng
            if (pathParts.length == 3 && "user".equals(pathParts[1])) {
                String userId = pathParts[2];
                PaginatedResponse<CommentResponse> response = commentService.getByUserId(userId, page, size);
                sendSuccessResponse(resp, response, ApiConstants.MSG_SUCCESS);
                return;
            }
            
            // GET /api/comments/video/{videoId} - Lấy danh sách comment theo ID video
            if (pathParts.length == 3 && "video".equals(pathParts[1])) {
                String videoId = pathParts[2];
                PaginatedResponse<CommentResponse> response = commentService.getByVideoId(videoId, page, size);
                sendSuccessResponse(resp, response, ApiConstants.MSG_SUCCESS);
                return;
            }
            
            throw new AppException(Error.NOT_FOUND);
            
        } catch (NumberFormatException e) {
            throw new AppException(Error.INVALID_INPUT, "ID không hợp lệ");
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Xử lý yêu cầu HTTP POST để tạo mới một comment
     * 
     * Endpoint: POST /api/comments
     * 
     * @param req Đối tượng HttpServletRequest chứa dữ liệu comment mới
     * @param resp Đối tượng HttpServletResponse để trả về kết quả
     * @throws ServletException nếu có lỗi xử lý servlet
     * @throws IOException nếu có lỗi nhập/xuất
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CommentRequest request = parseRequestBody(req, CommentRequest.class);
            CommentResponse response = commentService.create(request);
            
            resp.setStatus(HttpServletResponse.SC_CREATED);
            sendSuccessResponse(resp, response, ApiConstants.MSG_CREATED);
            
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Lỗi khi tạo mới comment");
        }
    }

    /**
     * Xử lý yêu cầu HTTP PUT để cập nhật thông tin comment
     * 
     * Endpoint: PUT /api/comments/{id}
     * 
     * @param req Đối tượng HttpServletRequest chứa dữ liệu cập nhật
     * @param resp Đối tượng HttpServletResponse để trả về kết quả
     * @throws ServletException nếu có lỗi xử lý servlet
     * @throws IOException nếu có lỗi nhập/xuất
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String[] pathParts = getAndValidatePathParts(req, "ID comment là bắt buộc");
            long id = Long.parseLong(pathParts[1]);
            
            CommentRequest request = parseRequestBody(req, CommentRequest.class);
            Optional<CommentResponse> response = commentService.update(id, request);
            
            sendSuccessResponse(resp, response, ApiConstants.MSG_UPDATED);
            
        } catch (NumberFormatException e) {
            throw new AppException(Error.INVALID_INPUT, "ID comment không hợp lệ");
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Lỗi khi cập nhật comment");
        }
    }

    /**
     * Xử lý yêu cầu HTTP DELETE để xóa một comment
     * 
     * Endpoint: DELETE /api/comments/{id}
     * 
     * @param req Đối tượng HttpServletRequest chứa ID comment cần xóa
     * @param resp Đối tượng HttpServletResponse để trả về kết quả
     * @throws ServletException nếu có lỗi xử lý servlet
     * @throws IOException nếu có lỗi nhập/xuất
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String[] pathParts = getAndValidatePathParts(req, "ID comment là bắt buộc");
            long id = Long.parseLong(pathParts[1]);
            
            commentService.delete(id);
            
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            
        } catch (NumberFormatException e) {
            throw new AppException(Error.INVALID_INPUT, "ID comment không hợp lệ");
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Lỗi khi xóa comment");
        }
    }
    
    /**
     * Trích xuất và kiểm tra tính hợp lệ của các phần trong URL path
     * 
     * @param req Đối tượng HttpServletRequest chứa thông tin yêu cầu
     * @param errorMessage Thông báo lỗi tùy chỉnh nếu path không hợp lệ
     * @return Mảng String chứa các phần của path đã được kiểm tra
     * @throws AppException nếu path không hợp lệ
     */
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
}
