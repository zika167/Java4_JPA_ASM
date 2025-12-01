package com.fpt.java4_asm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteResponse {
    private Integer id;
    private String userId;
    private String videoId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date likeDate;
}
