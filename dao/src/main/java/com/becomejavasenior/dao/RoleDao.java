package com.becomejavasenior.dao;

import com.becomejavasenior.Role;

import java.util.List;

public interface RoleDao {
    public int create(Role role) throws DaoException;
    public Role read(int id) throws DaoException;
    public boolean update(Role role) throws DaoException;
    public boolean delete(Role role) throws DaoException;
    public List<Role> findAll() throws DaoException;
}
