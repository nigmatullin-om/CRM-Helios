package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.TaskTypeDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.TaskType;
import com.becomejavasenior.service.TaskTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskTypeServiceImpl implements TaskTypeService {

    @Resource
    private TaskTypeDao taskTypeDao;

    public TaskTypeDao getTaskTypeDao() {
        return taskTypeDao;
    }

    public void setTaskTypeDao(TaskTypeDao taskTypeDao) {
        this.taskTypeDao = taskTypeDao;
    }

    public TaskTypeServiceImpl() {
        this.taskTypeDao = new DaoFactoryImpl().getTaskTypeDao();
    }
    @Override
    public int create(TaskType taskType) throws DatabaseException {
        return taskTypeDao.create(taskType);
    }

    @Override
    public TaskType getTaskTypeById(int id) throws DatabaseException {
        return taskTypeDao.getTaskTypeById(id);
    }

    @Override
    public List<TaskType> findAll() throws DatabaseException {
        return taskTypeDao.findAll();
    }
}
