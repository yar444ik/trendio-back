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
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final LikeMapper likeMapper;

    @Transactional
    public LikeDto likeRequest(String username, Long requestId) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        RequestEntity request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        if (likeRepository.findByUsernameAndRequest(user, request).isPresent()) {
            throw new IllegalStateException("User already liked this request");
        }

        LikeEntity like = new LikeEntity();
        like.setUsername(user);
        like.setRequest(request);
        like.setCreateDate(LocalDateTime.now());

        user.getLikes().add(like);
        request.getLikes().add(like);

        likeRepository.save(like);

        return likeMapper.entityToDto(like);
    }

    @Transactional
    public void unlikeRequest(String username, Long requestId) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        RequestEntity request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        LikeEntity like = likeRepository.findByUsernameAndRequest(user, request)
                .orElseThrow(() -> new ResourceNotFoundException("Like not found"));

        user.getLikes().remove(like);
        request.getLikes().remove(like);

        likeRepository.delete(like);
    }

    public List<LikeDto> getLikesForRequest(Long requestId) {
        RequestEntity request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        return likeMapper.listEntityToDto(likeRepository.findByRequest(request));
    }
}
