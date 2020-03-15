package com.gmail.vladbaransky.webmodule.controller;

import com.gmail.vladbaransky.service.ItemService;
import com.gmail.vladbaransky.service.model.ItemDTO;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ItemAPIController {

    private final ItemService itemService;

    public ItemAPIController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public List<ItemDTO> getItems() {
        return itemService.findAllItems();
    }

    @PostMapping("/add")
    public String saveItems(@Valid @RequestBody ItemDTO itemDTO,
                            BindingResult bindingResult) throws SQLException {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            List<String> errors = new ArrayList<>();
            for (ObjectError element : allErrors) {
                errors.add(element.getCode() + "-" + element.getDefaultMessage());
            }
            return errors.toString();
        } else {
            itemService.addItem(itemDTO);
            return "Object added into database:" + itemDTO.toString();
        }
    }

    @PostMapping("/delete")
    public String deleteItem(@RequestBody String status) {
        itemService.deleteItemByStatus(status);
        return "Item deleted";
    }
}
