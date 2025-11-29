package com.fpt.java4_asm.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fpt.java4_asm.models.entities.User;
import com.fpt.java4_asm.models.entities.Video;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO cho response Comment
 * Chứa toàn bộ thông tin comment để trả về client
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    // ID comment (auto-generated)
    private Long id;
    
    // User đăng comment
    private User user;
    
    // Video được comment
    private Video video;
    
    // Nội dung comment
    private String content;
    
    // Ngày tạo (format: yyyy-MM-dd HH:mm:ss)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    
    // Ngày cập nhật (format: yyyy-MM-dd HH:mm:ss)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
}
