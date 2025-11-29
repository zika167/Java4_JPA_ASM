package com.fpt.java4_asm.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Video")
public class Video {
    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "Title")
    private String title;
    @Column(name = "Poster")
    private String poster;
    @Column(name = "Views")
    private int views;
    @Column(name = "Description", columnDefinition = "LONGTEXT")
    private String description;
    @Column(name = "Active")
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;
    @CreationTimestamp // Tự động lấy giờ hiện tại khi INSERT
    @Column(name = "CreatedDate", updatable = false)
    private LocalDateTime createdDate;
    @UpdateTimestamp // Tự động cập nhật giờ khi UPDATE
    @Column(name = "UpdatedDate")
    private LocalDateTime updatedDate;
    @OneToMany(mappedBy = "video")
    private List<Comment> comments;
    @OneToMany(mappedBy = "video")
    private List<Favorite> favorites;
    @OneToMany(mappedBy = "video")
    private List<Share> shares;
}
