package com.fpt.java4_asm.utils.helpers;

import com.fpt.java4_asm.dto.request.VideoRequest;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.models.entities.Video;

/**
 * Lớp tiện ích để kiểm tra tính hợp lệ của các thao tác liên quan đến Video
 */
public class VideoValidation {

    private static final int MAX_TITLE_LENGTH = 255;
    private static final int MAX_KEYWORD_LENGTH = 100;
    private static final int MAX_PAGE_SIZE = 100;

    private VideoValidation() {
        throw new UnsupportedOperationException("Không thể tạo instance của lớp tiện ích");
    }

    /**
     * Validate VideoRequest khi tạo mới
     */
    public static void validateVideoRequest(VideoRequest request) {
        if (request == null) {
            throw new AppException(Error.INVALID_DATA, "Thông tin video không được để trống");
        }
        validateNotEmpty(request.getId(), "Mã video");
        validateNotEmpty(request.getTitle(), "Tiêu đề");
        validateNotEmpty(request.getPoster(), "Đường dẫn poster");
        validateNotEmpty(request.getDescription(), "Mô tả");
        validateNotEmpty(request.getUserId(), "User ID");
        
        // Validate độ dài
        if (request.getTitle().length() > MAX_TITLE_LENGTH) {
            throw new AppException(Error.INVALID_DATA, 
                "Tiêu đề không được vượt quá " + MAX_TITLE_LENGTH + " ký tự");
        }
    }

    /**
     * Validate VideoRequest cho update (không bắt buộc ID)
     */
    public static void validateVideoRequestForUpdate(VideoRequest request) {
        if (request == null) {
            throw new AppException(Error.INVALID_DATA, "Thông tin video không được để trống");
        }
        validateNotEmpty(request.getTitle(), "Tiêu đề");
        validateNotEmpty(request.getPoster(), "Đường dẫn poster");
        validateNotEmpty(request.getDescription(), "Mô tả");
        validateNotEmpty(request.getUserId(), "User ID");
        
        if (request.getTitle().length() > MAX_TITLE_LENGTH) {
            throw new AppException(Error.INVALID_DATA, 
                "Tiêu đề không được vượt quá " + MAX_TITLE_LENGTH + " ký tự");
        }
    }

    /**
     * Validate Video entity
     */
    public static void validateVideo(Video video) {
        if (video == null) {
            throw new AppException(Error.INVALID_DATA, "Thông tin video không được để trống");
        }
        validateNotEmpty(video.getId(), "Mã video");
        validateNotEmpty(video.getTitle(), "Tiêu đề");
        
        if (video.getUser() == null || video.getUser().getId() == null) {
            throw new AppException(Error.INVALID_DATA, "Thông tin người đăng không hợp lệ");
        }
    }

    /**
     * Validate Video ID
     */
    public static void validateVideoId(String id) {
        validateNotEmpty(id, "ID video");
    }

    /**
     * Validate User ID cho tìm kiếm video
     */
    public static void validateUserId(String userId) {
        validateNotEmpty(userId, "User ID");
    }

    /**
     * Validate từ khóa tìm kiếm
     */
    public static void validateSearchKeyword(String keyword) {
        validateNotEmpty(keyword, "Từ khóa tìm kiếm");
        if (keyword.length() > MAX_KEYWORD_LENGTH) {
            throw new AppException(Error.INVALID_DATA, 
                "Từ khóa tìm kiếm không được vượt quá " + MAX_KEYWORD_LENGTH + " ký tự");
        }
    }

    /**
     * Validate pagination
     */
    public static void validatePagination(int page, int size) {
        if (page < 1) {
            throw new AppException(Error.INVALID_DATA, "Số trang phải lớn hơn 0");
        }
        if (size < 1) {
            throw new AppException(Error.INVALID_DATA, "Kích thước trang phải lớn hơn 0");
        }
        if (size > MAX_PAGE_SIZE) {
            throw new AppException(Error.INVALID_DATA, 
                "Kích thước trang tối đa là " + MAX_PAGE_SIZE);
        }
    }

    // === Private helper methods ===
    
    private static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, fieldName + " không được để trống");
        }
    }
}
