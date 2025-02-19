package dev.trendio_back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tags")
public class TagEntity {

    public TagEntity(Long id, String nameTag) {
        this.id = id;
        this.nameTag = nameTag;
    }
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name_tag")
    private String nameTag;
    //private ImageEntity icon;
}
