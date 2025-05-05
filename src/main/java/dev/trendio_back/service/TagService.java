package dev.trendio_back.service;

import dev.trendio_back.dto.TagDto;
import dev.trendio_back.dto.mapper.TagMapper;
import dev.trendio_back.entity.TagEntity;
import dev.trendio_back.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TagService {

    private final TagRepository tagRepository;

    private final TagMapper tagMapper;

    public List<TagDto> findAll() {
        List<TagEntity> tagEntities = tagRepository.findAll();
        return tagEntities.stream().map(tagMapper::entityToDto).collect(Collectors.toList());
    }

    public TagDto create(TagDto tagDto) {
        return tagMapper.entityToDto(
                tagRepository.save(tagMapper.dtoToEntity(tagDto))
        );
    }

    public Optional<TagDto> findByName(String name) {
        return tagRepository.findByNameTag(name)
                .map(tagMapper::entityToDto);
    }
}
