package com.gmail.vladbaransky.repository;

import com.gmail.vladbaransky.repository.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserRepository extends GenericRepository<User> {
    List<User> findAllUsers(Connection connection) throws SQLException;

}
