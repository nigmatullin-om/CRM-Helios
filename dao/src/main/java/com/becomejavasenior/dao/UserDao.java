package com.becomejavasenior.dao;

import com.becomejavasenior.User;

import java.util.List;

public interface UserDao {
    public int create(User user) throws DatabaseException;
    public User read(int id) throws DatabaseException;
    public boolean update(User user) throws DatabaseException;
    public boolean delete(User user) throws DatabaseException;
    public List<User> findAll() throws DatabaseException;
}
