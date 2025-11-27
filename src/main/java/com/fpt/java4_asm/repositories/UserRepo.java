package com.fpt.java4_asm.repositories;

import com.fpt.java4_asm.models.entities.User;

import java.util.Optional;

public interface UserRepo extends BaseRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);
}
