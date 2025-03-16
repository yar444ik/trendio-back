package dev.trendio_back.dto.mapper;

import dev.trendio_back.dto.TagDto;
import dev.trendio_back.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class,CommentMapper.class,LikeMapper.class})
public interface TagMapper {
    @Mapping(target = "requests", ignore = true)
    @Mapping(source = "icon",target = "icon.imageUrl")
    TagEntity dtoToEntity(TagDto dto);

    @Mapping(target = "requests", ignore = true)
    @Mapping(source = "icon",target = "icon.imageUrl")
    List<TagEntity> listDtoToEntity(List<TagDto> dtos);

    @Mapping(target = "requests", ignore = true)
    @Mapping(source = "icon.imageUrl",target = "icon")
    TagDto entityToDto(TagEntity dto);

    @Mapping(target = "requests", ignore = true)
    @Mapping(source = "icon.imageUrl",target = "icon")
    List<TagDto> listEntityToDto(List<TagEntity> dto);
}
