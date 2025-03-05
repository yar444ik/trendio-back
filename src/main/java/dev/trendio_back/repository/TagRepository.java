package dev.trendio_back.repository;

import dev.trendio_back.entity.TagEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagRepository extends CrudRepository<TagEntity, Long> {
    Optional<TagEntity> findByNameTag(String nameTag);
}
