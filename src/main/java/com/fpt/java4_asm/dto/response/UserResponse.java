package com.fpt.java4_asm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    // Trả về Id để API xử lí, ko trả cho người dùng
    // Trả cho người dùng email, tên với ngày tạo là được
    private String id;
    private String email;
    private String fullName;
    private Date createdDate;
}
