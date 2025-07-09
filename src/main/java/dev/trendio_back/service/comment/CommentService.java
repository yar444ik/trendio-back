package dev.trendio_back.service.comment;

import dev.trendio_back.dto.CommentDto;
import dev.trendio_back.dto.auth.AuthUser;
import org.springframework.data.domain.Page;

public interface CommentService {

    boolean isCreator(String username, long commentId);
    Page<CommentDto> findAllForRequest(long requestId);
    CommentDto create(CommentDto commentDto, AuthUser authUser);
    CommentDto update(CommentDto commentDto, AuthUser authUser, Long id);
    Long delete(Long id);
}
