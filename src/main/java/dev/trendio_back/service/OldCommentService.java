package dev.trendio_back.service;

import dev.trendio_back.annotations.SaveLog;
import dev.trendio_back.dto.CommentDto;
import dev.trendio_back.dto.auth.AuthUser;
import dev.trendio_back.dto.mapper.CommentMapper;
import dev.trendio_back.entity.CommentEntity;
import dev.trendio_back.exception.NotFoundException;
import dev.trendio_back.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OldCommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public List<CommentDto> findAllForRequest (long requestId) {
        return commentMapper.listEntityToDto(commentRepository.findAllByRequestId(requestId));
    }

    public boolean isCreator (String username, long commentId) {
        return commentRepository.isCreator(username, commentId);
    }

    public CommentDto create (CommentDto comment, AuthUser authUser) {
        comment.setUserId(authUser.getId());
        comment.setUsername(authUser.getUsername());
        comment.setCreateDate(LocalDateTime.now());
        return commentMapper.entityToDto(
                commentRepository.save(commentMapper.dtoToEntity(comment)));
    }

    public CommentDto update (CommentDto comment, AuthUser authUser, Long id) {
        CommentEntity oldComment = commentRepository.findByRequestId(id);
        comment.setId(id);
        comment.setUserId(authUser.getId());
        comment.setUpdateDate(LocalDateTime.now());
        comment.setCreateDate(oldComment.getCreateDate());
        return commentMapper.entityToDto(
                commentRepository.save(commentMapper.dtoToEntity(comment)));
    }

    public Long delete(Long id) {
        try {
            commentRepository.deleteById(id);
            return id;
        } catch (Exception ex) {
            throw new NotFoundException("Комментария не существует" + id);
        }
    }
}
