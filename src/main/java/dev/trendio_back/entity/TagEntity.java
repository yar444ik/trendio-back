package dev.trendio_back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.trendio_back.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tags")
public class TagEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name_tag")
    private String nameTag;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private ImageEntity icon;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_id")
    private List<UserEntity> users;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "requests_id")
    private List<RequestEntity> requests;
}
