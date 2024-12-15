package com.moriya.project_moriya_java.security;

import com.moriya.project_moriya_java.model.Role;
import com.moriya.project_moriya_java.model.Users;
import com.moriya.project_moriya_java.service.UsersRepository;
import com.moriya.project_moriya_java.service.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UsersRepository userRepository;



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //לאמת את המשתמש עם המשתמש שנמצא ב-DB
        Users user=userRepository.findByName(username);
        if (user==null)
            throw new UsernameNotFoundException("user not found");
        List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
        for(Role role:user.getRoles())
        {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole().name()));
        }
        //יוצר משתמש עבור האבטחה
        return new CustomUserDetails(username,user.getPassword(),grantedAuthorities);
    }
}
