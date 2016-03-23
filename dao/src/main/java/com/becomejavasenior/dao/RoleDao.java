package com.becomejavasenior.dao;

import com.becomejavasenior.model.Role;

import java.util.List;

public interface RoleDao {
    void create(Role role) throws DatabaseException;
    Role getRoleById(int id) throws DatabaseException;
    void update(Role role) throws DatabaseException;
    void delete(Role role) throws DatabaseException;
    List<Role> findAll() throws DatabaseException;
}
