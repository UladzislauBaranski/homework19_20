package com.gmail.vladbaransky.service.impl;


import com.gmail.vladbaransky.service.UserService;
import com.gmail.vladbaransky.service.model.AppUser;
import com.gmail.vladbaransky.service.model.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public MyUserDetailsService(UserService userService) {this.userService = userService;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userService.loadUserByUsername(username);
        if (userDTO == null){
            throw new UsernameNotFoundException("User not found");
        }
        return new AppUser(userDTO);
    }

}
