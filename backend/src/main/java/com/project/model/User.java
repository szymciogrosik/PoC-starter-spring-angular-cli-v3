package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "email"
        })
})
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String firstName;

    @NotBlank
    @Size(max = 40)
    private String lastName;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 4, max = 100)
    @JsonIgnore
    private String password;

    //Todo: walidacja daty
    private Date creationDate;

    //Todo: walidacja daty
    private Date lastModificationDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(
            @NotBlank @Size(max = 40)
                    String firstName,
            @NotBlank @Size(max = 40)
                    String lastName,
            @NotBlank @Size(max = 40) @Email
                    String email,
            @NotBlank @Size(min = 6, max = 100)
                    String password,
            @NotBlank
                    Set<Role> roles
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.creationDate = new Date();
        this.lastModificationDate = new Date();
        this.roles = roles;
    }
}
