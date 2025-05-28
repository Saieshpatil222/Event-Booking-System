package com.user.controller;

import com.user.dto.ApiResponseDto;
import com.user.dto.UserDto;
import com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable String userId) {
        UserDto updatedUser = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable String userId) {
        UserDto singleUser = userService.getSingleUser(userId);
        return new ResponseEntity<>(singleUser, HttpStatus.OK);
    }

    @GetMapping("/internal/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> getSingleUserForBooking(@PathVariable String userId) {
        UserDto singleUser = userService.getSingleUser(userId);
        return new ResponseEntity<>(singleUser, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseDto> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        ApiResponseDto responseDto = ApiResponseDto.builder().message("User Deleted Successfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


}
