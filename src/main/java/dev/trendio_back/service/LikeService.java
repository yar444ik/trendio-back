package dev.trendio_back.service;

import dev.trendio_back.dto.LikeDto;
import dev.trendio_back.dto.mapper.LikeMapper;
import dev.trendio_back.entity.LikeEntity;
import dev.trendio_back.entity.RequestEntity;
import dev.trendio_back.entity.auth.UserEntity;
import dev.trendio_back.repository.LikeRepository;
import dev.trendio_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;
    private final UserRepository userRepository;

    public LikeDto likeRequest(Long userId, Long requestId) {
        LikeEntity like = new LikeEntity();
        like.setUser(UserEntity.of(userId));
        like.setRequest(RequestEntity.of(requestId));
        like.setCreateDate(LocalDateTime.now());

        likeRepository.save(like);

        return likeMapper.entityToDto(like);
    }

    public void unlikeRequest(Long userId, Long requestId) {
        LikeEntity like = likeRepository.findByUserIdAndRequestId(userId, requestId);
        likeRepository.delete(like);
    }

    public List<LikeDto> getLikesForRequest(Long requestId) {
        return likeMapper.listEntityToDto(likeRepository.findLikesByRequestId(requestId));
    }
}
