package com.fpt.java4_asm.services;

import com.fpt.java4_asm.dto.request.UserRequest;
import com.fpt.java4_asm.dto.response.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponse create(UserRequest request);

    Optional<UserResponse> update(String id, UserRequest request);

    Optional<UserResponse> getById(String id);

    List<UserResponse> getAll();

    boolean delete(String id);

    boolean exists(String id);

    long count();

    Optional<UserResponse> login(String email, String password);
}
