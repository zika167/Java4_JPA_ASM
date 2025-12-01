package com.fpt.java4_asm.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Share")
public class Share {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "VideoId", nullable = false)
    private Video video;

    @Column(name = "Emails", columnDefinition = "NVARCHAR(MAX)")
    private String emails; // Store as JSON array string

    @Column(name = "ShareDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shareDate = new Date();
}
