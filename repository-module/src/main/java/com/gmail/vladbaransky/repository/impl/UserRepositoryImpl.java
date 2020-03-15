package com.gmail.vladbaransky.repository.impl;

import com.gmail.vladbaransky.repository.UserRepository;
import com.gmail.vladbaransky.repository.model.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<User> implements UserRepository {

    @Override
    public List<User> findAllUsers(Connection connection) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, username, password, role from user"
                )
        ) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<User> documents = new ArrayList<>();
                while (resultSet.next()) {
                    User document = getDocument(resultSet);
                    documents.add(document);
                }
                return documents;
            }
        }
    }

    private User getDocument(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(resultSet.getString("role"));
        return user;
    }
}
