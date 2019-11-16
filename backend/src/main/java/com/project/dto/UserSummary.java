package com.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserSummary {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date creationDate;
    private Date lastModificationDate;
}