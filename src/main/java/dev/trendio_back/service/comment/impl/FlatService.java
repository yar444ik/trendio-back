package dev.trendio_back.service.comment.impl;

import dev.trendio_back.dto.CommentDto;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@SuperBuilder
@ConditionalOnProperty(name = "comments.type", havingValue = "flat")
public class FlatService extends BaseService {
    @Override
    public Page<CommentDto> findAllForRequest(long requestId) {
        return
    }
}
