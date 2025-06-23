package dev.trendio_back.dto.mapper;

import dev.trendio_back.dto.UserDto;
import dev.trendio_back.entity.auth.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {TagMapper.class,LikeMapper.class,RequestMapper.class,CommentMapper.class})
public interface UserMapper {
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "avatar.imageUrl", source = "avatar")
    UserEntity dtoToEntity(UserDto user);

    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "avatar.imageUrl", source = "avatar")
    List<UserEntity> listDtoToEntity(List<UserDto> user);

    @Mapping(target = "avatar", source = "avatar.imageUrl")
    UserDto entityToDto(UserEntity user);

    @Mapping(target = "avatar", source = "avatar.imageUrl")
    List<UserDto> listEntityToDto(List<UserEntity> user);
}
