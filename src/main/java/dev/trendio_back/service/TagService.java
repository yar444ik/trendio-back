package dev.trendio_back.service;

import dev.trendio_back.dto.TagDto;
import dev.trendio_back.dto.mapper.TagMapper;
import dev.trendio_back.entity.TagEntity;
import dev.trendio_back.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
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
        Optional<TagEntity> existingTag = tagRepository.findByNameTag(tagDto.getNameTag());
        if (existingTag.isPresent()) {
            return tagMapper.entityToDto(existingTag.get());
        }
        return tagMapper.entityToDto(
                tagRepository.save(tagMapper.dtoToEntity(tagDto))
        );
    }

    public TagDto update(TagDto tagDto) {
        TagEntity tagEntity = tagRepository.findById(tagDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + tagDto.getId()));

        if (!tagEntity.getNameTag().equals(tagDto.getNameTag())) {
            Optional<TagEntity> duplicateTag = tagRepository.findByNameTag(tagDto.getNameTag());
            if (duplicateTag.isPresent()) {
                throw new IllegalArgumentException("Tag with name '" + tagDto.getNameTag() + "' already exists!");
            }
        }

        tagEntity.setNameTag(tagDto.getNameTag());
        return tagMapper.entityToDto(tagRepository.save(tagEntity));
    }
}
