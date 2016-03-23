package com.becomejavasenior.dao;

import com.becomejavasenior.dao.impl.DatabaseException;
import com.becomejavasenior.model.Task;

import java.util.List;

public interface TaskDao {
    void create(Task task) throws DatabaseException;
    Task read(int id) throws DatabaseException;
    void update(Task task) throws DatabaseException;
    void delete(Task task) throws DatabaseException;
    List<Task> findAll() throws DatabaseException;
}
