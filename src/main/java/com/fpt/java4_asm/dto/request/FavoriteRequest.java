package com.fpt.java4_asm.dto.request;

import com.fpt.java4_asm.models.entities.User;
import com.fpt.java4_asm.models.entities.Video;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteRequest {
    @NotNull(message = "User is required")
    private User user;

    @NotNull(message = "Video is required")
    private Video video;

    private Date likeDate = new Date();
}