package dev.trendio_back.repository;

import dev.trendio_back.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByRequestId(long requestId);

    CommentEntity findByRequestId(long requestId);

    @Query("SELECT COUNT(c) >0  from CommentEntity c WHERE c.user.username = :username AND c.id = :commentId")
    boolean isCreator(String username, long commentId);
}
