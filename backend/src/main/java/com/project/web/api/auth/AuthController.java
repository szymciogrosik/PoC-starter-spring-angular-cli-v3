package com.project.web.api.auth;

import com.project.config.exception.CustomizeException;
import com.project.config.exception.ExceptionCode;
import com.project.config.security.jwt.JwtTokenUtil;
import com.project.dto.user.UserDto;
import com.project.repository.user.UserRepository;
import com.project.web.service.util.UtilService;
import lombok.Data;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Data
@RestController
@ConfigurationProperties(prefix = "jwt")
public class AuthController {

    private String header;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UtilService utilService;

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @SneakyThrows
    @PostMapping("/api/auth")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody String authData) {
        JSONObject jsonData = utilService.parseToJSONObject(authData);

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        jsonData.getString(USERNAME),
                        jsonData.getString(PASSWORD)
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(jsonData.getString(USERNAME));
        final String token = jwtTokenUtil.generateToken(userDetails);
        final String username = userDetails.getUsername();

        UserDto userDto = UserDto.parse(userRepository.findByUsername(username));
        userDto.setToken(token);

        if (userDto.getBanned())
            throw new CustomizeException(ExceptionCode.USER_IS_BANNED.getMessage());
        else return ResponseEntity
                .status(HttpStatus.OK)
                .body(utilService.parseToJSON(userDto));
    }

    @SneakyThrows
    @GetMapping("/api/refresh")
    public ResponseEntity<String> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = utilService.getTokenFromHeader(request);
        String username = utilService.getUsernameFromHeader(request);

        if (!jwtTokenUtil.isTokenExpired(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);

            UserDto userDto = UserDto.parse(userRepository.findByUsername(username));
            userDto.setToken(refreshedToken);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(utilService.parseToJSON(userDto));
        } else {
            return ResponseEntity.badRequest().body("Sesja wygasła! Zaloguj się ponownie.");
        }
    }

}

