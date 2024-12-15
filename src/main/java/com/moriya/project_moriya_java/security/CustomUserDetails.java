package com.moriya.project_moriya_java.security;

import com.moriya.project_moriya_java.model.Role;
import com.moriya.project_moriya_java.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends User {
    private Users user;

    public CustomUserDetails(String name, String password, Collection<? extends GrantedAuthority> authorities) {
        super(name, password, authorities);
    }


}
