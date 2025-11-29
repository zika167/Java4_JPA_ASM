package com.fpt.java4_asm.convert;

import com.fpt.java4_asm.dto.request.CommentRequest;
import com.fpt.java4_asm.dto.response.CommentResponse;
import com.fpt.java4_asm.dto.response.PaginatedResponse;
import com.fpt.java4_asm.models.entities.Comment;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Lớp chuyển đổi dữ liệu giữa các đối tượng Comment entity và CommentResponse/CommentRequest DTO
 * 
 * Mô tả:
 * - Thực hiện ánh xạ dữ liệu giữa các lớp entity và DTO
 * - Cung cấp các phương thức chuyển đổi cho từng đối tượng và danh sách đối tượng
 */
public class CommentConvert {

    /**
     * Chuyển đổi từ đối tượng Comment entity sang đối tượng CommentResponse DTO
     * 
     * @param comment Đối tượng Comment cần chuyển đổi
     * @return Đối tượng CommentResponse đã được chuyển đổi, trả về null nếu đầu vào là null
     */
    public CommentResponse toResponse(Comment comment) {
        if (comment == null) {
            return null;
        }
        
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setUser(comment.getUser());
        response.setVideo(comment.getVideo());
        response.setContent(comment.getContent());
        response.setCreatedDate(comment.getCreatedDate());
        response.setUpdatedDate(comment.getUpdatedDate());
        
        return response;
    }

    /**
     * Chuyển đổi từ đối tượng CommentRequest DTO sang đối tượng Comment entity
     * 
     * @param request Đối tượng CommentRequest cần chuyển đổi
     * @return Đối tượng Comment đã được tạo mới, trả về null nếu đầu vào là null
     */
    public Comment toEntity(CommentRequest request) {
        if (request == null) {
            return null;
        }
        
        Comment comment = new Comment();
        comment.setUser(request.getUser());
        comment.setVideo(request.getVideo());
        comment.setContent(request.getContent());
        comment.setCreatedDate(request.getCreatedDate() != null ? request.getCreatedDate() : new java.util.Date());
        comment.setUpdatedDate(request.getUpdatedDate() != null ? request.getUpdatedDate() : new java.util.Date());
        
        return comment;
    }

    /**
     * Cập nhật thông tin của một đối tượng Comment đã tồn tại từ dữ liệu trong CommentRequest
     * 
     * @param comment Đối tượng Comment cần cập nhật
     * @param request Đối tượng CommentRequest chứa dữ liệu mới
     * @return Đối tượng Comment đã được cập nhật, trả về nguyên bản nếu một trong hai tham số là null
     */
    public Comment toEntity(Comment comment, CommentRequest request) {
        if (comment == null || request == null) {
            return comment;
        }
        
        comment.setUser(request.getUser() != null ? request.getUser() : comment.getUser());
        comment.setVideo(request.getVideo() != null ? request.getVideo() : comment.getVideo());
        comment.setContent(request.getContent() != null ? request.getContent() : comment.getContent());
        comment.setUpdatedDate(new java.util.Date());
        
        return comment;
    }

    /**
     * Chuyển đổi một danh sách các đối tượng Comment thành danh sách CommentResponse
     * 
     * @param comments Danh sách các đối tượng Comment cần chuyển đổi
     * @return Danh sách các đối tượng CommentResponse đã được chuyển đổi, trả về null nếu đầu vào là null
     */
    public List<CommentResponse> toResponseList(List<Comment> comments) {
        if (comments == null) {
            return null;
        }
        
        return comments.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Chuyển đổi một danh sách Comment thành PaginatedResponse
     *
     * @param comments Danh sách các đối tượng Comment cần chuyển đổi
     * @return Đối tượng PaginatedResponse chứa danh sách CommentResponse đã được chuyển đổi
     */
    public PaginatedResponse<CommentResponse> toPaginatedResponse(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return PaginatedResponse.of(
                    List.of(),
                    0,
                    0,
                    0
            );
        }

        List<CommentResponse> responseList = toResponseList(comments);
        
        // Thiết lập giá trị mặc định cho phân trang
        int pageNumber = 1;
        int pageSize = comments.size();
        long totalElements = comments.size();
        
        return PaginatedResponse.of(
                responseList,
                pageNumber,
                pageSize,
                totalElements
        );
    }
}
