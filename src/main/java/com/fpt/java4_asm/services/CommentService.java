package com.fpt.java4_asm.services;

import com.fpt.java4_asm.dto.request.CommentRequest;
import com.fpt.java4_asm.dto.response.CommentResponse;
import com.fpt.java4_asm.dto.response.PaginatedResponse;

import java.util.List;
import java.util.Optional;

/**
 * Service interface cho Comment
 * Định nghĩa các phương thức nghiệp vụ liên quan đến Comment
 */
public interface CommentService {
    /**
     * Tạo mới comment
     * 
     * @param request Dữ liệu đầu vào Comment
     * @return Dữ liệu trả về Comment
     */
    CommentResponse create(CommentRequest request);

    /**
     * Cập nhật comment
     * 
     * @param id Id của comment
     * @param request Dữ liệu đầu vào Comment
     * @return Dữ liệu trả về Comment
     */
    Optional<CommentResponse> update(Long id, CommentRequest request);

    /**
     * Lấy Comment theo Id
     * 
     * @param id Id của comment
     * @return Dữ liệu trả về Comment
     */
    Optional<CommentResponse> getById(Long id);

    /**
     * Lấy Comment theo userId
     * 
     * @param userId Id của user
     * @param page Số trang
     * @param size Kích thước trang
     * @return Dữ liệu trả về danh sách Comment có phân trang
     */
    PaginatedResponse<CommentResponse> getByUserId(String userId, int page, int size);

    /**
     * Lấy Comment theo videoId
     * 
     * @param videoId Id của video
     * @param page Số trang
     * @param size Kích thước trang
     * @return Dữ liệu trả về danh sách Comment có phân trang
     */
    PaginatedResponse<CommentResponse> getByVideoId(String videoId, int page, int size);

    /**
     * Lấy tất cả comment
     * 
     * @return Dữ liệu trả về danh sách Comment
     */
    List<CommentResponse> getAll();

    /**
     * Xóa comment
     * 
     * @param id Id của comment
     * @return boolean
     */
    boolean delete(Long id);

    /**
     * Kiểm tra comment tồn tại
     * 
     * @param id Id của comment
     * @return boolean
     */
    boolean exists(Long id);

    /**
     * Đếm số lượng comment
     * 
     * @return long
     */
    long count();

    /**
     * Lấy danh sách phân trang các comment
     * 
     * @param page Số trang cần lấy (bắt đầu từ 1)
     * @param size Số lượng bản ghi trên mỗi trang
     * @return Đối tượng PaginatedResponse chứa thông tin phân trang và danh sách kết quả
     */
    PaginatedResponse<CommentResponse> paginate(int page, int size);
}
