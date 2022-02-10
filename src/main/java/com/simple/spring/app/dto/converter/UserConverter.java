package com.simple.spring.app.dto.converter;

import com.simple.spring.app.dto.UserDto;
import com.simple.spring.app.entity.User;

public class UserConverter {

    public static UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());

        return userDto;
    }

    public static User convertToUser(UserDto UserDto) {
        User user = new User();
        user.setId(UserDto.getId());
        user.setEmail(UserDto.getEmail());
        user.setPassword(UserDto.getPassword());

        return user;
    }
}
