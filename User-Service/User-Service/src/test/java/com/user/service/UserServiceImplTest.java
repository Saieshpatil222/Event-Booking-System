package com.user.service;

import com.user.dto.UserDto;
import com.user.entity.User;
import com.user.repository.UserRepository;
import com.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.listeners.MockitoListener;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser("ROLE_NORMAL")
    void createUserTest() {
        UserDto userDto = UserDto.builder().userName("abc").mobileNumber(918182892).password("abc123").build();
        User user = User.builder().userName("abc").mobileNumber(918182892).password("abc123").build();
        User savedUser = User.builder().userName("abc").mobileNumber(918182892).password("encodedPassword").build();

        Mockito.when(modelMapper.map(any(UserDto.class), any(Class.class))).thenReturn(user);
        Mockito.when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        Mockito.when(userRepository.save(any(User.class))).thenReturn(savedUser);
        Mockito.when(modelMapper.map(any(User.class), any(Class.class))).thenReturn(userDto);

        UserDto createdUser = userService.createUser(userDto);
        Assertions.assertNotNull(createdUser);
    }

    @Test
    @WithMockUser("ROLE_NORMAL")
    void createUserTestNegativeScenario() {
        UserDto userDto = UserDto.builder().userName("abc").mobileNumber(918182892).password("abc123").build();
        User user = User.builder().userName("abc").mobileNumber(918182892).password("abc123").build();
        Mockito.when(modelMapper.map(any(UserDto.class), any(Class.class))).thenReturn(user);
        Mockito.when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        Mockito.when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Database error"));

        Assertions.assertThrows(RuntimeException.class, () -> {
            userService.createUser(userDto);
        });
    }

    @Test
    @WithMockUser("ROLE_ADMIN")
    void getSingleUserTest() {
        String userId = "123";
        UserDto userDto = UserDto.builder().password("abc123").userName("Abc").userId("123").mobileNumber(12334443).build();
        User user = User.builder().userName("abc").mobileNumber(918182892).password("abc123").build();

        Mockito.when(this.userRepository.findById(anyString())).thenReturn(Optional.of(user));
        Mockito.when(this.modelMapper.map(any(User.class), any(Class.class))).thenReturn(userDto);

        UserDto singleUser = this.userService.getSingleUser(userId);
        Assertions.assertNotNull(singleUser);
    }

    @Test
    @WithMockUser("ROLE_ADMIN")
    void getAllUsersTest() {
        User user1 = User.builder().userName("abc").password("abc123").mobileNumber(1223333).emailId("abc@gmail.com").build();
        User user2 = User.builder().userName("xyz").password("xyz123").mobileNumber(19128829).emailId("xyz@gmail.com").build();
        User user3 = User.builder().userName("mno").password("mno123").mobileNumber(3535362).emailId("mno@gmail.com").build();

        UserDto userDto1 = UserDto.builder().userName("abc").password("abc123").mobileNumber(1223333).emailId("abc@gmail.com").build();
        UserDto userDto2 = UserDto.builder().userName("xyz").password("xyz123").mobileNumber(19128829).emailId("xyz@gmail.com").build();
        UserDto userDto3 = UserDto.builder().userName("mno").password("mno123").mobileNumber(3535362).emailId("mno@gmail.com").build();

        List<User> userList = Arrays.asList(user1, user2, user3);
        List<UserDto> userDtoList = Arrays.asList(userDto1, userDto2, userDto3);

        Mockito.when(userRepository.findAll()).thenReturn(userList);
        Mockito.when(modelMapper.map(user1, UserDto.class)).thenReturn(userDto1);
        Mockito.when(modelMapper.map(user2, UserDto.class)).thenReturn(userDto2);
        Mockito.when(modelMapper.map(user3, UserDto.class)).thenReturn(userDto3);

        List<UserDto> allUsers = userService.getAllUsers();
        Assertions.assertNotNull(allUsers);
        Assertions.assertEquals(userDtoList.size(), allUsers.size());
    }

    @Test
    public void deleteUser() {
        String userId = "123";
        UserDto userDto = UserDto.builder().password("abc123").userName("Abc").userId("123").mobileNumber(12334443).build();
        User user = User.builder().userName("abc").mobileNumber(918182892).password("abc123").build();

        Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        this.userService.deleteUser(userId);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void updateUser() {
        String userId = "123";
        UserDto userDto = UserDto.builder().password("abc123").userName("Abc").userId("123").mobileNumber(12334443).build();
        User user = User.builder().userName("abc").mobileNumber(918182892).password("abc123").build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        Mockito.when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        UserDto savedUserDto = userService.updateUser(userDto, userId);
        Assertions.assertNotNull(savedUserDto);
    }

}