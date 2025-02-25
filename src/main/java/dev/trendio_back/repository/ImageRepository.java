package dev.trendio_back.repository;

import dev.trendio_back.entity.ImageEntity;
import dev.trendio_back.entity.TagEntity;
import dev.trendio_back.entity.auth.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends CrudRepository<ImageEntity, Long> {
    Optional<ImageEntity> findById(Long imageId);
}
