package dev.trendio_back.service;

import dev.trendio_back.annotations.SaveLog;
import dev.trendio_back.dto.RequestDto;
import dev.trendio_back.dto.TagDto;
import dev.trendio_back.dto.auth.AuthUser;
import dev.trendio_back.dto.mapper.*;
import dev.trendio_back.entity.RequestEntity;
import dev.trendio_back.entity.TagEntity;
import dev.trendio_back.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final TagService tagService;
    private final TagMapper tagMapper;

    public Page<RequestDto> findAll(Pageable pageable) {
        Page<RequestEntity> requestEntities = requestRepository.findAll(pageable);
        return requestEntities.map(requestMapper::entityToDto);
    }

    @SaveLog(action = "CREATE")
    public RequestDto create(RequestDto request, AuthUser authUser) {
        request.setUserId(authUser.getId());
        request.setUsername(authUser.getUsername());
        request.setCreateDate(LocalDateTime.now());
        List<TagDto> tags = tagService.create(request.getTags());
        request.setTags(tags);
        RequestEntity requestEntity = requestRepository.save(requestMapper.dtoToEntity(request));
        return requestMapper.entityToDto(requestEntity);
    }

    @SaveLog(action = "DELETE", entityClass = "RequestId")
    public Long delete(Long id, AuthUser authUser) {
        RequestEntity requestEntity = requestRepository.findByUserIdAndId(authUser.getId(), id)
                .orElseThrow(() -> new AccessDeniedException("You don't have permission to update this request"));
        requestRepository.delete(requestEntity);
        return id;
    }

    @SaveLog(action = "UPDATE")
    public RequestDto update(RequestDto request, AuthUser authUser, Long id) {
        RequestEntity oldRequestEntity = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        if (Objects.equals(authUser.getId(), oldRequestEntity.getUser().getId())) {
            RequestEntity newRequestEntity = requestMapper.dtoToEntity(request);
            newRequestEntity.setId(id);
            newRequestEntity.setUser(oldRequestEntity.getUser());
            newRequestEntity.setCreateDate(oldRequestEntity.getCreateDate());
            List<TagEntity> tags = tagMapper.listDtoToEntity(tagService.create(request.getTags()));
            newRequestEntity.setTags(tags);
            return requestMapper.entityToDto(requestRepository.save(newRequestEntity));
        }
        else {
            throw new AccessDeniedException("You don't have permission to update this request");
        }
    }
}
