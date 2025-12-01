package com.fpt.java4_asm.services;

import com.fpt.java4_asm.dto.request.ShareRequest;
import com.fpt.java4_asm.dto.response.ShareResponse;
import com.fpt.java4_asm.dto.response.PaginatedResponse;

import java.util.List;
import java.util.Optional;

/**
 * Service interface cho Share
 * Định nghĩa các phương thức nghiệp vụ liên quan đến Share
 */
public interface ShareService {
    /**
     * Tạo mới share
     * 
     * @param request Dữ liệu đầu vào Share
     * @return Dữ liệu trả về Share
     */
    ShareResponse create(ShareRequest request);

    /**
     * Cập nhật share
     * 
     * @param id Id của share
     * @param request Dữ liệu đầu vào Share
     * @return Dữ liệu trả về Share
     */
    Optional<ShareResponse> update(Integer id, ShareRequest request);

    /**
     * Lấy Share theo Id
     * 
     * @param id Id của share
     * @return Dữ liệu trả về Share
     */
    Optional<ShareResponse> getById(Integer id);

    /**
     * Lấy Share theo userId
     * 
     * @param userId Id của user
     * @param page Số trang
     * @param size Kích thước trang
     * @return Dữ liệu trả về danh sách Share có phân trang
     */
    PaginatedResponse<ShareResponse> getByUserId(String userId, int page, int size);

    /**
     * Lấy Share theo videoId
     * 
     * @param videoId Id của video
     * @param page Số trang
     * @param size Kích thước trang
     * @return Dữ liệu trả về danh sách Share có phân trang
     */
    PaginatedResponse<ShareResponse> getByVideoId(String videoId, int page, int size);

    /**
     * Lấy tất cả share
     * 
     * @return Dữ liệu trả về danh sách Share
     */
    List<ShareResponse> getAll();

    /**
     * Xóa share
     * 
     * @param id Id của share
     * @return boolean
     */
    boolean delete(Integer id);

    /**
     * Kiểm tra share tồn tại
     * 
     * @param id Id của share
     * @return boolean
     */
    boolean exists(Integer id);

    /**
     * Đếm số lượng share
     * 
     * @return long
     */
    long count();

    /**
     * Lấy danh sách phân trang các share
     * 
     * @param page Số trang cần lấy (bắt đầu từ 1)
     * @param size Số lượng bản ghi trên mỗi trang
     * @return Đối tượng PaginatedResponse chứa thông tin phân trang và danh sách kết quả
     */
    PaginatedResponse<ShareResponse> paginate(int page, int size);
}
