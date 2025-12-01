package com.fpt.java4_asm.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoResponse {
    private String id;
    private String title;
    private String poster;
    private int views;
    private String description;
    private boolean active;
    
    // Thay vì trả về object User, ta chỉ trả về tên người đăng
    private String authorName;
    
    // Ngày tạo video (format: yyyy-MM-dd HH:mm:ss)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
}
