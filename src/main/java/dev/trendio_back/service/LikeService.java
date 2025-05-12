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

    public LikeDto likeRequest(String username, Long requestId) {
        UserEntity user = userRepository.findById(userRepository.findByUsername(username)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"))
                        .getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        RequestEntity request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        // Проверяем, не поставил ли пользователь уже лайк
        Optional<LikeEntity> existingLike = likeRepository.findByUsernameAndRequest(user, request);
        if (existingLike.isPresent()) {
            throw new IllegalStateException("User already liked this request");
        }

        LikeEntity like = new LikeEntity();
        like.setUsername(user);
        like.setRequest(request);
        like.setCreateDate(LocalDateTime.now());

        request.getLikes().add(like);
        user.getLikes().add(like);

        LikeEntity savedLike = likeRepository.save(like);
        requestRepository.save(request);
        userRepository.save(user);


        return likeMapper.entityToDto(savedLike);
    }

    @Transactional
    public void unlikeRequest(String username, Long requestId) {
        // Получаем managed сущности
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        RequestEntity request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        // Находим лайк
        LikeEntity like = likeRepository.findByUsernameAndRequest(user, request)
                .orElseThrow(() -> new ResourceNotFoundException("Like not found"));

        // Удаляем связи
        user.getLikes().remove(like);
        request.getLikes().remove(like);

        // Сохраняем изменения
        likeRepository.delete(like);
        userRepository.saveAndFlush(user);
        requestRepository.saveAndFlush(request);
    }

    public List<LikeDto> getLikesForRequest(Long requestId) {
        RequestEntity request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        return likeMapper.listEntityToDto(likeRepository.findByRequest(request));
    }
}
