package com.project.web.service.user;

import com.project.config.exception.CustomizeException;
import com.project.dto.user.FilterUserDto;
import com.project.dto.user.UserDto;

import java.util.List;

public interface UserService {

    UserDto getUserById(Long userId);
    UserDto getByUsername(String username);
    List<UserDto> getAllOnPage(int pageNumber);

    List<UserDto> getAllUserByFilter(FilterUserDto filterUser, Integer pageNumber);
    Integer getNumberUserAvailablePageByFilter(FilterUserDto filterUser);

    Boolean createUser(UserDto newUser);
    UserDto updateUser(UserDto updatedUser);
    UserDto changePassword(UserDto user) throws CustomizeException;

    Boolean banUser(Long userId);

}
