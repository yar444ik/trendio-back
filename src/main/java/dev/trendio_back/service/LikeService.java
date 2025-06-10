package dev.trendio_back.service;

import dev.trendio_back.dto.LikeDto;
import dev.trendio_back.dto.mapper.LikeMapper;
import dev.trendio_back.entity.LikeEntity;
import dev.trendio_back.entity.RequestEntity;
import dev.trendio_back.entity.auth.UserEntity;
import dev.trendio_back.repository.LikeRepository;
import dev.trendio_back.repository.RequestRepository;
import dev.trendio_back.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;
    private final UserRepository userRepository;

    public LikeDto likeRequest(String username, Long requestId) {
        LikeEntity like = new LikeEntity();

        Long userId = userRepository.findByUsername(username).orElseThrow(ResourceNotFoundException::new).getId();
        like.setUser(UserEntity.of(userId));
        like.setRequest(RequestEntity.of(requestId));
        like.setCreateDate(LocalDateTime.now());

        likeRepository.save(like);

        return likeMapper.entityToDto(like);
    }

    public void unlikeRequest(String username, Long requestId) {
        Long userId = userRepository.findByUsername(username).orElseThrow(ResourceNotFoundException::new).getId();
        LikeEntity like = likeRepository.findByUserIdAndRequestId(userId, requestId);
        likeRepository.delete(like);
    }

    public List<LikeDto> getLikesForRequest(Long requestId) {
        return likeMapper.listEntityToDto(likeRepository.findLikesByRequestId(requestId));
    }
}
