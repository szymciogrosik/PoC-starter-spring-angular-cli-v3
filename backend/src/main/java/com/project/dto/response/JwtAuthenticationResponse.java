package com.project.dto.response;

import com.project.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {
    private String token;
    private String tokenType = "Bearer";
    private User user;

    public JwtAuthenticationResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
