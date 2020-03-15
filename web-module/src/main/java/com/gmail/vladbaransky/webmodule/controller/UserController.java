package com.gmail.vladbaransky.webmodule.controller;

import com.gmail.vladbaransky.service.UserService;
import com.gmail.vladbaransky.service.model.ItemDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/item")
    public String getItems(Model model){
    List<ItemDTO> items=userService.findItems();
        System.out.println("controller"+items);
        model.addAttribute("items", items);
        return "item";
    }

}
