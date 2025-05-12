package dev.trendio_back.entity;

import dev.trendio_back.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class CommentEntity {

    public CommentEntity(UserEntity user, RequestEntity request, LocalDateTime createDate, LocalDateTime updateDate, String comment) {
        this.user=user;
        this.request=request;
        this.createDate=createDate;
        this.updateDate=updateDate;
        this.comment=comment;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "comments_user")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "comments_request")
    private RequestEntity request;

    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;
    @Column(name = "text")
    private String comment;
}
