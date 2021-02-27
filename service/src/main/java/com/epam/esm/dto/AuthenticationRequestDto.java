package com.epam.esm.dto;

import java.util.Objects;

public class AuthenticationRequestDto {
    private String email;
    private String password;

    public AuthenticationRequestDto() {}

    public AuthenticationRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthenticationRequestDto)) return false;
        AuthenticationRequestDto that = (AuthenticationRequestDto) o;
        return getEmail().equals(that.getEmail()) && getPassword().equals(that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword());
    }
}
