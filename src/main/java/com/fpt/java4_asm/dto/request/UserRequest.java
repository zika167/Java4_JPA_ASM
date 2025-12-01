package com.fpt.java4_asm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    //Nhận từ người dùng email, password ,confirm nếu xác thực mật khẩu, tên đầy đủ
    private String id;
    private String fullName;
    private String email;
    private String password;
    private Boolean admin;
}
