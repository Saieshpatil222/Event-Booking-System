package com.user.dto;

import lombok.*;

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
}
