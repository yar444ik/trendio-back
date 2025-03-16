package dev.trendio_back.dto.mapper;

import dev.trendio_back.dto.TagDto;
import dev.trendio_back.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {CommentMapper.class,LikeMapper.class})
public interface TagMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "requests",target = "requests")
    @Mapping(source = "icon",target = "icon.imageUrl")
    TagEntity tagDtoToEntity(TagDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "requests",target = "requests")
    @Mapping(source = "icon",target = "icon.imageUrl")
    List<TagEntity> listTagDtoToListEntity(List<TagDto> dtos);
}
