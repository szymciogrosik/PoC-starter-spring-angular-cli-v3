package com.project.web.api.user;

import com.project.web.service.user.UserService;
import com.project.web.service.util.UtilService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UtilService utilService;

    @SneakyThrows
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/user/{userId}")
    public ResponseEntity getUserById(@PathVariable Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(utilService.parseToJSON(userService.getUserById(userId)));
    }
}
