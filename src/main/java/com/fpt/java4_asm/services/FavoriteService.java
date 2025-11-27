package com.fpt.java4_asm.services;

import com.fpt.java4_asm.dto.request.FavoriteRequest;
import com.fpt.java4_asm.dto.response.FavoriteResponse;
import com.fpt.java4_asm.dto.response.PaginatedResponse;
import com.fpt.java4_asm.exception.AppException;

import java.util.List;
import java.util.Optional;

public interface FavoriteService{
    /**
     * Tạo mới entity
     * @param entity Dữ liệu đầu vào Favorite
     * @return Dữ liệu trả về Fatorite
     */
    FavoriteResponse create(FavoriteRequest entity);

    /**
     * Cập nhật entity
     * @param id Id của entity
     * @param entity Dữ liệu đầu vào Favorite
     * @return Dữ liệu trả về Fatorite
     */
    Optional<FavoriteResponse> update(Integer id, FavoriteRequest entity);

    /**
     * Lấy Favorite theo Id
     * @param id Id của entity
     * @return Dữ liệu trả về Fatorite
     */
    Optional<FavoriteResponse> getById(Integer id);

    /**
     * Lấy Favorite theo userId
     * @param userId Id của user
     * @return Dữ liệu trả về danh sách Fatorite
     */
    PaginatedResponse<FavoriteResponse> getByUserId(String userId, int page, int size);

    /**
     * Lấy tất cả entity
     * @return Dữ liệu trả về danh sách Fatorite
     */
    List<FavoriteResponse> getAll();

    /**
     * Xóa entity
     * @param id Id của entity
     * @return boolean
     */
    boolean delete(Integer id);

    /**
     * Kiểm tra entity tồn tại
     * @param id Id của entity
     * @return boolean
     */
    boolean exists(Integer id);

    /**
     * Đếm số lượng entity
     * @return long
     */
    long count();

    /**
     * Lấy danh sách phân trang các mục yêu thích
     * 
     * @param page Số trang cần lấy (bắt đầu từ 1)
     * @param size Số lượng bản ghi trên mỗi trang
     * @return Đối tượng PaginatedResponse chứa thông tin phân trang và danh sách kết quả
     **/
    PaginatedResponse<FavoriteResponse> paginate(int page, int size);
}
