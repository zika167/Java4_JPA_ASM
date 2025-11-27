package com.fpt.java4_asm.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fpt.java4_asm.models.entities.User;
import com.fpt.java4_asm.models.entities.Video;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteResponse {
    private Integer id;
    private User user;
    private Video video;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date likeDate;
}
