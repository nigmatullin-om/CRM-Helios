package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.TaskDao;
import com.becomejavasenior.dao.UserDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Task;
import com.becomejavasenior.model.User;
import com.becomejavasenior.service.TaskService;
import com.becomejavasenior.service.UserService;

import java.util.*;


public class UserServiceImpl implements UserService {

    private final UserDao userDao = new DaoFactoryImpl().getUserDao();

    @Override
    public void create(User user) throws DatabaseException {
        userDao.create(user);

    }

    @Override
    public User getUserById(int id) throws DatabaseException {
        return userDao.getUserById(id);
    }

    @Override
    public void update(User user) throws DatabaseException {
        userDao.update(user);

    }

    @Override
    public void delete(User user) throws DatabaseException {
        userDao.delete(user);

    }

    @Override
    public List<User> findAll() throws DatabaseException {
        return userDao.findAll();
    }
}