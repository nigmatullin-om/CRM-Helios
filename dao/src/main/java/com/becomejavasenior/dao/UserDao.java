package com.becomejavasenior.dao;

import com.becomejavasenior.User;

import java.util.List;

public interface UserDao {
    public int create(User user) throws DaoException;
    public User read(int id) throws DaoException;
    public boolean update(User user) throws DaoException;
    public boolean delete(User user) throws DaoException;
    public List<User> findAll() throws DaoException;
}
