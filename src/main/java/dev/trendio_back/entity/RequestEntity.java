package dev.trendio_back.entity;

import dev.trendio_back.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "requests")
public class RequestEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY/*, cascade=CascadeType.ALL*/)
    @JoinColumn(name = "requests_user", nullable = false)
    private UserEntity user;

    private String address;
    @Column(name = "create_date")
    private LocalDateTime createDate;

    private BigDecimal latitude;
    private BigDecimal longitude;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "requests_tags",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "request_id"))
    public List<TagEntity> tags;

    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
    private List<LikeEntity> likes = new ArrayList<>();

    @Column(name = "header_request")
    private String headerRequest;
    @Column(name = "text_request")
    private String textRequest;

    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private List<CommentEntity> comments;
}
