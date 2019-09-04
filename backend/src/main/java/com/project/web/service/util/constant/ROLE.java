package com.project.web.service.util.constant;

public enum ROLE {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String roleName;

    ROLE(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
