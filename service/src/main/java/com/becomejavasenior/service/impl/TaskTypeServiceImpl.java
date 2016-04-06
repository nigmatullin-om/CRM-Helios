package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.TaskTypeDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.TaskType;
import com.becomejavasenior.service.TaskTypeService;

import java.util.List;

public class TaskTypeServiceImpl implements TaskTypeService {
    private TaskTypeDao taskTypeDao;

    @Override
    public List<TaskType> findAll() throws DatabaseException {
        return taskTypeDao.findAll();
    }

    @Override
    public TaskType getTaskTypeById(int id) throws DatabaseException {
        return taskTypeDao.getTaskTypeById(id);
    }

    public TaskTypeServiceImpl() {
        taskTypeDao = new DaoFactoryImpl().getTaskTypeDao();
    }
}
