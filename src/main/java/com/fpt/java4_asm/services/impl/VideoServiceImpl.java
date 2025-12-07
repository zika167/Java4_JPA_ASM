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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Triển khai cụ thể của VideoService
 * Xử lý các nghiệp vụ liên quan đến Video: CRUD, tìm kiếm, phân trang
 * 
 * @author Java4 ASM
 */
public class VideoServiceImpl implements VideoService {
    private static final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);
    
    // Repository để thao tác với database
    private final VideoRepo videoRepo = new VideoRepoImpl();
    private final UserRepo userRepo = new UserRepoImpl();
    
    // Converter để chuyển đổi giữa Entity và DTO
    private final VideoConvert videoConvert = new VideoConvert();

    /**
     * Tạo video mới
     * 
     * @param request Thông tin video cần tạo
     * @return VideoResponse chứa thông tin video đã tạo
     * @throws AppException nếu video ID đã tồn tại hoặc user không tồn tại
     */
    @Override
    public VideoResponse create(VideoRequest request) {
        log.debug("Tạo video mới: {}", request.getId());
        VideoValidation.validateVideoRequest(request);
        
        if (videoRepo.existsById(request.getId())) {
            throw new AppException(Error.VIDEO_ALREADY_EXISTS, 
                "Video với ID '" + request.getId() + "' đã tồn tại");
        }
        
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new AppException(Error.USER_NOT_FOUND, 
                    "Không tìm thấy người dùng với ID: " + request.getUserId()));
        
        try {
            Video video = videoConvert.toEntity(request, user);
            Video saved = videoRepo.save(video);
            log.info("Tạo video thành công: {}", saved.getId());
            return videoConvert.toResponse(saved);
        } catch (AppException e) {
            log.warn("Tạo video thất bại: {}", e.getErrorMessage());
            throw e;
        } catch (Exception e) {
            log.error("Lỗi khi tạo video: {}", e.getMessage(), e);
            throw new AppException(Error.VIDEO_CREATE_FAILED, "Lỗi khi tạo video: " + e.getMessage());
        }
    }

    /**
     * Cập nhật thông tin video
     * 
     * @param id ID của video cần cập nhật
     * @param request Thông tin mới cần cập nhật
     * @return Optional chứa VideoResponse nếu cập nhật thành công
     * @throws AppException nếu video không tồn tại hoặc có lỗi khi cập nhật
     */
    @Override
    public Optional<VideoResponse> update(String id, VideoRequest request) {
        log.debug("Cập nhật video: {}", id);
        VideoValidation.validateVideoId(id);
        VideoValidation.validateVideoRequestForUpdate(request);
        
        try {
            Video existing = videoRepo.findById(id)
                    .orElseThrow(() -> new AppException(Error.VIDEO_NOT_FOUND, 
                        "Không tìm thấy video với ID: " + id));
            
            User user = null;
            if (request.getUserId() != null && !request.getUserId().equals(existing.getUser().getId())) {
                user = userRepo.findById(request.getUserId())
                        .orElseThrow(() -> new AppException(Error.USER_NOT_FOUND, 
                            "Không tìm thấy người dùng với ID: " + request.getUserId()));
            }
            
            videoConvert.updateEntity(existing, request, user);
            Video updated = videoRepo.update(existing)
                    .orElseThrow(() -> new AppException(Error.VIDEO_UPDATE_FAILED, "Cập nhật thất bại"));
            
            log.info("Cập nhật video thành công: {}", id);
            return Optional.of(videoConvert.toResponse(updated));
        } catch (AppException e) {
            log.warn("Cập nhật video thất bại {}: {}", id, e.getErrorMessage());
            throw e;
        } catch (Exception e) {
            log.error("Lỗi khi cập nhật video: {}", e.getMessage(), e);
            throw new AppException(Error.VIDEO_UPDATE_FAILED, "Lỗi khi cập nhật video: " + e.getMessage());
        }
    }

    /**
     * Lấy thông tin video theo ID
     * 
     * @param id ID của video cần tìm
     * @return Optional chứa VideoResponse nếu tìm thấy
     */
    @Override
    public Optional<VideoResponse> getById(String id) {
        VideoValidation.validateVideoId(id);
        
        try {
            return videoRepo.findById(id).map(videoConvert::toResponse);
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi tìm video: " + e.getMessage());
        }
    }

    /**
     * Lấy danh sách tất cả video
     * 
     * @return Danh sách VideoResponse
     */
    @Override
    public List<VideoResponse> getAll() {
        try {
            return videoConvert.toResponseList(videoRepo.findAll());
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi lấy danh sách video: " + e.getMessage());
        }
    }

    /**
     * Xóa video theo ID
     * 
     * @param id ID của video cần xóa
     * @return true nếu xóa thành công
     * @throws AppException nếu video không tồn tại
     */
    @Override
    public boolean delete(String id) {
        log.debug("Xóa video: {}", id);
        VideoValidation.validateVideoId(id);
        
        if (!videoRepo.existsById(id)) {
            throw new AppException(Error.VIDEO_NOT_FOUND, "Không tìm thấy video với ID: " + id);
        }
        
        try {
            boolean result = videoRepo.deleteById(id);
            log.info("Xóa video thành công: {}", id);
            return result;
        } catch (Exception e) {
            log.error("Lỗi khi xóa video: {}", e.getMessage(), e);
            throw new AppException(Error.VIDEO_DELETE_FAILED, "Lỗi khi xóa video: " + e.getMessage());
        }
    }

    /**
     * Kiểm tra video có tồn tại theo ID
     * 
     * @param id ID của video cần kiểm tra
     * @return true nếu tồn tại, false nếu không
     */
    @Override
    public boolean exists(String id) {
        VideoValidation.validateVideoId(id);
        return videoRepo.existsById(id);
    }

    /**
     * Đếm tổng số video trong hệ thống
     * 
     * @return Tổng số video
     */
    @Override
    public long count() {
        return videoRepo.count();
    }

    /**
     * Lấy danh sách video theo phân trang
     * 
     * @param page Số trang (bắt đầu từ 1)
     * @param size Số lượng bản ghi mỗi trang
     * @return PaginatedResponse chứa danh sách video và thông tin phân trang
     */
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

    /**
     * Lấy danh sách video theo ID người dùng
     * 
     * @param userId ID của người dùng
     * @return Danh sách video của người dùng
     * @throws AppException nếu user không tồn tại
     */
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

    /**
     * Tìm kiếm video theo tiêu đề
     * 
     * @param keyword Từ khóa tìm kiếm
     * @return Danh sách video phù hợp
     */
    @Override
    public List<VideoResponse> searchByTitle(String keyword) {
        VideoValidation.validateSearchKeyword(keyword);
        
        try {
            return videoConvert.toResponseList(videoRepo.searchByTitle(keyword));
        } catch (Exception e) {
            throw new AppException(Error.DATABASE_ERROR, "Lỗi khi tìm kiếm video: " + e.getMessage());
        }
    }

    /**
     * Tăng lượt xem cho video
     * 
     * @param videoId ID của video cần tăng lượt xem
     * @throws AppException nếu video không tồn tại
     */
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
