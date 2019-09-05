package com.project.service;

import org.springframework.stereotype.Service;
import com.project.dto.UserSummary;
import com.project.security.UserPrincipal;

@Service
public class UserService {

    public UserSummary getCurrentUser(UserPrincipal userPrincipal) {
        return UserSummary.builder()
                .id(userPrincipal.getId())
                .email(userPrincipal.getEmail())
                .name(userPrincipal.getName())
                .build();
    }
}
