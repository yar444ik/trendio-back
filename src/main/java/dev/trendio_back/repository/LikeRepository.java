package dev.trendio_back.repository;

import dev.trendio_back.entity.LikeEntity;
import dev.trendio_back.entity.RequestEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends CrudRepository<LikeEntity, Long> {
    Optional<LikeEntity> findById(Long likeId);
}
