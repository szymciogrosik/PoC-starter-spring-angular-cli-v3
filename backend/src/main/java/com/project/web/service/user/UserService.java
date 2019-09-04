package com.project.web.service.user;

import com.project.config.exception.CustomizeException;
import com.project.dto.user.FilterUserDto;
import com.project.dto.user.UserDto;

import java.util.List;

public interface UserService {

    UserDto getUserById(Long userId);
    UserDto getByUsername(String username);

}
