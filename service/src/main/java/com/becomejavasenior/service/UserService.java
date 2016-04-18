package com.becomejavasenior.service;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Task;
import com.becomejavasenior.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    void create(User user) throws DatabaseException;

    User getUserById(int id) throws DatabaseException;

    void update(User user) throws DatabaseException;

    void delete(User user) throws DatabaseException;

    List<User> findAll() throws DatabaseException;

}