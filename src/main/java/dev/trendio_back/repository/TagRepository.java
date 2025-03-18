package dev.trendio_back.repository;

import dev.trendio_back.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
    @Override
    Optional<TagEntity> findById(Long id);
    Optional<TagEntity> findByNameTag(String nameTag);
}
