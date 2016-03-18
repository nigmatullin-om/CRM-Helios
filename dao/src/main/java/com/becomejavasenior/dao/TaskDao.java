package com.becomejavasenior.dao;

import com.becomejavasenior.Task;

import java.util.List;

public interface TaskDao {
    public int create(Task task) throws DatabaseException;
    public Task read(int id) throws DatabaseException;
    public boolean update(Task task) throws DatabaseException;
    public boolean delete(Task task) throws DatabaseException;
    public List<Task> findAll() throws DatabaseException;
}
