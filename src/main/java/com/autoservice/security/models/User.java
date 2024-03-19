package com.autoservice.security.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;


@ApiModel(description = "Model of user data ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class User implements UserDetails {

    @ApiModelProperty(notes = "The unique id of the user")
    private Long id;

    @ApiModelProperty(notes = "Username of the user")
    @NotEmpty
    private String username;

    @ApiModelProperty(notes = "Email of the user")
    @NotEmpty
    private String email;

    @ApiModelProperty(notes = "Password of the user")
    @NotEmpty
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
