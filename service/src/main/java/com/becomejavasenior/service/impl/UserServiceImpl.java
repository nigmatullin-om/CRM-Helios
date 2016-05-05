package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.UserDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.User;
import com.becomejavasenior.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public int create(User user) throws DatabaseException {
        return userDao.create(user);
    }

    @Override
    public User getUserById(int id) throws DatabaseException {
        return userDao.getUserById(id);
    }

    @Override
    public User getUserByName(String name) throws DatabaseException {
        return userDao.getUserByName(name);
    }

    @Override
    public int update(User user) throws DatabaseException {
        return userDao.update(user);
    }

    @Override
    public boolean isEmailExist(String email) throws DatabaseException {
        return userDao.isEmailExist(email);
    }

    @Override
    public int delete(User user) throws DatabaseException {
        return userDao.delete(user);
    }

    @Override
    public List<User> findAll() throws DatabaseException {
        return userDao.findAll();
    }

}

