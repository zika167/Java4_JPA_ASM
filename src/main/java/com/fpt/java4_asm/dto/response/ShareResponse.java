package com.fpt.java4_asm.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO cho response Share
 * Chứa toàn bộ thông tin share để trả về client
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShareResponse {
    // ID share (auto-generated)
    private Integer id;
    
    // User ID chia sẻ video
    private String userId;
    
    // Video ID được chia sẻ
    private String videoId;
    
    // Danh sách email nhận chia sẻ (JSON string)
    private String emails;
    
    // Ngày chia sẻ (format: yyyy-MM-dd HH:mm:ss)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date shareDate;
}
