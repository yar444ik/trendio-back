package dev.trendio_back.service;

import dev.trendio_back.dto.RequestDto;
import dev.trendio_back.dto.TagDto;
import dev.trendio_back.dto.mapper.*;
import dev.trendio_back.entity.RequestEntity;
import dev.trendio_back.entity.TagEntity;
import dev.trendio_back.entity.auth.UserEntity;
import dev.trendio_back.repository.RequestRepository;
import dev.trendio_back.repository.TagRepository;
import dev.trendio_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final TagService tagService;
    private final TagMapper tagMapper;
    private final UserMapper userMapper;
    private final RequestMapper requestMapper;
    private final LikeMapper likeMapper;
    private final CommentMapper commentMapper;
    private final TagRepository tagRepository;

    public Page<RequestDto> findAll(Pageable pageable) {
        Page<RequestEntity> requestEntities = requestRepository.findAll(pageable);
        return requestEntities.map(requestMapper::entityToDto);
    }

    public RequestDto create(RequestDto request) {
        RequestEntity requestEntity = new RequestEntity();
        return requestMapper.entityToDto(requestRepository.save(createOrUpdateFromDtoToEntity(request, requestEntity)));

    }

    public Long delete(Long id) {
        requestRepository.deleteById(id);
        return id;
    }

    public RequestDto update(RequestDto request, Long id) {
        RequestEntity requestEntity = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        return requestMapper.entityToDto(requestRepository.save(createOrUpdateFromDtoToEntity(request, requestEntity)));
    }

    RequestEntity createOrUpdateFromDtoToEntity(RequestDto dto, RequestEntity entity) {
        String username = dto.getUsername();
        UserEntity user = userRepository.findByUsername(username).orElseThrow();
        entity.setUser(user);
        entity.setAddress( dto.getAddress() );
        if (entity.getCreateDate() == null) {
            entity.setCreateDate(LocalDateTime.now());
        } else {
            entity.setCreateDate(entity.getCreateDate());
        }
        entity.setLatitude( dto.getLatitude() );
        entity.setLongitude( dto.getLongitude() );

        List<TagEntity> allTags = dto.getTags().stream().map(tagMapper::dtoToEntity).toList();
        List<TagEntity> fromDb = tagRepository.findAllByNameTagIn(allTags.stream().map(TagEntity::getNameTag).collect(Collectors.toList()));
        List<TagEntity> notInDb = allTags.stream().filter(tag -> !fromDb.contains(tag)).toList();
        List<TagEntity> result = new java.util.ArrayList<>();
        result.addAll(tagRepository.saveAllAndFlush(notInDb));
        result.addAll(fromDb);
        entity.setTags(result);


        entity.setLikes( likeMapper.listDtoToEntity( dto.getLikes() ) );
        entity.setHeaderRequest( dto.getHeaderRequest() );
        entity.setTextRequest( dto.getTextRequest() );
        entity.setComments( commentMapper.listDtoToEntity( dto.getComments() ) );
        return entity;
    }
}
