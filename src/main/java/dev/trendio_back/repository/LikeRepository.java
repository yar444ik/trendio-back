package dev.trendio_back.repository;

import dev.trendio_back.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    LikeEntity findByUserIdAndRequestId(Long userId, Long requestId);

    //todo check sql
    @Query("SELECT l FROM LikeEntity l WHERE l.request.id = :requestId")
    List<LikeEntity> findLikesByRequestId(@Param("requestId") Long requestId);
}
