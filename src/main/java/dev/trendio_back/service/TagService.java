package dev.trendio_back.service;

import dev.trendio_back.dto.TagDto;
import dev.trendio_back.dto.mapper.TagMapper;
import dev.trendio_back.entity.TagEntity;
import dev.trendio_back.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TagService {

    private final TagRepository tagRepository;

    private final TagMapper tagMapper;

    public List<TagDto> getTop25TagsByCount() {
        return tagRepository.findTop25TagsByCount().stream().map(tagMapper::entityToDto).toList();
    }

    public List<TagDto> findAll() {
        List<TagEntity> tagEntities = tagRepository.findAll();
        return tagEntities.stream().map(tagMapper::entityToDto).collect(Collectors.toList());
    }

    public List<TagDto> create(List<TagDto> tagDtos) {
        List<TagEntity> allTags = tagDtos.stream().map(tagMapper::dtoToEntity).toList();
        List<TagEntity> fromDb = tagRepository.findAllByNameTagIn(allTags.stream().map(TagEntity::getNameTag).collect(Collectors.toList()));
        List<TagEntity> notInDb = allTags.stream().filter(tag -> !fromDb.contains(tag)).toList();
        List<TagEntity> result = new java.util.ArrayList<>();
        result.addAll(tagRepository.saveAllAndFlush(notInDb));
        result.addAll(fromDb);
        return tagMapper.listEntityToDto(result);
    }
}
