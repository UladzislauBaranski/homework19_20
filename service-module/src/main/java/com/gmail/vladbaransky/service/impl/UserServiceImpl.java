package com.gmail.vladbaransky.service.impl;


import com.gmail.vladbaransky.repository.ItemRepository;
import com.gmail.vladbaransky.repository.UserRepository;
import com.gmail.vladbaransky.repository.model.Item;
import com.gmail.vladbaransky.repository.model.User;
import com.gmail.vladbaransky.service.UserService;
import com.gmail.vladbaransky.service.model.ItemDTO;
import com.gmail.vladbaransky.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private List<ItemDTO> allItemDTOList;
    private List<ItemDTO> allCompletedItemDTOList;
    private String nameUserDTO;

    public UserServiceImpl(UserRepository userRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public UserDTO loadUserByUsername(String username) {
        List<UserDTO> userDTOList = findAllUsers();
        System.out.println(userDTOList);
        for (UserDTO userDTO : userDTOList) {
            if (username.equals(userDTO.getUsername())) {
                nameUserDTO = userDTO.getUsername();
                return userDTO;
            }
        }
        return null;
    }

    @Override
    public List<ItemDTO> findItemsByRole() {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Item> allCompletedItems = itemRepository.findCompletedItems(connection);
                allCompletedItemDTOList = allCompletedItems.stream()
                        .map(this::getItemDTOFromObject)
                        .collect(Collectors.toList());
                List<Item> allItems = itemRepository.findItems(connection);
                allItemDTOList = allItems.stream()
                        .map(this::getItemDTOFromObject)
                        .collect(Collectors.toList());
                connection.commit();
                return getItemList(nameUserDTO);
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    private List<ItemDTO> getItemList(String nameUserDTO) {
        if (nameUserDTO.equals("admin")) {
            System.out.println("allItemDTOList" + allItemDTOList);
            return allItemDTOList;
        } else if (nameUserDTO.equals("user")) {
            System.out.println("allCompletedItemDTOList" + allCompletedItemDTOList);
            return allCompletedItemDTOList;
        } else return Collections.emptyList();
    }

    @Override
    public List<UserDTO> findAllUsers() {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> users = userRepository.findAllUsers(connection);
                List<UserDTO> userDTOList = users.stream()
                        .map(this::getUserDTOFromObject)
                        .collect(Collectors.toList());
                connection.commit();
                return userDTOList;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    private UserDTO getUserDTOFromObject(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setPassword(user.getPassword());

        userDTO.setRole(user.getRole());
        return userDTO;
    }

    private ItemDTO getItemDTOFromObject(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setStatus(item.getStatus());

        return itemDTO;
    }
}
