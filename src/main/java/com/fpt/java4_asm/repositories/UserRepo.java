package com.fpt.java4_asm.repositories;

import com.fpt.java4_asm.models.entities.User;

import java.util.Optional;

public interface UserRepo extends BaseRepository<User, String> {
    // Class này là interface kế thừa từ BaseRespo, cái lớp đó là lớp Khoa viết sẵn căn bản , tái sử dụng lại. Ở đây lấy User và kiểu String(kiểu của ID)
    Optional<User> findByEmail(String email);
    // Phương thức tìm theo email và trả về kiểu User

    Optional<User> findByEmailAndPassword(String email, String password);
    // Phương thức tìm theo Email và password
}
