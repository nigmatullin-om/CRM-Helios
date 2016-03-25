package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.TaskDao;
import com.becomejavasenior.model.Task;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDaoImpl extends CommonDao implements TaskDao {
    private static final String READ_TASK = "SELECT id, name, finish_date, description, date_create, done, deleted FROM task WHERE id=?";
    private static final String CREATE_TASK = "INSERT INTO task (name, finish_date, responsible_id, description, " +
                                    "contact_id, deal_id, company_id, created_by, date_create, deleted) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_TASK = "UPDATE task SET name=?, finish_date=?, responsible_id=?, description=?, " +
                                    "contact_id=?, deal_id=?, company_id=?, created_by=?, date_create=?, deleted=? WHERE id=?";
    private static final String DELETE_TASK = "DELETE FROM task WHERE id=?";
    private static final String FIND_ALL_TASKS = "SELECT id, name, finish_date, description, date_create, done, deleted FROM task";

    @Override
    public void create(Task task) throws DatabaseException {
        try (Connection connection = getConnection();
                   PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TASK)){
            preparedStatement.setString(1, task.getName());
            preparedStatement.setDate(2, new java.sql.Date(task.getFinishDate().getTime()));
            preparedStatement.setInt(3, task.getResponsibleUser().getId());
            preparedStatement.setString(4, task.getDescription());
            preparedStatement.setInt(5, task.getContact().getId());
            preparedStatement.setInt(6, task.getDeal().getId());
            preparedStatement.setInt(7, task.getCompany().getId());
            preparedStatement.setInt(8, task.getCreatedByUser().getId());
            preparedStatement.setBoolean(9, task.getDeleted());
            preparedStatement.setDate(10, new java.sql.Date(task.getCreationDate().getTime()));
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Task getTaskById(int id) throws DatabaseException {
        Task task = null;
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_TASK);) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    task = new Task();
                    task.setId(resultSet.getInt("id"));
                    task.setName(resultSet.getString("name"));
                    task.setFinishDate(resultSet.getDate("finish_date"));
                    task.setDescription(resultSet.getString("description"));
                    task.setCreationDate(resultSet.getDate("date_create"));
                    task.setDeleted(resultSet.getBoolean("deleted"));
                    task.setDone(resultSet.getBoolean("done"));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        if (task == null) {
            throw new DatabaseException("no results for id=" + id);
        }
        return task;
    }

    @Override
    public void update(Task task) throws DatabaseException {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TASK);) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setDate(2, new java.sql.Date(task.getFinishDate().getTime()));
            preparedStatement.setInt(3, task.getResponsibleUser().getId());
            preparedStatement.setString(4, task.getDescription());
            preparedStatement.setInt(5, task.getContact().getId());
            preparedStatement.setInt(6, task.getDeal().getId());
            preparedStatement.setInt(7, task.getCompany().getId());
            preparedStatement.setInt(8, task.getCreatedByUser().getId());
            preparedStatement.setDate(9, new java.sql.Date(task.getCreationDate().getTime()));
            preparedStatement.setBoolean(10, task.getDeleted());
            preparedStatement.setInt(11, task.getId());
            preparedStatement.execute();
            } catch (SQLException e) {
                throw new DatabaseException(e.getMessage());
            }
    }

    @Override
    public void delete(Task task) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TASK);) {
            preparedStatement.setInt(1, task.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Task> findAll() throws DatabaseException {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_TASKS);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setName(resultSet.getString("name"));
                task.setFinishDate(resultSet.getDate("finish_date"));
                task.setDescription(resultSet.getString("description"));
                task.setCreationDate(resultSet.getDate("date_create"));
                task.setDeleted(resultSet.getBoolean("deleted"));
                task.setDone(resultSet.getBoolean("done"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return tasks;
    }

    public TaskDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
