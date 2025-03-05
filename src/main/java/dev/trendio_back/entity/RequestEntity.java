package dev.trendio_back.entity;

import dev.trendio_back.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.sql.Date;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "requests")
public class RequestEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "users_id")
    private UserEntity author;

    private String adress;
    @Column(name = "create_date")
    private LocalDateTime createDate;

    private BigDecimal latitude;
    private BigDecimal longitude;

    /*@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tags_id")
    public List<TagEntity> tags;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "likes_id")
    private List<LikeEntity> likes;*/

    @Column(name = "header_request")
    private String headerRequest;
    @Column(name = "text_request")
    private String textRequest;

    /*@OneToMany(mappedBy = "comment",fetch = FetchType.LAZY)
    @Column(name = "comments_id")
    private List<CommentEntity> comments;*/
}
