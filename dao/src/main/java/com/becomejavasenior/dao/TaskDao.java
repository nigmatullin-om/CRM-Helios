package com.becomejavasenior.dao;

import com.becomejavasenior.Task;

import java.util.List;

public interface TaskDao {
    public int create(Task task);
    public Task read(int id);
    public boolean update(Task task);
    public boolean delete(Task task);
    public List<Task> findAll();
}
