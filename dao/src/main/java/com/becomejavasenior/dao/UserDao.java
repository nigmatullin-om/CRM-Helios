package com.becomejavasenior.dao;

import com.becomejavasenior.User;

import java.util.List;

public interface UserDao {
    public int create(User user);
    public User read(int id);
    public boolean update(User user);
    public boolean delete(User user);
    public List<User> findAll();
}
