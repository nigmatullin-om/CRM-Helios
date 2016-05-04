package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.TaskDao;
import com.becomejavasenior.model.Task;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class TaskDaoImpl extends CommonDao implements TaskDao {
    private static String GET_TASK_BY_ID = "SELECT id, name, date_create, " +
            "description, finish_date,  period FROM task WHERE id = ? AND deleted = FALSE";
    private static String INSERT_TASK = "INSERT INTO task(id, responsible_id, contact_id, " +
            "deal_id, company_id, created_by, name, finish_date, description, date_create, period, deleted, task_type_id) VALUES" +
            "(DEFAULT, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?, FALSE, ?)";
    private static String SET_TASK_DELETED = "UPDATE task SET deleted=TRUE WHERE id = ?";
    private static String UPDATE_TASK = "UPDATE task SET responsible_id=?, contact_id=?, deal_id =?," +
            " company_id=?, created_by=?, name=?, finish_date=?, description=?, date_create=?, period=?, task_type_id=? WHERE id =?";

    private static String GET_ALL_TASKS_FOR_CONTACT = "SELECT id, name, company_id, contact_id, created_by, date_create, deal_id, " +
            "description, finish_date, responsible_id, period, task_type_id FROM task WHERE contact_id=? AND deleted=FALSE";
    private static String GET_ALL_TASKS_FOR_DEAL = "SELECT id, name, company_id, contact_id, created_by, date_create, deal_id, " +
            "description, finish_date, responsible_id, period, task_type_id FROM task WHERE deal_id=? AND deleted=FALSE";
    private static String GET_ALL_TASKS_FOR_COMPANY = "SELECT id, name, company_id, contact_id, created_by, date_create, deal_id, " +
            "description, finish_date, responsible_id, period, task_type_id FROM task WHERE company_id=? AND deleted=FALSE";
    private static String GET_ALL_TASKS = "SELECT id, name, company_id, contact_id, created_by, date_create, deal_id, " +
            "description, finish_date, responsible_id, period, task_type_id FROM task WHERE deleted=FALSE";
    private static String GET_ALL_TASKS_BETWEEN_DAYS = "SELECT id, name, company_id, contact_id, created_by, date_create, deal_id, " +
            "description, finish_date, responsible_id, period, task_type_id FROM task " +
            "WHERE deleted=FALSE AND finish_date >= ? AND finish_date <= ?";

    private static String GET_MAX_ID = "SELECT MAX(id) FROM task";

    public TaskDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Task task) throws DatabaseException {
        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_TASK, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, task.getResponsibleUser().getId());

            if (task.getContact() != null) {
                ps.setInt(2, task.getContact().getId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }

            if (task.getDeal() != null) {
                ps.setInt(3, task.getDeal().getId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            if (task.getCompany() != null) {
                ps.setInt(4, task.getCompany().getId());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setInt(5, task.getCreatedByUser().getId());

            ps.setString(6, task.getName());
            ps.setTimestamp(7, new Timestamp(task.getFinishDate().getTime()));
            ps.setString(8, task.getDescription());
            ps.setTimestamp(9, new Timestamp(new java.util.Date().getTime()));

            if (task.getPeriod() != null) {
                ps.setInt(10, task.getPeriod().ordinal() + 1);
            } else {
                ps.setNull(10, Types.INTEGER);
            }

            ps.setInt(11, task.getTaskType().getId());
            return ps;
        };
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int row = getJdbcTemplate().update(psc, keyHolder);

        if (row > 0)
           return (int)keyHolder.getKeys().get("id");
        else {
            throw new DatabaseException("Couldn't create the task entity!");
        }
    }

    @Override
    public Task getTaskById(int id) throws DatabaseException {
        try {
            return getJdbcTemplate().queryForObject(GET_TASK_BY_ID, new Object[]{id}, RowMappers.taskRowMapper());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int update(Task task) throws DatabaseException {
        Object contactId = task.getContact() != null ? task.getContact().getId() : null;
        Object dealId = task.getDeal() != null ? task.getDeal().getId() : null;
        Object companyId = task.getCompany() != null ? task.getCompany().getId() : null;
        try {
            return getJdbcTemplate().update(UPDATE_TASK,
                    task.getResponsibleUser().getId(),
                    contactId,
                    dealId,
                    companyId,
                    task.getCreatedByUser().getId(),
                    task.getName(),
                    task.getFinishDate(),
                    task.getDescription(),
                    new Timestamp(new java.util.Date().getTime()),
                    task.getPeriod().ordinal() + 1,
                    task.getTaskType().getId(),
                    task.getId());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int delete(Task task) throws DatabaseException {
        try {
            return getJdbcTemplate().update(SET_TASK_DELETED, task.getId());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int getMaxId() throws DatabaseException {
        try {
            return getJdbcTemplate().queryForObject(GET_MAX_ID, Integer.class);
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Task> findAll() throws DatabaseException {
        try {
            return getJdbcTemplate().query(GET_ALL_TASKS, RowMappers.taskRowMapper());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Task> getAllTasksForContactById(int contactId) throws DatabaseException {
        try {
            return getJdbcTemplate().query(GET_ALL_TASKS_FOR_CONTACT, new Object[]{contactId}, RowMappers.taskRowMapper());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Task> getAllTasksForCompanyById(int companyId) throws DatabaseException {
        try {
            return getJdbcTemplate().query(GET_ALL_TASKS_FOR_COMPANY, new Object[]{companyId}, RowMappers.taskRowMapper());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Task> getAllTasksForDealById(int dealId) throws DatabaseException {
        try {
            return getJdbcTemplate().query(GET_ALL_TASKS_FOR_DEAL, new Object[]{dealId}, RowMappers.taskRowMapper());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int createWithId(Task task) throws DatabaseException {
        try {
            return create(task);
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Task> getTasksBetweenDays(LocalDate startDay, LocalDate finishDay) throws DatabaseException {
        try {

            Timestamp startDate = Timestamp.valueOf(startDay.atStartOfDay());
            Timestamp finishDate = Timestamp.valueOf(finishDay.plusDays(1).atStartOfDay());
            return getJdbcTemplate().query(GET_ALL_TASKS_BETWEEN_DAYS, new Object[]{startDate, finishDate}, RowMappers.taskRowMapper());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
