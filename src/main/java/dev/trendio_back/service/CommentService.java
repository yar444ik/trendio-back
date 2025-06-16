package dev.trendio_back.service;

import dev.trendio_back.dto.CommentDto;
import dev.trendio_back.dto.mapper.CommentMapper;
import dev.trendio_back.entity.CommentEntity;
import dev.trendio_back.exception.NotFoundException;
import dev.trendio_back.repository.CommentRepository;
import dev.trendio_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;

    public List<CommentDto> findAllForRequest (long requestId) {
        return commentMapper.listEntityToDto(commentRepository.findAllByRequestId(requestId));
    }

    public boolean isCreator (String username, long commentId) {
        return commentRepository.isCreator(username, commentId);
    }

    public CommentDto create (CommentDto comment, String username) {
        //todo убрать запрос в юзеррепозиторий и сразу получать с помощью @AuthPrincipal айдишник юзера
        comment.setUserId(userRepository.findUserIdByUsername(username));
        comment.setUsername(username);
        comment.setCreateDate(LocalDateTime.now());
        return commentMapper.entityToDto(
                commentRepository.save(commentMapper.dtoToEntity(comment)));
    }

    public CommentDto update (CommentDto comment, String username, Long id) {
        CommentEntity oldComment = commentRepository.findByRequestId(id);
        //todo убрать запрос в юзеррепозиторий и сразу получать с помощью @AuthPrincipal айдишник юзера
        Long AuthUserId = userRepository.findUserIdByUsername(username);
        comment.setId(id);
        comment.setUserId(AuthUserId);
        comment.setUpdateDate(LocalDateTime.now());
        comment.setCreateDate(oldComment.getCreateDate());
        return commentMapper.entityToDto(
                commentRepository.save(commentMapper.dtoToEntity(comment)));
    }

    public Long delete(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return id;
        } else {
            throw new NotFoundException("Комментария не существует" + id);
        }
    }
}
