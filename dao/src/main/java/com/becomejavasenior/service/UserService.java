package com.becomejavasenior.service;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    int create(User user) throws DatabaseException;

    User getUserById(int id) throws DatabaseException;

    int update(User user) throws DatabaseException;

    int delete(User user) throws DatabaseException;

    List<User> findAll() throws DatabaseException;
}
