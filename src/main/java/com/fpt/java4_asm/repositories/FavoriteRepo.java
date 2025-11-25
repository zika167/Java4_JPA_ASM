package com.fpt.java4_asm.repositories;

import com.fpt.java4_asm.models.entities.Favorite;

import java.util.List;

public interface FavoriteRepo extends BaseRepository<Favorite, Integer>{
    /**
     * Kiểm tra xem user có thích video không
     */
    boolean existsByUserAndVideo(String userId, String videoId);

    /**
     * Tìm theo UserId
     */
    List<Favorite> findByUserId(String userId);

    List<Favorite> pages(int page, int size);
}
