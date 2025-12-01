package com.fpt.java4_asm.repositories;

import com.fpt.java4_asm.models.entities.Share;
import java.util.List;

public interface ShareRepo extends BaseRepository<Share, Integer> {
	/**
	 * Tìm theo VideoId
	 */
	List<Share> findByVideoId(String videoId);

	/**
	 * Tìm theo UserId
	 */
	List<Share> findByUserId(String userId);

	/**
	 * Lấy danh sách phân trang
	 * 
	 * @param page Chỉ số trang (0-based)
	 * @param size Kích thước trang
	 * @return Danh sách Share của trang được yêu cầu
	 */
	List<Share> pages(int page, int size);
}
