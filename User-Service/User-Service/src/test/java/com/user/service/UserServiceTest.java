package com.user.service;

import com.user.dto.UserDto;
import com.user.entity.User;
import com.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    @Mock
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    User user;

    @BeforeEach
    public void init() {
        user = User.builder().userName("Saiesh").emailId("patilsaiesh180@gmail.com").mobileNumber(9022281).password("9uihu").build();
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
    public void deleteAccount() {
        String userId = "csfasvq";
        when(userRepository.findById("csfasvq")).thenReturn(Optional.ofNullable(user));
        userService.deleteUser(userId);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void getSingleUser() {
        String userId = "abc1234";
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        UserDto dto = userService.getSingleUser(userId);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getUsername(), user.getUsername(), "Name is not matching");
    }

    @Test
    public void getAllUser() {
        User user1 = User.builder().userName("ABC").password("bgytuvuyi").emailId("abc@Gmail.com").mobileNumber(867574).build();
        List<User> users = Arrays.asList(user, user1);
        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<UserDto> userList = userService.getAllUsers();
        Assertions.assertNotNull(userList);
    }

    @Test
    public void updateUser() {
        String userId = "dytf7uf6";
        UserDto user1 = UserDto.builder().userName("ABC").password("bgytuvuyi").emailId("abc@Gmail.com").mobileNumber(867574).build();
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto userDto = userService.updateUser(user1, userId);
        Assertions.assertNotNull(userDto);
    }

}
