package com.fpt.java4_asm.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    
    // User ID đăng comment
    private String userId;
    
    // Video ID được comment
    private String videoId;
    
    // Nội dung comment
    private String content;
    
    // Ngày tạo (format: yyyy-MM-dd HH:mm:ss)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    
    // Ngày cập nhật (format: yyyy-MM-dd HH:mm:ss)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
}
