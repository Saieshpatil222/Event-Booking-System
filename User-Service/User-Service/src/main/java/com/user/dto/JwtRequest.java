package com.user.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtRequest {

    private String emailId;

    private String password;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
