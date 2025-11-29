package com.fpt.java4_asm.repositories;

import com.fpt.java4_asm.models.entities.Video;
import java.util.List;

/**
 * Repository interface cho Video entity
 */
public interface VideoRepo extends BaseRepository<Video, String> {
    
    /**
     * Tìm video theo userId
     */
    List<Video> findByUserId(String userId);
    
    /**
     * Tìm video theo title (tìm kiếm)
     */
    List<Video> searchByTitle(String keyword);
    
    /**
     * Lấy danh sách video active
     */
    List<Video> findAllActive();
    
    /**
     * Phân trang video
     */
    List<Video> pages(int page, int size);
    
    /**
     * Tăng lượt xem
     */
    void incrementViews(String videoId);
}
