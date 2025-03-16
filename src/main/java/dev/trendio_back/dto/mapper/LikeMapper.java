package dev.trendio_back.dto.mapper;

import dev.trendio_back.dto.LikeDto;
import dev.trendio_back.dto.TagDto;
import dev.trendio_back.entity.LikeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {TagMapper.class,UserMapper.class,RequestMapper.class})
public interface LikeMapper {
    //@Mapping(source = "",target = "")
    @Mapping(source = "username",target = "username.id")
    @Mapping(source = "request",target = "request.id")
    LikeEntity likeDtoToEntity(LikeDto dto);

    @Mapping(source = "username",target = "username.id")
    @Mapping(source = "request",target = "request.id")
    List<LikeEntity> listLikeDtoToListEntity(List<LikeDto> dtos);
}
