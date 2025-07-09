package dev.trendio_back.service.comment.impl;

import dev.trendio_back.dto.CommentDto;
import dev.trendio_back.dto.auth.AuthUser;
import dev.trendio_back.dto.mapper.CommentMapper;
import dev.trendio_back.entity.CommentEntity;
import dev.trendio_back.exception.NotFoundException;
import dev.trendio_back.repository.CommentRepository;
import dev.trendio_back.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public abstract class BaseService implements CommentService {
    protected final CommentRepository commentRepository;
    protected final CommentMapper commentMapper;

    @Override
    public boolean isCreator(String username, long commentId) {
        return commentRepository.isCreator(username, commentId);
    }

    @Override
    public CommentDto create(CommentDto comment, AuthUser authUser) {
        comment.setUserId(authUser.getId());
        comment.setUsername(authUser.getUsername());
        comment.setCreateDate(LocalDateTime.now());
        return commentMapper.entityToDto(
                commentRepository.save(commentMapper.dtoToEntity(comment)));
    }

    @Override
    public CommentDto update(CommentDto comment, AuthUser authUser, Long id) {
        CommentEntity oldComment = commentRepository.findByRequestId(id);
        comment.setId(id);
        comment.setUserId(authUser.getId());
        comment.setUpdateDate(LocalDateTime.now());
        comment.setCreateDate(oldComment.getCreateDate());
        return commentMapper.entityToDto(
                commentRepository.save(commentMapper.dtoToEntity(comment)));
    }

    @Override
    public Long delete(Long id) {
        try {
            commentRepository.deleteById(id);
            return id;
        } catch (Exception ex) {
            throw new NotFoundException("Комментария не существует: " + id);
        }
    }
}
