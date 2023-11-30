package com.example.chat.web.mapper;

import com.example.chat.model.User;
import com.example.chat.web.dto.UserDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

//    @Mapping(target = )
    UserDto toDto(User user);

}
