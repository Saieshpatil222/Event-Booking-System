package com.user.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String userId;

    private String userName;

    private int mobileNumber;

    private String emailId;

    private String password;

    private List<String> roles;
}
