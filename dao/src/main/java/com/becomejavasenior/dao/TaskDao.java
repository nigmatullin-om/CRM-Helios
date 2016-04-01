package com.becomejavasenior.dao;

import com.becomejavasenior.model.Task;

import java.util.List;

public interface TaskDao {
    int create(Task task) throws DatabaseException;
    Task getTaskById(int id) throws DatabaseException;
    int update(Task task) throws DatabaseException;
    int delete(Task task) throws DatabaseException;
    List<Task> findAll() throws DatabaseException;
}
