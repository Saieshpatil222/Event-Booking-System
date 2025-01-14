package com.user.service.impl;

import com.user.dto.UserDto;
import com.user.entity.User;
import com.user.exception.ResourceNotFoundException;
import com.user.repository.UserRepository;
import com.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = modelMapper.map(userDto, User.class);
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(List.of("ROLE_NORMAL"));
        User user1 = userRepository.save(user);
        return modelMapper.map(user1, UserDto.class);

    }

    @Override
    public UserDto getSingleUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found With Given UserId =" + userId));
        UserDto dto = modelMapper.map(user, UserDto.class);
        return dto;
    }

    @Override
    public List<UserDto> getAllUsers() {//use stream api because we need to map each record into dto so we need stream api
        List<User> user = userRepository.findAll();
        return user.stream().map(users -> modelMapper.map(users, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found With Given UserId=" + userId));
        user.setUserName(userDto.getUserName());
        user.setEmailId(userDto.getEmailId());
        user.setMobileNumber(userDto.getMobileNumber());
        userRepository.save(user);
        UserDto userDto1 = modelMapper.map(user, UserDto.class);
        return userDto1;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found With Given UserId=" + userId));
        userRepository.delete(user);
    }
}
