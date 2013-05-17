package org.emaginalabs.example.springdata.response;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class UserDto {

    private String username;

    private SecurityRole role;

    public UserDto(String username, SecurityRole role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public SecurityRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
