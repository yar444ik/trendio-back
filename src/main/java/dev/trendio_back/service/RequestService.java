package dev.trendio_back.service;

import dev.trendio_back.dto.RequestDto;
import dev.trendio_back.dto.TagDto;
import dev.trendio_back.dto.mapper.RequestMapper;
import dev.trendio_back.dto.mapper.RequestMapperImpl;
import dev.trendio_back.dto.mapper.UserMapper;
import dev.trendio_back.entity.RequestEntity;
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
    private final TagService tagService;
    private final RequestMapper requestMapper;

    public Page<RequestDto> findAll(int page, int size, String sortField, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<RequestEntity> requestEntities = requestRepository.findAll(pageable);
        return requestEntities.map(
                requestMapper::entityToDto
        );
    }

    public RequestDto create(RequestDto dto) {
        RequestEntity entity = requestMapper.dtoToEntity(dto);
        entity.setCreateDate(LocalDateTime.now());

        return requestMapper.entityToDto(requestRepository.save(entity));
    }

    public RequestDto update(RequestDto dto, Long id) {
        RequestDto oldDto = requestMapper.entityToDto(requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found")));

        oldDto.setUsername(dto.getUsername());
        oldDto.setAddress(dto.getAddress());
        oldDto.setLatitude(dto.getLatitude());
        oldDto.setLongitude(dto.getLongitude());
        oldDto.setLikes(dto.getLikes());
        oldDto.setHeaderRequest(dto.getHeaderRequest());
        oldDto.setTextRequest(dto.getTextRequest());
        oldDto.setComments(dto.getComments());

        if (dto.getTags() != null) {
            List<TagDto> updatedTags = dto.getTags().stream()
                    .map(tagDto -> {
                        // Если у тега есть ID, используем его
                        if (tagDto.getId() != null) {
                            return tagDto;
                        }
                        // Иначе ищем существующий тег по имени
                        return tagService.findByName(tagDto.getNameTag())
                                .orElseGet(() -> tagService.create(tagDto));
                    })
                    .collect(Collectors.toList());
            oldDto.setTags(updatedTags);
        }

        requestRepository.save(requestMapper.dtoToEntity(oldDto));

        return oldDto;
    }

    public Long delete(Long id) {
        requestRepository.deleteById(id);
        return id;
    }
}
