package com.fpt.java4_asm.repositories;

import com.fpt.java4_asm.models.entities.User;

import java.util.Optional;

// Interface Repository cho User entity
// Kế thừa BaseRepository<User, String> để sử dụng lại các method cơ bản
// <User> = loại entity, <String> = loại của ID (khóa chính)
// BaseRepository cung cấp: save, update, findById, findAll, deleteById, existsById, count
public interface UserRepo extends BaseRepository<User, String> {
    
    // Tìm user theo email
    // Trả về Optional<User> vì có thể không tìm thấy user với email đó
    Optional<User> findByEmail(String email);

    // Tìm user theo email và password
    // Dùng cho chức năng login (xác thực email và password khớp)
    // Trả về Optional<User> vì có thể ko tìm thấy (email hoặc password sai)
    Optional<User> findByEmailAndPassword(String email, String password);
}
