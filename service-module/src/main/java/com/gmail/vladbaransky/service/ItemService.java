package com.gmail.vladbaransky.service;

import com.gmail.vladbaransky.service.model.ItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface ItemService {
    List<ItemDTO> findAllItems();

    void addItem(ItemDTO itemDTO) throws SQLException;

    void deleteItemByStatus(String status);
}
