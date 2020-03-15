package com.gmail.vladbaransky.service.impl;

import com.gmail.vladbaransky.repository.ItemRepository;
import com.gmail.vladbaransky.repository.model.Item;
import com.gmail.vladbaransky.service.ItemService;
import com.gmail.vladbaransky.service.model.ItemDTO;
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
public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<ItemDTO> findAllItems() {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Item> allItems = itemRepository.findItems(connection);
                List<ItemDTO> allItemDTOList = allItems.stream()
                        .map(this::getItemDTOFromObject)
                        .collect(Collectors.toList());
                connection.commit();
                return allItemDTOList;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public void addItem(ItemDTO itemDTO) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = getObjectFromItemDTO(itemDTO);
                itemRepository.add(connection, item);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteItemByStatus(String status) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                itemRepository.delete(connection, status);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private ItemDTO getItemDTOFromObject(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setStatus(item.getStatus());
        return itemDTO;
    }

    private Item getObjectFromItemDTO(ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setStatus(itemDTO.getStatus());
        return item;
    }
}
