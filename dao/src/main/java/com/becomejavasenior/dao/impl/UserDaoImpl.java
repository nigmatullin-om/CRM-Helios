package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.UserDao;
import com.becomejavasenior.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends CommonDao implements UserDao {
    private static final String CREATE_USER = "INSERT INTO user (name, password, photo_file_id, email, phone_mobile," +
                        "phone_work, lang_id, role_id, note, date_create, deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String READ_USER = "SELECT id, name, password, email, phone_mobile, phone_work, note, date_create, deleted " +
                        "FROM user WHERE id = ?";
    private static final String UPDATE_USER = "UPDATE user SET name=?, password=?, photo_file_id=?, email=?, phone_mobile=?," +
            "phone_work=?, lang_id=?, role_id=?, note=?, date_create=?, deleted=? WHERE id=?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id=?";
    private static final String FIND_ALL_USERS = "SELECT id, name, password, email, phone_mobile, phone_work, note, date_create, deleted " +
                            "FROM user";

    @Override
    public void create(User user) throws DatabaseException {
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getPhotoFile().getId());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getMobilePhone());
            preparedStatement.setString(6, user.getWorkPhone());
            preparedStatement.setInt(7, user.getLanguage().getId());
            preparedStatement.setInt(8, user.getRole().getId());
            preparedStatement.setString(9, user.getNote());
            preparedStatement.setTimestamp(10, new Timestamp(user.getCreationDate().getTime()));
            preparedStatement.setBoolean(11, user.getDeleted());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public User getUserById(int id) throws DatabaseException {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_USER);) {
                preparedStatement.setInt(1, id);
                try(ResultSet resultSet = preparedStatement.executeQuery();) {
                    if (resultSet.next()){
                        user = new User();
                        user.setId(resultSet.getInt("id"));
                        user.setName(resultSet.getString("name"));
                        user.setPassword(resultSet.getString("password"));
                        user.setEmail(resultSet.getString("email"));
                        user.setMobilePhone(resultSet.getString("phone_mobile"));
                        user.setWorkPhone(resultSet.getString("phone_work"));
                        user.setNote(resultSet.getString("note"));
                        user.setCreationDate(resultSet.getDate("date_create"));
                        user.setDeleted(resultSet.getBoolean("deleted"));
                    }
                }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        if (user == null){
            throw new DatabaseException("no result for id=" + id);
        }
        return user;
    }

    @Override
    public void update(User user) throws DatabaseException {
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);) {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setInt(3, user.getPhotoFile().getId());
                preparedStatement.setString(4, user.getEmail());
                preparedStatement.setString(5, user.getMobilePhone());
                preparedStatement.setString(6, user.getWorkPhone());
                preparedStatement.setInt(7, user.getLanguage().getId());
                preparedStatement.setInt(8, user.getRole().getId());
                preparedStatement.setString(9, user.getNote());
                preparedStatement.setDate(10, new java.sql.Date(user.getCreationDate().getTime()));
            preparedStatement.setBoolean(11, user.getDeleted());
                preparedStatement.setInt(12, user.getId());
                preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void delete(User user) throws DatabaseException {
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);) {
                preparedStatement.setInt(1, user.getId());
                preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<User> findAll() throws DatabaseException {
        List<User> users = new ArrayList<User>();
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEmail(resultSet.getString("email"));
                    user.setMobilePhone(resultSet.getString("phone_mobile"));
                    user.setWorkPhone(resultSet.getString("phone_work"));
                    user.setNote(resultSet.getString("note"));
                    user.setCreationDate(resultSet.getDate("date_create"));
                    user.setDeleted(resultSet.getBoolean("deleted"));
                    users.add(user);
                }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return users;
    }

    public UserDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
