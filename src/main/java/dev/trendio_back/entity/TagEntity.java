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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_tag", unique = true, nullable = false)
    private String nameTag;
/* убираю это поле по причине ненадобности
    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private ImageEntity icon; */

/* убираю это поле по причине ненадобности
    @ManyToMany(mappedBy = "tags", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private List<RequestEntity> requests;*/
}
