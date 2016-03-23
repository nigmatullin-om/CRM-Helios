package com.becomejavasenior.dao;

import com.becomejavasenior.dao.impl.DatabaseException;
import com.becomejavasenior.model.Task;

import java.util.List;

public interface TaskDao {
    public void create(Task task) throws DatabaseException;
    public Task read(int id) throws DatabaseException;
    public void update(Task task) throws DatabaseException;
    public void delete(Task task) throws DatabaseException;
    public List<Task> findAll() throws DatabaseException;
}
