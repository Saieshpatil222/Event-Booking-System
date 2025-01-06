package com.user.service;

import com.user.dto.UserDto;
import com.user.entity.User;
import com.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class UserTestService {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    User user;

    @BeforeEach
    public void init() {
        user = User.builder().userName("Saiesh").emailId("patilsaiesh180@gmail.com").mobileNumber(9022281).build();
    }

    @Test
    public void createUser() {
        Mockito.when(userRepository.save(any())).thenReturn(user);
        UserDto u = modelMapper.map(user, UserDto.class);
        UserDto userDto = userService.createUser(u);
        assertNotNull(userDto);
        assertEquals("Saiesh", userDto.getUserName());
    }

    @Test
    public void deleteAccount(){
        String userId = "csfasvq";
        Mockito.when(userRepository.findById("csfasvq")).thenReturn(Optional.ofNullable(user));
        userService.deleteUser(userId);
        verify(userRepository, times(1)).delete(user);
    }
}
