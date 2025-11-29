package com.fpt.java4_asm.repositories;

import com.fpt.java4_asm.models.entities.Comment;

import java.util.List;

/**
 * Repository interface cho Comment entity
 * Kế thừa các phương thức CRUD cơ bản từ BaseRepository
 * và bổ sung các phương thức truy vấn đặc thù cho Comment
 */
public interface CommentRepo extends BaseRepository<Comment, Long>{
    /**
     * Tìm tất cả comment theo UserId
     * 
     * @param userId ID của người dùng
     * @return Danh sách các comment của người dùng
     */
    List<Comment> findByUserId(String userId);

    /**
     * Tìm tất cả comment theo VideoId
     * 
     * @param videoId ID của video
     * @return Danh sách các comment của video
     */
    List<Comment> findByVideoId(String videoId);

    /**
     * Đếm số lượng comment của một video
     * 
     * @param videoId ID của video
     * @return Số lượng comment
     */
    long countByVideoId(String videoId);

    /**
     * Lấy danh sách comment có phân trang
     * 
     * @param page Số trang (bắt đầu từ 0)
     * @param size Số lượng bản ghi trên mỗi trang
     * @return Danh sách các comment
     */
    List<Comment> pages(int page, int size);
}
