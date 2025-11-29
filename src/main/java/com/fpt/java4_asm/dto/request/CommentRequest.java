package com.fpt.java4_asm.dto.request;

import com.fpt.java4_asm.models.entities.User;
import com.fpt.java4_asm.models.entities.Video;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO cho yêu cầu tạo/cập nhật Comment
 * Chứa thông tin cần thiết từ client
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    // User đăng comment (reference entity)
    @NotNull(message = "User is required")
    private User user;

    // Video được comment (reference entity)
    @NotNull(message = "Video is required")
    private Video video;

    // Nội dung comment
    @NotNull(message = "Content is required")
    private String content;

    // Ngày tạo (tự động set nếu null)
    private Date createdDate = new Date();

    // Ngày cập nhật (tự động set nếu null)
    private Date updatedDate = new Date();
}
