package com.fpt.java4_asm.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import java.util.Date;

// Entity: đối tượng đại diện cho 1 bảng trong database
// @Entity: báo cho JPA biết đây là entity (được map với bảng DB)
// @Data: tự sinh getter, setter, equals, hashCode, toString
// @AllArgsConstructor: tự sinh constructor với tất cả tham số
// @NoArgsConstructor: tự sinh constructor không tham số
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User") // Map với bảng "User" trong database
public class User {
    @Id
    @Column(name = "Id")
    private String id; // Khóa chính (Primary Key), định danh duy nhất cho mỗi user

    @Column(name = "Email", unique = true, nullable = false)
    // unique = true: email phải độc nhất (không được trùng)
    // nullable = false: email không được null
    private String email; // Email của user (được dùng để login)

    @Column(name = "Password", nullable = false)
    private String password; // Mật khẩu của user

    @Column(name = "Fullname")
    private String fullname; // Tên đầy đủ của user

    @Column(name = "Admin")
    private Boolean admin; // Cờ để xác định user có phải admin hay không (true/false)

    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP) // Lưu trữ thời gian (bao gồm ngày giờ phút giây)
    private Date createdDate; // Ngày tạo user

    @Column(name = "UpdatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate; // Ngày cập nhật user gần nhất

    @Transient // Field này không được lưu vào database
    private String confirmPassword;
    // Dùng để xác nhận password (nhập lại password để kiểm tra chính xác)
    // @Transient: field này chỉ tồn tại trong Object, không tương ứng với cột nào trong bảng DB
}
