package com.fpt.java4_asm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
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
        private LocalDateTime createdDate;
}
