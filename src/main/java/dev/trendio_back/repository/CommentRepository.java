package dev.trendio_back.repository;

import dev.trendio_back.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByRequestId(long requestId);

    CommentEntity findByRequestId(long requestId);

    //todo show sql and look at the amount of joins .user.username, use userId
    @Query("SELECT COUNT(c) >0  from CommentEntity c WHERE c.user.username = :username AND c.id = :commentId")
    boolean isCreator(String username, long commentId);
}
