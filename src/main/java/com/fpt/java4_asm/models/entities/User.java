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
    private String id;

    @Column(name = "Password")
    private String password;

    @Column(name = "Email")
    private String email;

    @Column(name = "Fullname")
    private String fullname;

    @Column(name = "Admin")
    private Boolean admin;

    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "UpdatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Transient
    private String confirmPassword;
}
