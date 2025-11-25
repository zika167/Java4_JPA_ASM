package com.fpt.java4_asm.controllers.api;

import com.fpt.java4_asm.dto.request.FavoriteRequest;
import com.fpt.java4_asm.dto.response.FavoriteResponse;
import com.fpt.java4_asm.dto.response.PaginatedResponse;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.services.FavoriteService;
import com.fpt.java4_asm.services.impl.FavoriteServiceImpl;
import com.fpt.java4_asm.utils.constants.ApiConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Lớp Controller xử lý các API liên quan đến chức năng Yêu thích (Favorite)
 * 
 * Các chức năng chính:
 * - Lấy danh sách yêu thích
 * - Thêm mới yêu thích
 * - Cập nhật thông tin yêu thích
 * - Xóa yêu thích
 * 
 * @version 1.0
 * @since 2025-11-26
 */
@WebServlet(ApiConstants.API_FAVORITES + "/*")
public class FavoriteAPI extends BaseApiServlet {
    
    // Khởi tạo đối tượng service để xử lý nghiệp vụ liên quan đến yêu thích
    private final FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * Xử lý yêu cầu HTTP GET
     * 
     * Các endpoint hỗ trợ:
     * - GET /api/favorites: Lấy tất cả các mục yêu thích
     * - GET /api/favorites/{id}: Lấy thông tin yêu thích theo ID
     * - GET /api/favorites/user/{userId}: Lấy danh sách yêu thích theo ID người dùng
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
            
            // GET /api/favorites - Lấy tất cả các mục yêu thích (có phân trang)
            if (pathInfo == null || pathInfo.equals("/")) {
                PaginatedResponse<FavoriteResponse> response = favoriteService.paginate(page, size);
                sendSuccessResponse(resp, response, ApiConstants.MSG_SUCCESS);
                return;
            }
            
            String[] pathParts = pathInfo.split("/");

            // GET /api/favorites/{id} - Lấy thông tin yêu thích theo ID
            if (pathParts.length == 2) {
                int id = Integer.parseInt(pathParts[1]);
                Optional<FavoriteResponse> response = favoriteService.getById(id);
                if (response.isPresent()) {
                    sendSuccessResponse(resp, response.get(), ApiConstants.MSG_SUCCESS);
                } else {
                    throw new AppException(Error.NOT_FOUND, "Không tìm thấy yêu thích với ID: " + id);
                }
                return;
            }
            
            // GET /api/favorites/user/{userId} - Lấy danh sách yêu thích theo ID người dùng (có phân trang)
            if (pathParts.length == 3 && "user".equals(pathParts[1])) {
                String userId = pathParts[2];
                PaginatedResponse<FavoriteResponse> response = favoriteService.getByUserId(userId, page, size);
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
     * Xử lý yêu cầu HTTP POST để tạo mới một mục yêu thích
     * 
     * Endpoint: POST /api/favorites
     * 
     * @param req Đối tượng HttpServletRequest chứa dữ liệu yêu thích mới
     * @param resp Đối tượng HttpServletResponse để trả về kết quả
     * @throws ServletException nếu có lỗi xử lý servlet
     * @throws IOException nếu có lỗi nhập/xuất
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            FavoriteRequest request = parseRequestBody(req, FavoriteRequest.class);
            FavoriteResponse response = favoriteService.create(request);
            
            resp.setStatus(HttpServletResponse.SC_CREATED);
            sendSuccessResponse(resp, response, ApiConstants.MSG_CREATED);
            
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Lỗi khi tạo mới yêu thích");
        }
    }

    /**
     * Xử lý yêu cầu HTTP PUT để cập nhật thông tin yêu thích
     * 
     * Endpoint: PUT /api/favorites/{id}
     * 
     * @param req Đối tượng HttpServletRequest chứa dữ liệu cập nhật
     * @param resp Đối tượng HttpServletResponse để trả về kết quả
     * @throws ServletException nếu có lỗi xử lý servlet
     * @throws IOException nếu có lỗi nhập/xuất
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String[] pathParts = getAndValidatePathParts(req, "ID yêu thích là bắt buộc");
            int id = Integer.parseInt(pathParts[1]);
            
            FavoriteRequest request = parseRequestBody(req, FavoriteRequest.class);
            Optional<FavoriteResponse> response = favoriteService.update(id, request);
            
            sendSuccessResponse(resp, response, ApiConstants.MSG_UPDATED);
            
        } catch (NumberFormatException e) {
            throw new AppException(Error.INVALID_INPUT, "ID yêu thích không hợp lệ");
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Lỗi khi cập nhật yêu thích");
        }
    }

    /**
     * Xử lý yêu cầu HTTP DELETE để xóa một mục yêu thích
     * 
     * Endpoint: DELETE /api/favorites/{id}
     * 
     * @param req Đối tượng HttpServletRequest chứa ID yêu thích cần xóa
     * @param resp Đối tượng HttpServletResponse để trả về kết quả
     * @throws ServletException nếu có lỗi xử lý servlet
     * @throws IOException nếu có lỗi nhập/xuất
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String[] pathParts = getAndValidatePathParts(req, "ID yêu thích là bắt buộc");
            int id = Integer.parseInt(pathParts[1]);
            
            favoriteService.delete(id);
            
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            
        } catch (NumberFormatException e) {
            throw new AppException(Error.INVALID_INPUT, "ID yêu thích không hợp lệ");
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Lỗi khi xóa yêu thích");
        }
    }
    
    /**
     * Phương thức trợ giúp để lấy và kiểm tra các phần của đường dẫn
     * @param req Đối tượng HttpServletRequest
     * @param errorMessage Thông báo lỗi nếu không hợp lệ
     * @return Mảng các phần của đường dẫn
     */
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
