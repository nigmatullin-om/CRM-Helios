package com.becomejavasenior.dao;

import com.becomejavasenior.Task;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDaoImpl extends CommonDao implements TaskDao {
    private final String READ_TASK = "SELECT * FROM task WHERE id=?";
    private final String CREATE_TASK = "INSERT INTO task (name, finish_date, responsible_id, description, " +
                                    "contact_id, deal_id, company_id, created_by, date_create) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_TASK = "UPDATE task SET name=?, finish_date=?, responsible_id=?, description=?, " +
                                    "contact_id=?, deal_id=?, company_id=?, created_by=?, date_create=? WHERE id=?";
    private final String DELETE_TASK = "DELETE FROM task WHERE id=?";
    private final String FIND_ALL_TASKS = "SELECT * FROM task";

    public int create(Task task) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(CREATE_TASK);
            preparedStatement.setString(1, task.getName());
            preparedStatement.setDate(2, new java.sql.Date(task.getFinishDate().getTime()));
            preparedStatement.setInt(3, task.getResponsibleUser().getId());
            preparedStatement.setString(4, task.getDescription());
            preparedStatement.setInt(5, task.getContact().getId());
            preparedStatement.setInt(6, task.getDeal().getId());
            preparedStatement.setInt(7, task.getCompany().getId());
            preparedStatement.setInt(8, task.getCreatedByUser().getId());
            preparedStatement.setDate(9, new java.sql.Date(task.getCreationDate().getTime()));
            preparedStatement.execute();
        } catch (SQLException e) {
            exceptions.add(e);
        }
        finally {
            try {
                preparedStatement.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                connection.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            if(exceptions.size() != 0) {
                throw new DatabaseException(exceptions);
            }
        }
        return 1;
    }

    public Task read(int id) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Task task = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(READ_TASK);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                task.setId(resultSet.getInt(1));
                task.setName(resultSet.getString(2));
                task.setFinishDate(resultSet.getDate(3));
                task.setDescription(resultSet.getString(5));
                task.setCreationDate(resultSet.getDate(10));
            }
        } catch (SQLException e) {
            exceptions.add(e);
        }
        finally {
            try {
                resultSet.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                preparedStatement.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                connection.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            if(exceptions.size() != 0) {
                throw new DatabaseException(exceptions);
            }
        }
        return task;
    }

    public boolean update(Task task) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_TASK);
            preparedStatement.setString(1, task.getName());
            preparedStatement.setDate(2, new java.sql.Date(task.getFinishDate().getTime()));
            preparedStatement.setInt(3, task.getResponsibleUser().getId());
            preparedStatement.setString(4, task.getDescription());
            preparedStatement.setInt(5, task.getContact().getId());
            preparedStatement.setInt(6, task.getDeal().getId());
            preparedStatement.setInt(7, task.getCompany().getId());
            preparedStatement.setInt(8, task.getCreatedByUser().getId());
            preparedStatement.setDate(9, new java.sql.Date(task.getCreationDate().getTime()));
            preparedStatement.setInt(10, task.getId());
            preparedStatement.execute();
            } catch (SQLException e) {
                exceptions.add(e);
            }
        finally {
            try {
                preparedStatement.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                connection.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            if(exceptions.size() != 0) {
                throw new DatabaseException(exceptions);
            }
        }
        return true;
    }

    public boolean delete(Task task) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_TASK);
            preparedStatement.setInt(1, task.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            exceptions.add(e);
        }
        finally {
            try {
                preparedStatement.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                connection.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            if(exceptions.size() != 0) {
                throw new DatabaseException(exceptions);
            }
        }
        return true;
    }

    public List<Task> findAll() throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Task> tasks = new ArrayList<Task>();
        try {
            preparedStatement = connection.prepareStatement(FIND_ALL_TASKS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt(1));
                task.setName(resultSet.getString(2));
                task.setFinishDate(resultSet.getDate(3));
                task.setDescription(resultSet.getString(5));
                task.setCreationDate(resultSet.getDate(10));
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            try {
                resultSet.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                preparedStatement.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                connection.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            if(exceptions.size() != 0) {
                throw new DatabaseException(exceptions);
            }
        }
        return tasks;
    }

    public TaskDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
