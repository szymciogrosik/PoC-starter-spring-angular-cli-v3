package com.project.web.service.user;

import com.project.config.exception.CustomizeException;
import com.project.config.exception.ExceptionCode;
import com.project.dto.user.FilterUserDto;
import com.project.dto.user.UserDto;
import com.project.model.Role;
import com.project.model.User;
import com.project.repository.role.RoleRepository;
import com.project.repository.user.UserRepository;
import com.project.web.service.util.constant.ROLE;
import lombok.Data;
import lombok.Setter;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
@ConfigurationProperties(prefix = "pager")
class UserServiceImpl implements UserDetailsService, UserService {

    @Setter
    private int maxPageSize;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;


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
    public List<UserDto> getAllOnPage(int pageNumber) {
        return userRepository
                .findAll(new PageRequest(pageNumber, maxPageSize))
                .getContent()
                .stream()
                .map(UserDto::parse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAllUserByFilter(FilterUserDto filterUser, Integer pageNumber) {
        filterUser.setSearchPhrase("%" + filterUser.getSearchPhrase() + "%");

        if (filterUser.getBanned() == -1)
            return userRepository.findAllByBannedAndUsernameLikeOrFirstNameLikeOrLastNameLikeOrEmailLike(
                    Boolean.FALSE,
                    filterUser.getSearchPhrase(),
                    filterUser.getSearchPhrase(),
                    filterUser.getSearchPhrase(),
                    filterUser.getSearchPhrase(),
                    new PageRequest(pageNumber, maxPageSize))
                    .getContent()
                    .stream()
                    .map(UserDto::parse)
                    .collect(Collectors.toList());
        else if (filterUser.getBanned() == 1)
            return userRepository.findAllByBannedAndUsernameLikeOrFirstNameLikeOrLastNameLikeOrEmailLike(
                    Boolean.TRUE,
                    filterUser.getSearchPhrase(),
                    filterUser.getSearchPhrase(),
                    filterUser.getSearchPhrase(),
                    filterUser.getSearchPhrase(),
                    new PageRequest(pageNumber, maxPageSize))
                    .getContent()
                    .stream()
                    .map(UserDto::parse)
                    .collect(Collectors.toList());
        else return userRepository.findAllByUsernameLikeOrFirstNameLikeOrLastNameLikeOrEmailLike(
                    filterUser.getSearchPhrase(),
                    filterUser.getSearchPhrase(),
                    filterUser.getSearchPhrase(),
                    filterUser.getSearchPhrase(),
                    new PageRequest(pageNumber, maxPageSize))
                    .getContent()
                    .stream()
                    .map(UserDto::parse)
                    .collect(Collectors.toList());
    }

    @Override
    public Integer getNumberUserAvailablePageByFilter(FilterUserDto filterUser) {
        if (filterUser.getBanned() == -1)
            return (int) Math.ceil(
                    userRepository.countAllByBannedAndUsernameLikeOrFirstNameLikeOrLastNameLikeOrEmailLike(
                            Boolean.FALSE,
                            filterUser.getSearchPhrase(),
                            filterUser.getSearchPhrase(),
                            filterUser.getSearchPhrase(),
                            filterUser.getSearchPhrase()) / (double) maxPageSize);
        else if (filterUser.getBanned() == 1)
            return (int) Math.ceil(
                    userRepository.countAllByBannedAndUsernameLikeOrFirstNameLikeOrLastNameLikeOrEmailLike(
                            Boolean.TRUE,
                            filterUser.getSearchPhrase(),
                            filterUser.getSearchPhrase(),
                            filterUser.getSearchPhrase(),
                            filterUser.getSearchPhrase()) / (double) maxPageSize);
        else return (int) Math.ceil(
                    userRepository.countAllByUsernameLikeOrFirstNameLikeOrLastNameLikeOrEmailLike(
                            filterUser.getSearchPhrase(),
                            filterUser.getSearchPhrase(),
                            filterUser.getSearchPhrase(),
                            filterUser.getSearchPhrase()) / (double) maxPageSize);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean createUser(UserDto newUser) {
        Role roleUser = roleRepository.findByName(ROLE.USER.getRoleName());
        List<Role> roles = new ArrayList<>();
        roles.add(roleUser);

        User user = User.builder()
                .id(null)
                .username(newUser.getUsername())
                .password(newUser.getPassword())
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .email(newUser.getEmail())
                .accountCreationDate(new Date())
                .lastModificationDate(new Date())
                .banned(false)
                .roles(roles)
                .build();

        return (userRepository.save(user) != null);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public UserDto updateUser(UserDto updatedUser) {
        User oldUser = userRepository.findById(updatedUser.getUserId());

        User user = User.builder()
                .id(updatedUser.getUserId())
                .username(oldUser.getUsername())
                .password(oldUser.getPassword())
                .firstName(updatedUser.getFirstName())
                .lastName(updatedUser.getLastName())
                .email(updatedUser.getEmail())
                .accountCreationDate(oldUser.getAccountCreationDate())
                .lastModificationDate(new Date())
                .banned(oldUser.getBanned())
                .roles(oldUser.getRoles())
                .build();

        return UserDto.parse(userRepository.save(user));
    }

    @Override
    @SneakyThrows
    public UserDto changePassword(UserDto user) throws CustomizeException {
        JSONObject jsonData = new JSONObject(user.getPassword());

        String oldPassword = jsonData.getString("oldPassword");
        String newPassword = jsonData.getString("newPassword");

        User dbUser = userRepository.findById(user.getUserId());

        if (oldPassword.equals(dbUser.getPassword())) {
            dbUser.setPassword(newPassword);
            return UserDto.parse(userRepository.save(dbUser));
        } else throw new CustomizeException(ExceptionCode.WRONG_PASSWORD.getMessage());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean banUser(Long userId) {
        User user = userRepository.findById(userId);
        user.setBanned(!user.getBanned());
        return (userRepository.save(user) != null);
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
