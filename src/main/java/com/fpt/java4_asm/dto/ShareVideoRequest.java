package com.fpt.java4_asm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ShareVideoRequest {
    @NotBlank(message = "User ID is required")
    private String userId;
    
    @NotBlank(message = "Video ID is required")
    private String videoId;
    
    @NotEmpty(message = "At least one email is required")
    private List<@Email(message = "Invalid email format") String> emails;
}
