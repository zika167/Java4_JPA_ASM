package com.fpt.java4_asm.convert;

import com.fpt.java4_asm.dto.request.UserRequest;
import com.fpt.java4_asm.dto.response.UserResponse;
import com.fpt.java4_asm.models.entities.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UserConvert {

    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullname());
        response.setCreatedDate(user.getCreatedDate());

        return response;
    }

    public User toEntity(UserRequest request) {
        if (request == null) {
            return null;
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFullname(request.getFullName());
        user.setConfirmPassword(request.getConfirmPassword());
        user.setAdmin(false);
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());

        return user;
    }

    public User toEntity(User user, UserRequest request) {
        if (user == null || request == null) {
            return user;
        }

        user.setEmail(request.getEmail() != null ? request.getEmail() : user.getEmail());
        user.setPassword(request.getPassword() != null ? request.getPassword() : user.getPassword());
        user.setFullname(request.getFullName() != null ? request.getFullName() : user.getFullname());
        user.setUpdatedDate(new Date());

        return user;
    }

    public List<UserResponse> toResponseList(List<User> users) {
        if (users == null) {
            return null;
        }

        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
