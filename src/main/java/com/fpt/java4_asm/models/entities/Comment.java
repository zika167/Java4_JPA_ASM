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
@Table(name = "Comment")
public class Comment {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mối quan hệ Many-to-One với User (nhiều comment thuộc về 1 user)
    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    // Mối quan hệ Many-to-One với Video (nhiều comment thuộc về 1 video)
    @ManyToOne
    @JoinColumn(name = "VideoId", nullable = false)
    private Video video;

    @Column(name = "Content", nullable = false)
    private String content;


    @Column(name = "CreatedDate", nullable = false)
    private Date createdDate;

    @Column(name ="UpdatedDate",nullable = false)
    private Date updatedDate;


        
}
