package com.project.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {
    private String token;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }
}
