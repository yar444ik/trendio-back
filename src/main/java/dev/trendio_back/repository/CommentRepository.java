package dev.trendio_back.repository;

import dev.trendio_back.entity.CommentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommentRepository extends CrudRepository<CommentEntity, Long> {
    Optional<CommentEntity> findById(Long commentId);
}
