package com.fpt.java4_asm.repositories;

import com.fpt.java4_asm.models.entities.User;

import java.util.List;
import java.util.Optional;

// Interface Repository cho User entity
// Kế thừa BaseRepository<User, String> để sử dụng lại các method cơ bản
// <User> = loại entity, <String> = loại của ID (khóa chính)
// BaseRepository cung cấp: save, update, findById, findAll, deleteById, existsById, count
public interface UserRepo extends BaseRepository<User, String> {
    // Lấy thông tin user theo Email
    Optional<User> findByEmail(String email);
    List<User> pages(int page, int size);
}
