package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.RoleDao;
import com.becomejavasenior.model.Role;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl extends CommonDao implements RoleDao {
    private static final String CREATE_ROLE = "INSERT INTO role (role_name) VALUES (?)";
    private static final String READ_ROLE = "SELECT id, role_name FROM role WHERE id=?";
    private static final String UPDATE_ROLE = "UPDATE role SET role_name=? WHERE id=?";
    private static final String DELETE_ROLE = "DELETE FROM role WHERE id=?";
    private static final String FIND_ALL_ROLES = "SELECT id, role_name FROM role";

    @Override
    public void create(Role role) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ROLE)) {
            preparedStatement.setString(1, role.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Role getRoleById(int id) throws DatabaseException {
        Role role = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_ROLE);) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery();){
                if(resultSet.next()) {
                    role = new Role();
                    role.setId(resultSet.getInt("id"));
                    role.setName(resultSet.getString("role_name"));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        if (role == null){
            throw new DatabaseException("no result for id=" + id);
        }
        return role;
    }

    @Override
    public void update(Role role) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROLE);) {
            preparedStatement.setString(1, role.getName());
            preparedStatement.setInt(2, role.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void delete(Role role) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROLE);) {
            preparedStatement.setInt(1, role.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Role> findAll() throws DatabaseException {
        List<Role> roles = new ArrayList<Role>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ROLES);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while(resultSet.next()){
                Role role = new Role();
                role.setId(resultSet.getInt("id"));
                role.setName(resultSet.getString("role_name"));
                roles.add(role);
            }
        }
        catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        }
        return roles;
    }

    public RoleDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
