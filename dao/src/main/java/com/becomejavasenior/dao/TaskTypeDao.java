package com.becomejavasenior.dao;

import com.becomejavasenior.model.TaskType;

public interface TaskTypeDao {
    int create(TaskType taskType) throws DatabaseException;
    TaskType getTaskTypeById(int id) throws DatabaseException;
}
