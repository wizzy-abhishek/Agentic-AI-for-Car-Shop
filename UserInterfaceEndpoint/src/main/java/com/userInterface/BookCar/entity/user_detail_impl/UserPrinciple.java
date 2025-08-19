package com.userInterface.BookCar.entity.user_detail_impl;

import com.userInterface.BookCar.entity.AppUsers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserPrinciple implements UserDetails {

    private AppUsers appUsers;

    public UserPrinciple(AppUsers appUser) {
        this.appUsers = appUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        String roleName = appUsers.getUserRole().name();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + roleName));

        return authorities;
    }

    public AppUsers getAppUsers() {
        return appUsers;
    }

    @Override
    public String getPassword() {
        return appUsers.getPassword();
    }

    @Override
    public String getUsername() {
        return appUsers.getEmail();
    }
}
