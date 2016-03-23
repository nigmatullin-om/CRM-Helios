package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
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
    private final String CREATE_ROLE = "INSERT INTO crm_helios.role (role_name) VALUES (?)";
    private final String READ_ROLE = "SELECT * FROM crm_helios.role WHERE id=?";
    private final String UPDATE_ROLE = "UPDATE crm_helios.role SET role_name=? WHERE id=?";
    private final String DELETE_ROLE = "DELETE FROM crm_helios.role WHERE id=?";
    private final String FIND_ALL_ROLES = "SELECT * FROM crm_helios.role";

    public int create(Role role) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ROLE)) {
            preparedStatement.setString(1, role.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return 1;
    }

    public Role read(int id) throws DatabaseException {
        Role role = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_ROLE);) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery();){
                if(resultSet.next()) {
                    role.setId(resultSet.getInt(1));
                    role.setName(resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return role;
    }

    public boolean update(Role role) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROLE);) {
            preparedStatement.setString(1, role.getName());
            preparedStatement.setInt(2, role.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return true;
    }

    public boolean delete(Role role) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROLE);) {
            preparedStatement.setInt(1, role.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return true;
    }

    public List<Role> findAll() throws DatabaseException {
        List<Role> roles = new ArrayList<Role>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ROLES);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while(resultSet.next()){
                Role role = new Role();
                role.setId(resultSet.getInt(1));
                role.setName(resultSet.getString(2));
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
