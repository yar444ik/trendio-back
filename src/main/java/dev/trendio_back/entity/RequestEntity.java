package dev.trendio_back.entity;

import dev.trendio_back.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;

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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,  cascade={CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false,unique = false)
    private UserEntity user;

    private String address;
    @Column(name = "create_date")
    private LocalDateTime createDate;

    private BigDecimal latitude;
    private BigDecimal longitude;

    @ManyToMany(fetch = FetchType.LAZY,  cascade={CascadeType.MERGE})
    @JoinTable(name = "requests_tags",
            joinColumns = @JoinColumn(name = "request_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "tag_id", nullable = false))
    public List<TagEntity> tags;

    @OneToMany(mappedBy = "request",fetch = FetchType.LAZY, cascade=CascadeType.ALL/*, orphanRemoval = true*/)
    private List<LikeEntity> likes;

    @Column(name = "header_request")
    private String headerRequest;
    @Column(name = "text_request")
    private String textRequest;

    @OneToMany(mappedBy = "request",fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private List<CommentEntity> comments;

    public static RequestEntity of(Long id) {
        RequestEntity request = new RequestEntity();
        request.setId(id);
        return request;
    }
}
