package com.becomejavasenior.dao;

import com.becomejavasenior.dao.impl.DatabaseException;
import com.becomejavasenior.model.User;

import java.util.List;

public interface UserDao {
    void create(User user) throws DatabaseException;
    User read(int id) throws DatabaseException;
    void update(User user) throws DatabaseException;
    void delete(User user) throws DatabaseException;
    List<User> findAll() throws DatabaseException;
}
