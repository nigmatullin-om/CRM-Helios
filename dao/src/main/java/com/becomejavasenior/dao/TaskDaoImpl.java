package com.becomejavasenior.dao;

import com.becomejavasenior.Task;

import javax.sql.DataSource;
import java.util.List;

public class TaskDaoImpl extends AbstractDao implements TaskDao {
    public int create(Task task) {
        return 0;
    }

    public Task read(int id) {
        return null;
    }

    public boolean update(Task task) {
        return false;
    }

    public boolean delete(Task task) {
        return false;
    }

    public List<Task> findAll() {
        return null;
    }

    public TaskDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
