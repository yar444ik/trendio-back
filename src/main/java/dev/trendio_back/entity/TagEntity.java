package dev.trendio_back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.trendio_back.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagEntity that)) return false;
        return Objects.equals(getNameTag(), that.getNameTag());
    }

    // Метод hashCode
    @Override
    public int hashCode() {
        return 31 * getNameTag().hashCode();
    }
}
