package com.example.chat.utils.mapper;

import com.example.chat.model.User;
import com.example.chat.utils.dto.UserDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

//    @Mapping(target = )
    UserDto toDto(User user);

}
