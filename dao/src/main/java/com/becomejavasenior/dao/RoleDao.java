package com.becomejavasenior.dao;

import com.becomejavasenior.dao.impl.DatabaseException;
import com.becomejavasenior.model.Role;

import java.util.List;

public interface RoleDao {
    public int create(Role role) throws DatabaseException;
    public Role read(int id) throws DatabaseException;
    public boolean update(Role role) throws DatabaseException;
    public boolean delete(Role role) throws DatabaseException;
    public List<Role> findAll() throws DatabaseException;
}
