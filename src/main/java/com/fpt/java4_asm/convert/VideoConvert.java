package com.fpt.java4_asm.convert;

import com.fpt.java4_asm.dto.request.VideoRequest;
import com.fpt.java4_asm.dto.response.VideoResponse;
import com.fpt.java4_asm.models.entities.User;
import com.fpt.java4_asm.models.entities.Video;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter giữa Video Entity và DTO
 */
public class VideoConvert {

    /**
     * Convert VideoRequest -> Video Entity (tạo mới)
     * Lưu ý: User chỉ set ID, cần validate user tồn tại ở Service
     */
    public Video toEntity(VideoRequest request) {
        Video video = new Video();
        video.setId(request.getId());
        video.setTitle(request.getTitle());
        video.setPoster(request.getPoster());
        video.setDescription(request.getDescription());
        video.setActive(request.isActive());
        video.setViews(0);
        
        // Set user reference (chỉ set ID, JPA sẽ tự map)
        User user = new User();
        user.setId(request.getUserId());
        video.setUser(user);
        
        return video;
    }

    /**
     * Convert VideoRequest -> Video Entity với User đã load từ DB
     */
    public Video toEntity(VideoRequest request, User user) {
        Video video = new Video();
        video.setId(request.getId());
        video.setTitle(request.getTitle());
        video.setPoster(request.getPoster());
        video.setDescription(request.getDescription());
        video.setActive(request.isActive());
        video.setViews(0);
        video.setUser(user);
        
        return video;
    }

    /**
     * Update Video Entity từ VideoRequest
     */
    public void updateEntity(Video video, VideoRequest request) {
        video.setTitle(request.getTitle());
        video.setPoster(request.getPoster());
        video.setDescription(request.getDescription());
        video.setActive(request.isActive());
    }

    /**
     * Update Video Entity từ VideoRequest với User mới
     */
    public void updateEntity(Video video, VideoRequest request, User user) {
        video.setTitle(request.getTitle());
        video.setPoster(request.getPoster());
        video.setDescription(request.getDescription());
        video.setActive(request.isActive());
        if (user != null) {
            video.setUser(user);
        }
    }

    /**
     * Convert Video Entity -> VideoResponse
     */
    public VideoResponse toResponse(Video video) {
        VideoResponse response = new VideoResponse();
        response.setId(video.getId());
        response.setTitle(video.getTitle());
        response.setPoster(video.getPoster());
        response.setViews(video.getViews());
        response.setDescription(video.getDescription());
        response.setActive(video.isActive());
        response.setCreatedDate(video.getCreatedDate());
        
        // Set author name từ User.fullname
        if (video.getUser() != null) {
            response.setAuthorName(video.getUser().getFullname());
        }
        
        return response;
    }

    /**
     * Convert List<Video> -> List<VideoResponse>
     */
    public List<VideoResponse> toResponseList(List<Video> videos) {
        return videos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
