package com.fpt.java4_asm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// DTO (Data Transfer Object) dùng để trả về dữ liệu cho client (người dùng)
// Chỉ chứa những field cần thiết, không trả về password hoặc dữ liệu nhạy cảm
// @Data: tự sinh getter, setter, equals, hashCode, toString
// @NoArgsConstructor: tự sinh constructor không tham số
// @AllArgsConstructor: tự sinh constructor có tất cả tham số
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id; // ID của user (dùng để API xử lý)
    private String email; // Email của user
    private String fullName; // Tên đầy đủ của user
    private Date createdDate; // Ngày tạo user (ghi nhận thời điểm tạo)
    // Lưu ý: Ko trả về password vì nó là dữ liệu nhạy cảm
    // Ko trả về admin flag vì client ko cần biết user là admin hay ko
}
