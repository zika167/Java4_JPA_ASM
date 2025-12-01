package com.fpt.java4_asm.repositories;

import com.fpt.java4_asm.models.entities.Share;
import java.util.List;

public interface ShareRepo extends BaseRepository<Share, String> {
	/**
	 * Tìm theo VideoId
	 */
	List<Share> findByVideoId(String videoId);

	/**
	 * Tìm theo UserId
	 */
	List<Share> findByUserId(String userId);

}
