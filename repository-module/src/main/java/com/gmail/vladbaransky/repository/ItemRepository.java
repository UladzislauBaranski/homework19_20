package com.gmail.vladbaransky.repository;

import com.gmail.vladbaransky.repository.model.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ItemRepository extends GenericRepository<Item>{

    List<Item> findItems(Connection connection) throws SQLException;

    List<Item> findCompletedItems(Connection connection) throws SQLException;

    void add(Connection connection, Item item)throws SQLException;

    void delete(Connection connection, String status);
}
