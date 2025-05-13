package dev.trendio_back.service;

import dev.trendio_back.dto.RequestDto;
import dev.trendio_back.dto.TagDto;
import dev.trendio_back.dto.mapper.RequestMapper;
import dev.trendio_back.dto.mapper.RequestMapperImpl;
import dev.trendio_back.dto.mapper.TagMapper;
import dev.trendio_back.dto.mapper.UserMapper;
import dev.trendio_back.entity.RequestEntity;
import dev.trendio_back.entity.TagEntity;
import dev.trendio_back.entity.auth.UserEntity;
import dev.trendio_back.repository.RequestRepository;
import dev.trendio_back.repository.TagRepository;
import dev.trendio_back.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final TagService tagService;
    private final TagMapper tagMapper;
    private final RequestMapper requestMapper;

    public Page<RequestDto> findAll(int page, int size, String sortField, String sortDirection) {
        //todo collect Pageable in controller
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<RequestEntity> requestEntities = requestRepository.findAll(pageable);
        return requestEntities.map(
                requestMapper::entityToDto
        );
    }

    public RequestDto create(RequestDto dto) {
        // Получаем пользователя
        UserEntity user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Сохраняем или получаем существующие теги
        List<TagEntity> tagEntities = dto.getTags().stream()
                .map(tagDto -> tagService.create(tagDto)) // Используем create для гарантии сохранения
                .map(tagMapper::dtoToEntity)
                .collect(Collectors.toList());

        // Создаем и настраиваем RequestEntity
        //todo mappers
        //todo create entity without requesting tags, use only ids
        RequestEntity entity = new RequestEntity();
        entity.setUser(user);
        entity.setAddress(dto.getAddress());
        entity.setCreateDate(LocalDateTime.now());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setHeaderRequest(dto.getHeaderRequest());
        entity.setTextRequest(dto.getTextRequest());
        entity.setTags(tagEntities); // Устанавливаем теги

        // Сохраняем RequestEntity
        RequestEntity savedEntity = requestRepository.save(entity);
        return requestMapper.entityToDto(savedEntity);
    }

    public RequestDto update(RequestDto dto, Long id) {
        // Получаем существующую сущность
        RequestEntity existingEntity = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        // Обновляем поля
        existingEntity.setAddress(dto.getAddress());
        existingEntity.setLatitude(dto.getLatitude());
        existingEntity.setLongitude(dto.getLongitude());
        existingEntity.setHeaderRequest(dto.getHeaderRequest());
        existingEntity.setTextRequest(dto.getTextRequest());

        // Обновляем теги (если они есть)
        if (dto.getTags() != null) {
            //todo requests in db is a bad idea
            List<TagEntity> updatedTags = dto.getTags().stream()
                    .map(tagDto -> {
                        if (tagDto.getId() == null) {
                            // Создаем новый тег, если id отсутствует
                            return tagService.create(tagDto);
                        } else {
                            // Обновляем существующий тег, если id указан
                            return tagService.update(tagDto);
                        }
                    })
                    .map(tagMapper::dtoToEntity)
                    .collect(Collectors.toList());

            existingEntity.setTags(updatedTags);
        }

        // Сохраняем обновленную сущность
        RequestEntity updatedEntity = requestRepository.save(existingEntity);
        return requestMapper.entityToDto(updatedEntity);
    }

    public Long delete(Long id) {
        requestRepository.deleteById(id);
        return id;
    }
}
