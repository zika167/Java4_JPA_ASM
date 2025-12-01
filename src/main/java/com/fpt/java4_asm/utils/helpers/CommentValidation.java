package com.fpt.java4_asm.utils.helpers;

import com.fpt.java4_asm.dto.request.CommentRequest;
import com.fpt.java4_asm.exception.AppException;
import com.fpt.java4_asm.exception.Error;
import com.fpt.java4_asm.models.entities.Comment;

/**
 * Lớp tiện ích để kiểm tra tính hợp lệ của các thao tác liên quan đến Comment.
 * 
 * Cung cấp các phương thức tĩnh để kiểm tra nhiều khía cạnh khác nhau của các thực thể Comment
 * và các DTO liên quan, đảm bảo tính toàn vẹn dữ liệu và các quy tắc nghiệp vụ
 * trước khi xử lý các thao tác liên quan đến Comment.
 */
public class CommentValidation {
    
    /**
     * Constructor riêng tư để ngăn chặn việc tạo thể hiện của lớp tiện ích này.
     */
    private CommentValidation() {
        throw new UnsupportedOperationException("Không thể tạo thể hiện của lớp tiện ích");
    }
    
    /**
     * Kiểm tra tính hợp lệ của đối tượng CommentRequest.
     * 
     * @param request Đối tượng CommentRequest cần kiểm tra
     * @throws AppException với mã lỗi INVALID_DATA nếu request không hợp lệ
     */
    public static void validateCommentRequest(CommentRequest request) {
        if (request == null) {
            throw new AppException(Error.INVALID_DATA, "Thông tin comment không được để trống");
        }

        // Validate User
        validateRequiredField(request.getUser(), "Người dùng");
        if (request.getUser() == null || request.getUser().getId() == null) {
            throw new AppException(Error.INVALID_DATA, "Thông tin người dùng không hợp lệ");
        }

        // Validate Video
        validateRequiredField(request.getVideo(), "Video");
        if (request.getVideo() == null || request.getVideo().getId() == null) {
            throw new AppException(Error.INVALID_DATA, "Thông tin video không hợp lệ");
        }

        // Validate Content
        validateRequiredField(request.getContent(), "Nội dung");
        if (request.getContent().trim().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, "Nội dung comment không được để trống");
        }
        
        // Giới hạn độ dài content (tùy yêu cầu)
        if (request.getContent().length() > 5000) {
            throw new AppException(Error.INVALID_DATA, "Nội dung comment không được vượt quá 5000 ký tự");
        }
    }

    /**
     * Kiểm tra tính hợp lệ của một đối tượng Comment.
     * 
     * @param comment Đối tượng Comment cần kiểm tra
     * @throws AppException với mã lỗi INVALID_DATA nếu comment không hợp lệ
     */
    public static void validateComment(Comment comment) {
        if (comment == null) {
            throw new AppException(Error.INVALID_DATA, "Thông tin comment không được để trống");
        }

        validateRequiredField(comment.getUser(), "Người dùng");
        validateRequiredField(comment.getVideo(), "Video");
        validateRequiredField(comment.getContent(), "Nội dung");

        if (comment.getUser().getId() == null) {
            throw new AppException(Error.INVALID_DATA, "ID người dùng không hợp lệ");
        }

        if (comment.getVideo().getId() == null) {
            throw new AppException(Error.INVALID_DATA, "ID video không hợp lệ");
        }

        if (comment.getContent().trim().isEmpty()) {
            throw new AppException(Error.INVALID_DATA, "Nội dung comment không được để trống");
        }

        // Tự động set createdDate và updatedDate nếu null
        if (comment.getCreatedDate() == null) {
            comment.setCreatedDate(new java.util.Date());
        }
        if (comment.getUpdatedDate() == null) {
            comment.setUpdatedDate(new java.util.Date());
        }
    }

    /**
     * Kiểm tra tính hợp lệ của ID Comment.
     * 
     * @param id ID cần kiểm tra
     * @throws AppException với mã lỗi INVALID_DATA nếu ID là null hoặc nhỏ hơn 1
     */
    public static void validateCommentId(Long id) {
        if (id == null || id <= 0) {
            throw new AppException(Error.INVALID_DATA, "ID comment không hợp lệ");
        }
    }

    /**
     * Kiểm tra tính hợp lệ của các tham số phân trang.
     * 
     * @param page Số trang (bắt đầu từ 1)
     * @param size Số lượng phần tử trên mỗi trang
     * @throws AppException với mã lỗi INVALID_DATA nếu tham số không hợp lệ
     */
    public static void validatePagination(int page, int size) {
        if (page < 1) {
            throw new AppException(Error.INVALID_DATA, "Số trang phải lớn hơn 0");
        }
        if (size < 1) {
            throw new AppException(Error.INVALID_DATA, "Kích thước trang phải lớn hơn 0");
        }
        // Giới hạn kích thước trang tối đa
        int maxPageSize = 100;
        if (size > maxPageSize) {
            throw new AppException(Error.INVALID_DATA, 
                String.format("Kích thước trang tối đa là %d", maxPageSize));
        }
    }

    /**
     * Kiểm tra một trường bắt buộc không được null
     * 
     * @param field Trường cần kiểm tra
     * @param fieldName Tên trường (dùng trong thông báo lỗi)
     * @param <T> Kiểu dữ liệu của trường
     * @throws AppException nếu trường là null
     */
    private static <T> void validateRequiredField(T field, String fieldName) {
        if (field == null) {
            throw new AppException(Error.INVALID_DATA, fieldName + " không được để trống");
        }
    }
}
