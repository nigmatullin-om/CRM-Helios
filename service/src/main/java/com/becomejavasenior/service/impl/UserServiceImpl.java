package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.UserDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.User;
import com.becomejavasenior.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static Logger log = LogManager.getLogger(UserServiceImpl.class);
    private UserDao userDao;

    @Override
    public int create(User user) throws DatabaseException {
        return 0;
    }

    @Override
    public User getUserById(int id) throws DatabaseException {
        return null;
    }

    @Override
    public int update(User user) throws DatabaseException {
        return 0;
    }

    @Override
    public int delete(User user) throws DatabaseException {
        return 0;
    }

    @Override
    public List<User> findAll() throws DatabaseException {
        return userDao.findAll();
    }

    public UserServiceImpl() {
        this.userDao = new DaoFactoryImpl().getUserDao();
    }
}
