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
import jakarta.transaction.Transactional;
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
import java.util.ArrayList;
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
    private final TagRepository tagRepository;

    public Page<RequestDto> findAll(int page, int size, String sortField, String sortDirection) {
        //todo(?) collect Pageable in controller
        //todo отсюда
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page, size, sort); //todo в контроллере
        Page<RequestEntity> requestEntities = requestRepository.findAll(pageable);
        return requestEntities.map(requestMapper::entityToDto);
    }

    public RequestDto create(RequestDto dto) {
        RequestEntity requestEntity = requestMapper.dtoToEntity(dto);
        requestEntity = requestRepository.save(requestEntity);
        return requestMapper.entityToDto(requestEntity);
    }

    public Long delete(Long id) {
        requestRepository.deleteById(id);
        return id;
    }

    public RequestDto update(RequestDto request, Long id) {
        RequestEntity requestEntity = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        return requestMapper.entityToDto(requestRepository.save(dtoToEntity(request, requestEntity)));
    }

    RequestEntity dtoToEntity(RequestDto dto, RequestEntity entity) {
        entity.setAddress(dto.getAddress());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setHeaderRequest(dto.getHeaderRequest());
        entity.setTextRequest(dto.getTextRequest());
        //todo clear and update(save all) (как делать в hibernate merge)
        List<TagDto> tagsDto = dto.getTags();
        List<TagEntity> tagEntities = tagMapper.listDtoToEntity(tagsDto);
        tagRepository.findByNameTagIn(dto.getTags().stream().filter()...);
//        List<TagEntity> newTags = dto.getTags().stream()
//                .map(tagMapper::dtoToEntity)
//                .collect(Collectors.toList());
//        tagService.saveAll(newTags);
        entity.setTags(newTags);
        return entity;
    }
}
