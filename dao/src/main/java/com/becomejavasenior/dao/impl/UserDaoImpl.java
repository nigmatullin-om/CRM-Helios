package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.UserDao;
import com.becomejavasenior.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends CommonDao implements UserDao {
    private final String CREATE_USER = "INSERT INTO user (name, password, photo_file_id, email, phone_mobile," +
                        "phone_work, lang_id, role_id, note, date_create, deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String READ_USER = "SELECT * FROM crm_helios.user WHERE id = ?";
    private final String UPDATE_USER = "UPDATE user SET name=?, password=?, photo_file_id=?, email=?, phone_mobile=?," +
            "phone_work=?, lang_id=?, role_id=?, note=?, date_create=?, deleted=? WHERE id=?";
    private final String DELETE_USER = "DELETE FROM user WHERE id=?";
    private final String FIND_ALL_USERS = "SELECT * FROM crm_helios.user";


    public int create(User user) throws DatabaseException {
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
        return 1;
    }

    public User read(int id) throws DatabaseException {
        User user = new User();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_USER);) {
                preparedStatement.setInt(1, id);
                try(ResultSet resultSet = preparedStatement.executeQuery();) {
                    if (resultSet.next()){
                        user.setId(resultSet.getInt(1));
                        user.setName(resultSet.getString(2));
                        user.setPassword(resultSet.getString(3));
                        user.setEmail(resultSet.getString(5));
                        user.setMobilePhone(resultSet.getString(6));
                        user.setWorkPhone(resultSet.getString(7));
                        user.setNote(resultSet.getString(10));
                        user.setCreationDate(resultSet.getDate(11));
                        //user.setDeleted(resultSet.getBoolean(12));
                        connection.close();
                    }
                }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return user;
    }

    public boolean update(User user) throws DatabaseException {
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
        return true;
    }

    public boolean delete(User user) throws DatabaseException {
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);) {
                preparedStatement.setInt(1, user.getId());
                preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return true;
    }

    public List<User> findAll() throws DatabaseException {
        List<User> users = new ArrayList<User>();
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt(1));
                    user.setName(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));
                    user.setEmail(resultSet.getString(5));
                    user.setMobilePhone(resultSet.getString(6));
                    user.setWorkPhone(resultSet.getString(7));
                    user.setNote(resultSet.getString(10));
                    user.setCreationDate(resultSet.getDate(11));
                    user.setDeleted(resultSet.getBoolean(12));
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
