package com.example.assspring.service;

import com.example.assspring.entity.UserEntity;
import com.example.assspring.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("username not found");
        }
        SimpleGrantedAuthority grand = new SimpleGrantedAuthority(userEntity.getRole().getName());
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(grand);
        User user = new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
        return user;
    }

    @PreAuthorize("hasAuthority('admin')")
    public String deleteUser() {
        return "success";
    }
}
