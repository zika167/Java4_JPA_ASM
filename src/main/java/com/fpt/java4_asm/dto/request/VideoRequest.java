package com.fpt.java4_asm.dto.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class VideoRequest {
        @NotBlank(message = "Mã video không được để trống")
        private String id;
        @NotBlank(message = "Tiêu đề không được để trống")
         private String title;
        @NotBlank(message = "Đường dẫn poster không được để trống")
        private String poster;
        @NotBlank(message = "Mô tả không được bỏ trống")
        private String description;
        @JsonProperty("active")
        private Boolean active = true; // mặt định là hoạt động
        @NotBlank(message = "User id không đươợc để trống")
        private String userId;
}
