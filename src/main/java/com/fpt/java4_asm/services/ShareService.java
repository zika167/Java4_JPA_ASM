package com.fpt.java4_asm.services;

import com.fpt.java4_asm.dto.ShareVideoRequest;
import com.fpt.java4_asm.models.entities.Share;

import java.util.List;

public interface ShareService {
    Share shareVideo(ShareVideoRequest request);
    List<Share> getSharesByVideoId(String videoId);
    List<Share> getSharesByUserId(String userId);
}
