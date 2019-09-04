package com.project.dto.user.role;

import com.project.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private Long roleId;
    private String name;

    public static RoleDto parse(Role role) {
        return RoleDto.builder()
                .roleId(role.getId())
                .name(role.getName())
                .build();
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                '}';
    }
}
