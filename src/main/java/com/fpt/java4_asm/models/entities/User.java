package com.fpt.java4_asm.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "Id")
    private String id; // Này là khóa chính liên kết với Bảng User DB

    @Column(name = "Email", unique = true, nullable = false)
    private String email; // Cái unique là như kiểu độc nhất á, nó ko đổi được, ghi vậy để trừ trường hợp thay đổi người dùng đổi email nó lại ko độc nhất

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Fullname")
    private String fullname;

    @Column(name = "Admin")
    private Boolean admin;

    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP) // Mặc định thời gian là thời gian hiện tại
    private Date createdDate;

    @Column(name = "UpdatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Transient
    private String confirmPassword;
    // Phải có cái này để xác nhận lại password dù rằng cái field này ko hề có trong table nha
}
