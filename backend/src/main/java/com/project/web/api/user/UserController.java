package com.project.web.api.user;

import com.project.config.exception.CustomizeException;
import com.project.dto.user.FilterUserDto;
import com.project.dto.user.UserDto;
import com.project.dto.user.UserPagerDto;
import com.project.web.service.user.UserService;
import com.project.web.service.util.UtilService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UtilService utilService;

    @PostMapping("/api/user/create")
    public ResponseEntity createNewAccount(@RequestBody UserDto newUserDto) {
        if (userService.createUser(newUserDto))
            return ResponseEntity.status(HttpStatus.OK).build();
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/api/user/update")
    public ResponseEntity updateAccount(@RequestBody UserDto modifyUserDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateUser(modifyUserDto));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/api/user/update/password")
    public ResponseEntity changeAccountPassword(@RequestBody UserDto modifyUserDto) {
        try {
            userService.changePassword(modifyUserDto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (CustomizeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @SneakyThrows
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/user/{userId}")
    public ResponseEntity getUserById(@PathVariable Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(utilService.parseToJSON(userService.getUserById(userId)));
    }

    @SneakyThrows
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/user/getAllOnPage")
    public ResponseEntity getUsersOnPage(@Param("pageNumber") Integer pageNumber) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(utilService.parseToJSON(userService.getAllOnPage(pageNumber)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/user/{userId}/ban")
    public ResponseEntity banUser(@PathVariable Long userId) {
        if (userService.banUser(userId))
            return ResponseEntity.status(HttpStatus.OK).build();
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/user/getAllFilteredOnPage")
    public ResponseEntity getAllUsersOnPage(@RequestBody FilterUserDto filterUser, @Param("pageNumber") Integer pageNumber) {
        List<UserDto> users = userService.getAllUserByFilter(filterUser, pageNumber);
        Integer numberAvailablePages = userService.getNumberUserAvailablePageByFilter(filterUser);

        UserPagerDto response = UserPagerDto.builder()
                .users(users)
                .numberAvailablePages(numberAvailablePages)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(utilService.parseToJSON(response));
    }

}
