package com.akasa.food.order.service;


import com.akasa.food.order.models.User;
import com.akasa.food.order.models.UserRole;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserInfoUserDetails implements UserDetails {


    private Long id;
    private String name;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoUserDetails(User userInfo) {
        id=userInfo.getId();
        name=userInfo.getName();
        password=userInfo.getPassword();
        authorities= userInfo.getRoles().stream()
                .map(UserRole::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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