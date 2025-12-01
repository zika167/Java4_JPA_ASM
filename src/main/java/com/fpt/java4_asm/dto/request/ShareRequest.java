package com.fpt.java4_asm.dto.request;

import com.fpt.java4_asm.models.entities.User;
import com.fpt.java4_asm.models.entities.Video;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO cho yêu cầu tạo/cập nhật Share
 * Chứa thông tin cần thiết từ client
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShareRequest {
    // User chia sẻ video (reference entity)
    @NotNull(message = "User is required")
    private User user;

    // Video được chia sẻ (reference entity)
    @NotNull(message = "Video is required")
    private Video video;

    // Danh sách email nhận chia sẻ (JSON string)
    @NotNull(message = "Emails is required")
    private String emails;

    // Ngày chia sẻ (tự động set nếu null)
    private Date shareDate = new Date();
}
