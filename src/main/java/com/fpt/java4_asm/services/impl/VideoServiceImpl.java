package com.fpt.java4_asm.services.impl;

import com.fpt.java4_asm.convert.VideoConvert;
import com.fpt.java4_asm.dto.request.VideoRequest;
import com.fpt.java4_asm.dto.response.PaginatedResponse;
import com.fpt.java4_asm.dto.response.VideoResponse;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.models.entities.User;
import com.fpt.java4_asm.models.entities.Video;
import com.fpt.java4_asm.repositories.UserRepo;
import com.fpt.java4_asm.repositories.VideoRepo;
import com.fpt.java4_asm.repositories.impl.UserRepoImpl;
import com.fpt.java4_asm.repositories.impl.VideoRepoImpl;
import com.fpt.java4_asm.services.VideoService;
import com.fpt.java4_asm.utils.helpers.VideoValidation;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Implementation của VideoService
 */
public class VideoServiceImpl implements VideoService {
    
    private final VideoRepo videoRepo = new VideoRepoImpl();
    private final UserRepo userRepo = new UserRepoImpl();
    private final VideoConvert videoConvert = new VideoConvert();

    @Override
    public VideoResponse create(VideoRequest request) {
        // Validate request
        VideoValidation.validateVideoRequest(request);
        
        // Kiểm tra Video ID đã tồn tại chưa
        if (videoRepo.existsById(request.getId())) {
            throw new AppException(Error.VIDEO_ALREADY_EXISTS, 
                "Video với ID '" + request.getId() + "' đã tồn tại");
        }
        
        // Validate và lấy User từ DB
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new AppException(Error.USER_NOT_FOUND, 
                    "Không tìm thấy người dùng với ID: " + request.getUserId()));
        
        try {
            Video video = videoConvert.toEntity(request, user);
            Video saved = videoRepo.save(video);
            return videoConvert.toResponse(saved);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi tạo video: " + e.getMessage());
        }
    }

    @Override
    public Optional<VideoResponse> update(String id, VideoRequest request) {
        // Validate
        VideoValidation.validateVideoId(id);
        VideoValidation.validateVideoRequestForUpdate(request);
        
        try {
            // Kiểm tra Video tồn tại
            Video existing = videoRepo.findById(id)
                    .orElseThrow(() -> new AppException(Error.VIDEO_NOT_FOUND, 
                        "Không tìm thấy video với ID: " + id));
            
            // Validate và lấy User nếu có thay đổi
            User user = null;
            if (request.getUserId() != null && !request.getUserId().equals(existing.getUser().getId())) {
                user = userRepo.findById(request.getUserId())
                        .orElseThrow(() -> new AppException(Error.USER_NOT_FOUND, 
                            "Không tìm thấy người dùng với ID: " + request.getUserId()));
            }
            
            videoConvert.updateEntity(existing, request, user);
            Video updated = videoRepo.update(existing)
                    .orElseThrow(() -> new AppException(Error.DATABASE_ERROR, "Cập nhật thất bại"));
            
            return Optional.of(videoConvert.toResponse(updated));
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi cập nhật video: " + e.getMessage());
        }
    }

    @Override
    public Optional<VideoResponse> getById(String id) {
        VideoValidation.validateVideoId(id);
        
        try {
            return videoRepo.findById(id).map(videoConvert::toResponse);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi tìm video: " + e.getMessage());
        }
    }

    @Override
    public List<VideoResponse> getAll() {
        try {
            return videoConvert.toResponseList(videoRepo.findAll());
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi lấy danh sách video: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(String id) {
        VideoValidation.validateVideoId(id);
        
        if (!videoRepo.existsById(id)) {
            throw new AppException(Error.VIDEO_NOT_FOUND, "Không tìm thấy video với ID: " + id);
        }
        
        try {
            return videoRepo.deleteById(id);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi xóa video: " + e.getMessage());
        }
    }

    @Override
    public boolean exists(String id) {
        VideoValidation.validateVideoId(id);
        return videoRepo.existsById(id);
    }

    @Override
    public long count() {
        return videoRepo.count();
    }

    @Override
    public PaginatedResponse<VideoResponse> paginate(int page, int size) {
        VideoValidation.validatePagination(page, size);
        
        try {
            long total = videoRepo.count();
            if (total == 0) {
                return PaginatedResponse.of(Collections.emptyList(), page, size, 0);
            }
            
            int pageIndex = page - 1;
            List<Video> videos = videoRepo.pages(pageIndex, size);
            List<VideoResponse> content = videoConvert.toResponseList(videos);
            
            return PaginatedResponse.of(content, page, size, total);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi phân trang: " + e.getMessage());
        }
    }

    @Override
    public List<VideoResponse> getByUserId(String userId) {
        VideoValidation.validateUserId(userId);
        
        // Validate user tồn tại
        if (!userRepo.existsById(userId)) {
            throw new AppException(Error.USER_NOT_FOUND, "Không tìm thấy người dùng với ID: " + userId);
        }
        
        try {
            return videoConvert.toResponseList(videoRepo.findByUserId(userId));
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi tìm video theo user: " + e.getMessage());
        }
    }

    @Override
    public List<VideoResponse> searchByTitle(String keyword) {
        VideoValidation.validateSearchKeyword(keyword);
        
        try {
            return videoConvert.toResponseList(videoRepo.searchByTitle(keyword));
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi tìm kiếm video: " + e.getMessage());
        }
    }

    @Override
    public void incrementViews(String videoId) {
        VideoValidation.validateVideoId(videoId);
        
        if (!videoRepo.existsById(videoId)) {
            throw new AppException(Error.VIDEO_NOT_FOUND, "Không tìm thấy video");
        }
        
        try {
            videoRepo.incrementViews(videoId);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi tăng lượt xem: " + e.getMessage());
        }
    }
}
