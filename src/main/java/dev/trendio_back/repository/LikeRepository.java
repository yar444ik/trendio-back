package dev.trendio_back.repository;

import dev.trendio_back.entity.LikeEntity;
import dev.trendio_back.entity.RequestEntity;
import dev.trendio_back.entity.auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findById(Long likeId);
    List<LikeEntity> findByRequest(RequestEntity request);
    Optional<LikeEntity> findByUsernameAndRequest(UserEntity user, RequestEntity request);
}
