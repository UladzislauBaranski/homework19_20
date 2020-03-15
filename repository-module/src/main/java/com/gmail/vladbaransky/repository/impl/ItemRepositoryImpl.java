package com.gmail.vladbaransky.repository.impl;

import com.gmail.vladbaransky.repository.ItemRepository;
import com.gmail.vladbaransky.repository.model.Item;
import com.gmail.vladbaransky.repository.model.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Item> implements ItemRepository {
    private static final String STATUS_COMPLETED= "completed";

    @Override
    public List<Item> findItems(Connection connection) throws SQLException {
            try (
                    PreparedStatement statement = connection.prepareStatement(
                            "SELECT id, name, status from item"
                    )
            ) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Item> items = new ArrayList<>();
                    while (resultSet.next()) {
                        Item item = getItem(resultSet);
                        items.add(item);
                    }
                    return items;
                }
            }
    }

    @Override
    public List<Item> findCompletedItems(Connection connection) throws SQLException{
        try (

                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, name, status from item where status=? "
                )
        ) {
            statement.setString(1, STATUS_COMPLETED);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Item> items = new ArrayList<>();
                while (resultSet.next()) {
                    Item item = getItem(resultSet);
                    items.add(item);
                }
                return items;
            }
        }
    }


    private Item getItem(ResultSet resultSet) throws SQLException {
        Item item = new Item();
        item.setId(resultSet.getInt("id"));
        item.setName(resultSet.getString("name"));
        item.setStatus(resultSet.getString("status"));
        return item;
    }
}
