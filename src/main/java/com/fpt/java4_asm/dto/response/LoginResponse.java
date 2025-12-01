package com.fpt.java4_asm.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO cho response Login
 * Chứa thông tin user sau khi đăng nhập thành công
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    // ID user
    private String id;
    
    // Email user
    private String email;
    
    // Tên đầy đủ user
    private String fullname;
    
    // Vai trò (true = admin, false = user)
    private Boolean admin;
    
    // Token xác thực (có thể dùng JWT hoặc session ID)
    private String token;
    
    // Ngày tạo (format: yyyy-MM-dd HH:mm:ss)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
}
