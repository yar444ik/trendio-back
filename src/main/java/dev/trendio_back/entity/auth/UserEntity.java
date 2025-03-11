package dev.trendio_back.entity.auth;

import dev.trendio_back.dto.auth.Role;
import dev.trendio_back.entity.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String username;
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    @Column(name = "role"/*, nullable = false*/)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "password_id")
    private PasswordEntity passwordEntity;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "avatar_id")
    private ImageEntity avatar;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "likes_users")
    private List<LikeEntity> likes;
    @OneToMany(mappedBy = "username",fetch = FetchType.LAZY)
    private List<CommentEntity> comments;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "requests_user")
    private List<RequestEntity> requests;
}
