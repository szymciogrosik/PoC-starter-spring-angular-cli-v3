package com.project.web.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.config.security.jwt.JwtTokenUtil;
import com.project.web.service.user.UserService;
import lombok.Setter;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Scope(scopeName = "singleton")
@ConfigurationProperties(prefix = "jwt")
public class UtilService {

    @Setter
    private String header;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    public String parseToJSON(Object o) {
        return mapper.writeValueAsString(o);
    }

    @SneakyThrows
    public JSONObject parseToJSONObject(String s) {
        return new JSONObject(s);
    }

    public String getUsernameFromHeader(HttpServletRequest request) {
        return jwtTokenUtil.getUsernameFromToken(request.getHeader(header));
    }

    public Long getUserIdFromHeader(HttpServletRequest request) {
        if (getUsernameFromHeader(request) != null)
            return userService.getByUsername(getUsernameFromHeader(request)).getUserId();
        else return null;
    }

    public String getTokenFromHeader(HttpServletRequest request) {
        return request.getHeader(header);
    }

}

