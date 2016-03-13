package com.becomejavasenior.dao;

import com.becomejavasenior.Role;

import javax.sql.DataSource;
import java.awt.image.RescaleOp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl extends AbstractDao implements RoleDao {
    private final String CREATE_ROLE = "INSERT INTO crm_helios.role (role_name) VALUES (?)";
    private final String READ_ROLE = "SELECT * FROM crm_helios.role WHERE id=?";
    private final String UPDATE_ROLE = "UPDATE crm_helios.role SET role_name=? WHERE id=?";
    private final String DELETE_ROLE = "DELETE FROM crm_helios.role WHERE id=?";
    private final String FIND_ALL_ROLES = "SELECT * FROM crm_helios.role";

    public int create(Role role) throws DaoException {
        Connection connection = null;
        connection = getConnection();
        if (connection != null){
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(CREATE_ROLE);
                preparedStatement.setString(1, role.getName());
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

    public Role read(int id) throws DaoException {
        Connection connection = null;
        connection = getConnection();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(READ_ROLE);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    Role role = new Role();
                    role.setId(resultSet.getInt(1));
                    role.setName(resultSet.getString(2));
                    connection.close();
                    return role;
                }
                else {
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

    public boolean update(Role role) throws DaoException {
        Connection connection = null;
        connection = getConnection();
        if(connection != null){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROLE);
                preparedStatement.setString(1, role.getName());
                preparedStatement.setInt(2, role.getId());
                preparedStatement.execute();
                connection.close();
                return true;
            } catch (SQLException e) {
                throw new DaoException(e.getMessage());
            }
        }
        else {
            throw new DaoException("Can not get connection");
        }
    }

    public boolean delete(Role role) throws DaoException {
        Connection connection = null;
        connection = getConnection();
        if(connection != null){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROLE);
                preparedStatement.setInt(1, role.getId());
                preparedStatement.execute();
                connection.close();
                return true;
            } catch (SQLException e) {
                throw new DaoException(e.getMessage());
            }
        }
        else {
            throw new DaoException("Can not get connection");
        }

    }

    public List<Role> findAll() throws DaoException {
        Connection connection = null;
        connection = getConnection();
        if(connection != null){
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(FIND_ALL_ROLES);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Role> roles = new ArrayList<Role>();
                while(resultSet.next()){
                    Role role = new Role();
                    role.setId(resultSet.getInt(1));
                    role.setName(resultSet.getString(2));
                    roles.add(role);
                }
                connection.close();
                return roles;
            }
            catch (SQLException e){
                throw new DaoException(e.getMessage());
            }
        }
        else {
            throw new DaoException("Can not get connection");
        }
    }

    public RoleDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
