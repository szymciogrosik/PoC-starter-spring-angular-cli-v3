package com.project.dto.user;

import lombok.Data;

@Data
public class FilterUserDto {

    private String searchPhrase;
    private Integer banned;

}
