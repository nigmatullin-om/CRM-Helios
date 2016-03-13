package com.becomejavasenior.dao;

import com.becomejavasenior.Task;

import java.util.List;

public interface TaskDao {
    public int create(Task task) throws DaoException;
    public Task read(int id) throws DaoException;
    public boolean update(Task task) throws DaoException;
    public boolean delete(Task task) throws DaoException;
    public List<Task> findAll() throws DaoException;
}
