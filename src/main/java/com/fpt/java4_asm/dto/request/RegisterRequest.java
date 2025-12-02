package com.fpt.java4_asm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String id;
    private String fullName;
    private String email;
    private String password;
    private String confirmPassword;
    private Boolean admin;
}
