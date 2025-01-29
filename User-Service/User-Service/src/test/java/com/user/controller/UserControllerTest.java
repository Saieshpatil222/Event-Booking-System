package com.user.controller;

import com.user.dto.ApiResponseDto;
import com.user.dto.UserDto;
import com.user.service.UserService;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void createUserTest() {
        UserDto userDto = UserDto.builder().userId("jh").userName("ABC").emailId("abc@gmail.com").mobileNumber(218768).password("hg").build();
        Mockito.when(userService.createUser(userDto)).thenReturn(userDto);
        ResponseEntity<UserDto> response = userController.createUser(userDto);
        Assertions.assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDto.getUserId(), response.getBody().getUserId());
        assertEquals(userDto.getMobileNumber(), response.getBody().getMobileNumber());
        Mockito.verify(userService, Mockito.times(1)).createUser(userDto);
    }

    @Test
    public void getSingleUserTest() {
        String userId = "abc123";
        UserDto userDto = UserDto.builder().userId("abc123").userName("ABC").emailId("abc@gmail.com").mobileNumber(218768).password("hg").build();
        Mockito.when(userService.getSingleUser(userId)).thenReturn(userDto);
        ResponseEntity<UserDto> singleUser = userController.getSingleUser(userId);
        Assertions.assertNotNull(singleUser);
        assertEquals(userId, userDto.getUserId());
        assertEquals(HttpStatus.OK, singleUser.getStatusCode());
        assertEquals(userDto.getPassword(), singleUser.getBody().getPassword());
        assertEquals(userDto.getEmailId(), singleUser.getBody().getEmailId());
    }

    @Test
    public void getAllUsersTest() {
        UserDto userDto1 = UserDto.builder().userName("abc").password("abc123").mobileNumber(1223333).emailId("abc@gmail.com").build();
        UserDto userDto2 = UserDto.builder().userName("xyz").password("xyz123").mobileNumber(19128829).emailId("xyz@gmail.com").build();
        UserDto userDto3 = UserDto.builder().userName("mno").password("mno123").mobileNumber(3535362).emailId("mno@gmail.com").build();
        List<UserDto> userDtoList = Arrays.asList(userDto1, userDto2, userDto3);
        Mockito.when(userService.getAllUsers()).thenReturn(userDtoList);
        ResponseEntity<List<UserDto>> allUsers = userController.getAllUsers();
        Assertions.assertNotNull(allUsers);
        assertEquals(HttpStatus.OK, allUsers.getStatusCode());
        assertEquals(userDtoList.size(), allUsers.getBody().size());
    }

    @Test
    public void deleteUserTest() {
        String userId = "buyout";
        UserDto userDto1 = UserDto.builder().userName("abc").password("abc123").mobileNumber(1223333).emailId("abc@gmail.com").build();
        Mockito.doNothing().when(userService).deleteUser(userId);
        ResponseEntity<ApiResponseDto> response = userController.deleteUser(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
    }

}