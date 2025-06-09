package dev.trendio_back.repository;

import dev.trendio_back.dto.TagDto;
import dev.trendio_back.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
    @Override
    Optional<TagEntity> findById(Long id);

    List<TagEntity> findAllByNameTagIn(List<String> nameTags);

    @Query(
            value = "SELECT t.id, t.name_tag, COUNT(rt.request_id) AS cnt " +
                    "FROM tags t " +
                    "JOIN requests_tags rt ON t.id = rt.tag_id " +
                    "GROUP BY t.id " +
                    "ORDER BY cnt DESC " +
                    "LIMIT 25",
            nativeQuery = true
    )
    List<TagEntity> findTop25TagsByCount();

}
