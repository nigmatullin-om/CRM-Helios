package com.becomejavasenior.dao;

import com.becomejavasenior.User;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractDao implements UserDao {
    private final String CREATE_USER = "INSERT INTO crm_helios.user (name, password, photo_file_id, email, phone_mobile," +
                        "phone_work, lang_id, role_id, note, date_create) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String READ_USER = "SELECT * FROM crm_helios.user WHERE id = ?";
    private final String UPDATE_USER = "UPDATE crm_helios.user SET name=?, password=?, photo_file_id=?, email=?, phone_mobile=?," +
            "phone_work=?, lang_id=?, role_id=?, note=?, date_create=? WHERE id=?";
    private final static String DELETE_USER = "DELETE FROM crm_helios.user WHERE id=?";
    private static String FIND_ALL_USERS = "SELECT * FROM crm_helios.user";

    public int create(User user) throws DaoException {
        Connection connection = null;
        connection = getConnection();
        if (connection != null){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER);
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
                preparedStatement.execute();
                connection.close();
                return 1;
            } catch (SQLException e) {
                throw new DaoException(e.getMessage());
            }
        }
        else {
            throw new DaoException("Can not get connection");
        }
    }

    public User read(int id) throws DaoException {
        Connection connection = null;
        connection = getConnection();
        if(connection != null){
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(READ_USER);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    User user = new User();
                    user.setId(resultSet.getInt(1));
                    user.setName(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));
                    /*int photoId = resultSet.getInt(4);*/
                    user.setEmail(resultSet.getString(5));
                    user.setMobilePhone(resultSet.getString(6));
                    user.setWorkPhone(resultSet.getString(7));
                    /*int langId = resultSet.getInt(8);
                    int roleId = resultSet.getInt(9);*/
                    user.setNote(resultSet.getString(10));
                    user.setCreationDate(resultSet.getDate(11));
                    /*DaoFactory daoFactory = new DaoFactoryImpl();
                    user.setPhotoFile( daoFactory.getFileDao().read(photoId));
                    user.setLanguage(daoFactory.getLanguageDao().read(langId));
                    user.setRole(daoFactory.getRoleDao().read(roleId));*/
                    connection.close();
                    return user;
                }
                else {
                    connection.close();
                    return null;
                }
            } catch (SQLException e) {
                throw new DaoException(e.getMessage());
            }
        }
        else {
            throw new DaoException("Can not get connection");
        }

    }

    public boolean update(User user) throws DaoException {
        Connection connection = null;
        connection = getConnection();
        if(connection != null){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);
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
                preparedStatement.setInt(11, user.getId());
                preparedStatement.execute();
                connection.close();
                return true;
            } catch (SQLException e) {
                throw  new DaoException(e.getMessage());
            }
        }
        else {
            throw new DaoException("Can not get connection");
        }
    }

    public boolean delete(User user) throws DaoException {
        Connection connection = null;
        connection = getConnection();
        if(connection != null){
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(DELETE_USER);
                preparedStatement.setInt(1, user.getId());
                preparedStatement.execute();
                connection.close();
                return true;
            } catch (SQLException e) {
                throw  new DaoException(e.getMessage());
            }
        }
        else {
            throw new DaoException("Can not get connection");
        }
    }

    public List<User> findAll() throws DaoException {
        Connection connection = null;
        connection = getConnection();
        if(connection != null){
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(FIND_ALL_USERS);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<User> users = new ArrayList<User>();
                /*DaoFactory daoFactory = new DaoFactoryImpl();
                FileDao fileDao = daoFactory.getFileDao();
                LanguageDao languageDao = daoFactory.getLanguageDao();
                RoleDao roleDao = daoFactory.getRoleDao();*/
                while (resultSet.next()){
                    User user = new User();

                    user.setId(resultSet.getInt(1));
                    user.setName(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));
                    /*int photoId = resultSet.getInt(4);*/
                    user.setEmail(resultSet.getString(5));
                    user.setMobilePhone(resultSet.getString(6));
                    user.setWorkPhone(resultSet.getString(7));
                    /*int langId = resultSet.getInt(8);
                    int roleId = resultSet.getInt(9);*/
                    user.setNote(resultSet.getString(10));
                    user.setCreationDate(resultSet.getDate(11));

                    /*user.setPhotoFile( fileDao.read(photoId));
                    user.setLanguage(languageDao.read(langId));
                    user.setRole(roleDao.read(roleId));*/
                    users.add(user);
                }
                connection.close();
                return users;
            } catch (SQLException e) {
                throw  new DaoException(e.getMessage());
            }
        }
        else {
            throw new DaoException("Can not get connection");
        }
    }

    public UserDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
