package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.TaskDao;
import com.becomejavasenior.model.Task;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

public class TaskDaoImpl extends CommonDao implements TaskDao {
    public TaskDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Task task) throws DatabaseException {
        throw new DatabaseException("method not supported yet!");
    }

    @Override
    public Task getTaskById(int id) throws DatabaseException {
        throw new DatabaseException("method not supported yet!");
    }

    @Override
    public int update(Task task) throws DatabaseException {
        throw new DatabaseException("method not supported yet!");
    }

    @Override
    public int delete(Task task) throws DatabaseException {
        throw new DatabaseException("method not supported yet!");
    }

    @Override
    public int getMaxId() throws DatabaseException {
        throw new DatabaseException("method not supported yet!");
    }

    @Override
    public List<Task> findAll() throws DatabaseException {
        throw new DatabaseException("method not supported yet!");
    }

    @Override
    public List<Task> getAllTasksForContactById(int contactId) throws DatabaseException {
        throw new DatabaseException("method not supported yet!");
    }

    @Override
    public List<Task> getAllTasksForCompanyById(int companyId) throws DatabaseException {
        throw new DatabaseException("method not supported yet!");
    }

    @Override
    public List<Task> getAllTasksForDealById(int dealId) throws DatabaseException {
        throw new DatabaseException("method not supported yet!");
    }

    @Override
    public int createWithId(Task task) throws DatabaseException {
        throw new DatabaseException("method not supported yet!");
    }

    @Override
    public List<Task> getTasksBetweenDays(LocalDate startDay, LocalDate finishDay) throws DatabaseException {
        throw new DatabaseException("method not supported yet!");
    }
}
