package com.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.dto.UserDto;
import com.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build(); //The standaloneSetup() method is used when you want to test a single controller without needing to set up a full Spring application context (like beans, service layers, etc.).
        objectMapper = new ObjectMapper();
    }

    @Test
    void createUserTest() throws Exception {
        UserDto requestDto = UserDto.builder()
                .userId("user123")
                .userName("John")
                .emailId("john@example.com")
                .mobileNumber(1234567890L)
                .password("password123")
                .build();

        UserDto responseDto = UserDto.builder()
                .userId("user123")
                .userName("John")
                .emailId("john@example.com")
                .mobileNumber(1234567890L)
                .password("password123")
                .build();

        when(userService.createUser(any(UserDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value("user123"))
                .andExpect(jsonPath("$.userName").value("John"))
                .andExpect(jsonPath("$.emailId").value("john@example.com"))
                .andExpect(jsonPath("$.mobileNumber").value(1234567890))
                .andDo(print());

        verify(userService).createUser(any(UserDto.class));
    }

    @Test
    public void getSingleUserTest() throws Exception {
        String userId = "abc123";
        UserDto userDto = UserDto.builder().userId("abc123").userName("ABC").emailId("abc@gmail.com").mobileNumber(218768).password("hg").build();
        when(userService.getSingleUser(userId)).thenReturn(userDto);

        mockMvc.perform(get("/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("abc123"))
                .andExpect(jsonPath("$.userName").value("ABC"))
                .andExpect(jsonPath("$.emailId").value("abc@gmail.com"))
                .andDo(print());
        verify(userService).getSingleUser(userId);
    }

    @Test
    public void getAllUsersTest() throws Exception {
        UserDto userDto1 = UserDto.builder().userName("abc").password("abc123").mobileNumber(1223333).emailId("abc@gmail.com").build();
        UserDto userDto2 = UserDto.builder().userName("xyz").password("xyz123").mobileNumber(19128829).emailId("xyz@gmail.com").build();
        UserDto userDto3 = UserDto.builder().userName("mno").password("mno123").mobileNumber(3535362).emailId("mno@gmail.com").build();
        List<UserDto> userDtoList = Arrays.asList(userDto1, userDto2, userDto3);
        Mockito.when(userService.getAllUsers()).thenReturn(userDtoList);

        mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN"))
                        .content(objectMapper.writeValueAsString(userDtoList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].emailId").value("abc@gmail.com"))
                .andExpect(jsonPath("$[0].userName").value("abc"))
                .andExpect(jsonPath("$[0].mobileNumber").value(1223333))
                .andExpect(jsonPath("$[1].emailId").value("xyz@gmail.com"))
                .andExpect(jsonPath("$", hasSize(3)));
        verify(userService).getAllUsers();
    }

    @Test
    public void deleteUserTest() throws Exception {
        String userId = "buyout";
        UserDto userDto = UserDto.builder().userName("abc").password("abc123").mobileNumber(1223333).emailId("abc@gmail.com").build();
        Mockito.doNothing().when(userService).deleteUser(userId);

        mockMvc.perform(delete("/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("normal").roles("NORMAL")))
                .andExpect(status().isOk());
        verify(userService).deleteUser(userId);
    }

    @Test
    public void updateUserTest() throws Exception {
        String userId = "unsubdued";
        UserDto userDto = UserDto.builder().userName("abc").password("abc123").mobileNumber(1223333).emailId("abc@gmail.com").build();
        UserDto updatedUser = UserDto.builder().userName("abc").password("abc@123").mobileNumber(1223333).emailId("abc@gmail.com").build();
        Mockito.when(userService.updateUser(any(UserDto.class), eq(userId))).thenReturn(updatedUser);

        mockMvc.perform(put("/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("normal").roles("NORMAL"))
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("abc"))
                .andExpect(jsonPath("$.password").value("abc@123"))
                .andExpect(jsonPath("$.emailId").value("abc@gmail.com"))
                .andExpect(status().isOk())
                .andDo(print());
        verify(userService).updateUser(any(UserDto.class), eq(userId));
    }

}