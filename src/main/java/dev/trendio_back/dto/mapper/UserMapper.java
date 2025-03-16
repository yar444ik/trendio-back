package dev.trendio_back.dto.mapper;

import dev.trendio_back.dto.UserDto;
import dev.trendio_back.entity.auth.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {TagMapper.class,LikeMapper.class,RequestMapper.class,CommentMapper.class})
public interface UserMapper {
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "role", source = "role")
    //@Mapping(target = "password", ignore = true)
    @Mapping(target = "avatar.imageUrl", source = "avatar")
    @Mapping(target = "likes", source = "likes")
    @Mapping(target = "comments", source = "comments")
    @Mapping(target = "requests", source = "requests")
    UserEntity dtoToEntity(UserDto user);

    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "role", source = "role")
    //@Mapping(target = "password", ignore = true)
    @Mapping(target = "avatar.imageUrl", source = "avatar")
    @Mapping(target = "likes", source = "likes")
    @Mapping(target = "comments", source = "comments")
    @Mapping(target = "requests", source = "requests")
    List<UserEntity> listDtoToEntity(List<UserDto> user);
}
