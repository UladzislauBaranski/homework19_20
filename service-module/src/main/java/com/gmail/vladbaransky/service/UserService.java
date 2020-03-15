package com.gmail.vladbaransky.service;

import com.gmail.vladbaransky.service.model.ItemDTO;
import com.gmail.vladbaransky.service.model.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO loadUserByUsername(String username);

    List<UserDTO> findAllUsers();

    List<ItemDTO> findItemsByRole();

}

