package com.becomejavasenior.dao;

import com.becomejavasenior.dao.impl.DatabaseException;
import com.becomejavasenior.model.User;

import java.util.List;

public interface UserDao {
    public void create(User user) throws DatabaseException;
    public User read(int id) throws DatabaseException;
    public void update(User user) throws DatabaseException;
    public void delete(User user) throws DatabaseException;
    public List<User> findAll() throws DatabaseException;
}
