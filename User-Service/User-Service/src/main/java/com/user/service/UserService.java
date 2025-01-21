package com.user.service;

import com.user.dto.UserDto;
import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getSingleUser(String userId);

    List<UserDto> getAllUsers();

    UserDto updateUser(UserDto userDto, String userId);

    void deleteUser(String userId);
}
