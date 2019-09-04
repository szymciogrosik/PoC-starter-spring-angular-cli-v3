package com.project.web.service.user;

import com.project.config.exception.CustomizeException;
import com.project.config.exception.ExceptionCode;
import com.project.dto.user.UserDto;
import com.project.model.User;
import com.project.repository.role.RoleRepository;
import com.project.repository.user.UserRepository;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Data
@Service
@ConfigurationProperties(prefix = "pager")
class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto getUserById(Long userId) {
        return UserDto.parse(userRepository.findById(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getByUsername(String username) {
        return UserDto.parse(userRepository.findByUsername(username));
    }

    @Override
    @SneakyThrows
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new CustomizeException(ExceptionCode.USER_NOT_FOUND.getMessage());
        else return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }
}
