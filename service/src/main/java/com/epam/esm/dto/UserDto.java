package com.epam.esm.dto;

import com.epam.esm.model.entity.Role;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class UserDto extends RepresentationModel<UserDto> {
    private Long id;
    private String email;
    private String name;
    private String secondName;
    private Role role;

    public UserDto(Long id, String name, String secondName, String email, Role role) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.email=email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        UserDto user = (UserDto) o;

        if (!Objects.equals(id, user.id)) {
            return false;
        }
        return Objects.equals(name, user.name) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + email.hashCode();
        return result;
    }
}
