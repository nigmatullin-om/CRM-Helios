package com.becomejavasenior.dao;

import com.becomejavasenior.User;

import javax.sql.DataSource;
import java.util.List;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public int create(User user) {
        return 0;
    }

    public User read(int id) {
        return null;
    }

    public boolean update(User user) {
        return false;
    }

    public boolean delete(User user) {
        return false;
    }

    public List<User> findAll() {
        return null;
    }

    public UserDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
