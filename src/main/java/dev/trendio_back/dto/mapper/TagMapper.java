package dev.trendio_back.dto.mapper;

import dev.trendio_back.dto.TagDto;
import dev.trendio_back.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TagMapper {
    TagEntity dtoToEntity(TagDto dto);

    List<TagEntity> listDtoToEntity(List<TagDto> dtos);

    TagDto entityToDto(TagEntity entity);

    List<TagDto> listEntityToDto(List<TagEntity> entities);
}
