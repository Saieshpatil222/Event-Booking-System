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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserTestService {

    @Autowired
  //  @Mock
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    @MockBean
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
    public void getAllUsersTest() {
        User user1 = User.builder().userId("1").userName("XYZ").mobileNumber(12345678).emailId("xyz@gmail.com").build();

        User user2 = User.builder().userId("2").userName("ABC").mobileNumber(98765349).emailId("abc@gmail.com").build();

        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        UserDto userDto1 = UserDto.builder().userId("1").userName("XYZ").mobileNumber(12345678).emailId("xyz@gmail.com").build();

        UserDto userDto2 = UserDto.builder().userId("2").userName("ABC").mobileNumber(987654321).emailId("abc@gmail.com").build();

        when(modelMapper.map(user1, UserDto.class)).thenReturn(userDto1);
        when(modelMapper.map(user2, UserDto.class)).thenReturn(userDto2);

        List<UserDto> userDtos = userService.getAllUsers();

        assertNotNull(userDtos);
        assertEquals(2, userDtos.size());
        assertEquals("XYZ", userDtos.get(0).getUserName());
        assertEquals("ABC", userDtos.get(1).getUserName());

        verify(userRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(user1, UserDto.class);
        verify(modelMapper, times(1)).map(user2, UserDto.class);
    }

    @Test
    public void deleteUser() {
        String useId = "1";
        when(userRepository.findById(useId)).thenReturn(Optional.of(user));
        userService.deleteUser(useId);
        verify(userRepository, times(1)).delete(user);
    }

}
