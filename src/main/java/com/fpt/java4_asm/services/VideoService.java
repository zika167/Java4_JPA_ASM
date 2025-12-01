package com.fpt.java4_asm.services;

import com.fpt.java4_asm.dto.request.VideoRequest;
import com.fpt.java4_asm.dto.response.PaginatedResponse;
import com.fpt.java4_asm.dto.response.VideoResponse;

import java.util.List;
import java.util.Optional;

/**
 * Service interface cho Video
 */
public interface VideoService {
    
    VideoResponse create(VideoRequest request);
    
    Optional<VideoResponse> update(String id, VideoRequest request);
    
    Optional<VideoResponse> getById(String id);
    
    List<VideoResponse> getAll();
    
    boolean delete(String id);
    
    boolean exists(String id);
    
    long count();
    
    PaginatedResponse<VideoResponse> paginate(int page, int size);
    
    /**
     * Lấy video theo userId
     */
    List<VideoResponse> getByUserId(String userId);
    
    /**
     * Tìm kiếm video theo title
     */
    List<VideoResponse> searchByTitle(String keyword);
    
    /**
     * Tăng lượt xem video
     */
    void incrementViews(String videoId);
}
