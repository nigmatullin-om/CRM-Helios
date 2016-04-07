package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DaoFactory;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.TaskDao;
import com.becomejavasenior.model.*;
import org.apache.logging.log4j.LogManager;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDaoImpl extends CommonDao implements TaskDao {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(TaskDaoImpl.class);

    private static String GET_TASK_BY_ID = "SELECT id, name, company_id, contact_id, created_by, date_create, deal_id, " +
            "description, finish_date, responsible_id, period, task_type_id FROM task WHERE id = ? AND deleted = FALSE";
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


    private DaoFactory daoFactory;

    public TaskDaoImpl(DataSource dataSource) {
        super(dataSource);
        daoFactory = new DaoFactoryImpl();
    }

    public Task getTaskById(int id) throws DatabaseException {
        Task task;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TASK_BY_ID)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            task = getTaskFromResultSet(resultSet);

        } catch (SQLException e) {
            LOGGER.error("Getting a task was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return task;
    }

    @Override
    public int update(Task task) throws DatabaseException {

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_TASK)) {

            ps.setInt(1, task.getResponsibleUser().getId());

            if (task.getContact() != null) {
                ps.setInt(2, task.getContact().getId());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }

            if (task.getDeal() != null) {
                ps.setInt(3, task.getDeal().getId());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }

            if (task.getCompany() != null) {
                ps.setInt(4, task.getCompany().getId());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }

            ps.setInt(5, task.getCreatedByUser().getId());

            ps.setString(6, task.getName());
            ps.setTimestamp(7, new Timestamp(task.getFinishDate().getTime()));
            ps.setString(8, task.getDescription());
            ps.setTimestamp(9, new Timestamp(new java.util.Date().getTime()));

            if (task.getPeriod() != null) {
                ps.setInt(10, task.getPeriod().ordinal());
            } else {
                ps.setNull(10, java.sql.Types.INTEGER);
            }

            ps.setInt(11, task.getTaskType().getId());
            ps.setInt(12, task.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Updating a task was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int delete(Task task) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SET_TASK_DELETED)) {

            ps.setInt(1, task.getId());
            return ps.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Deleting a task was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int create(Task task) throws DatabaseException {

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_TASK)) {

            ps.setInt(1, task.getResponsibleUser().getId());

            if (task.getContact() != null) {
                ps.setInt(2, task.getContact().getId());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }

            if (task.getDeal() != null) {
                ps.setInt(3, task.getDeal().getId());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }

            if (task.getCompany() != null) {
                ps.setInt(4, task.getCompany().getId());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }

            ps.setInt(5, task.getCreatedByUser().getId());

            ps.setString(6, task.getName());

            ps.setTimestamp(7, new Timestamp(task.getFinishDate().getTime()));

            ps.setString(8, task.getDescription());
            ps.setTimestamp(9, new Timestamp(new java.util.Date().getTime()));

            ps.setInt(10, task.getPeriod().ordinal());
            ps.setInt(11, task.getTaskType().getId());

            return ps.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Creating a task was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Task> getAllTasksForContactById(int contactId) throws DatabaseException {
        return getTasksList(GET_ALL_TASKS_FOR_CONTACT, contactId);
    }

    @Override
    public List<Task> getAllTasksForCompanyById(int companyId) throws DatabaseException {
        return getTasksList(GET_ALL_TASKS_FOR_COMPANY, companyId);
    }

    @Override
    public List<Task> getAllTasksForDealById(int dealId) throws DatabaseException {
        return getTasksList(GET_ALL_TASKS_FOR_DEAL, dealId);
    }

    @Override
    public List<Task> findAll() throws DatabaseException {
        List<Task> tasks = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_TASKS)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Task task = getTaskFromResultSet(resultSet);
                tasks.add(task);
            }

        } catch (SQLException e) {
            LOGGER.error("Getting tasks was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return tasks;
    }

    private List<Task> getTasksList(String sqlQuery, int taskOwnerId) throws DatabaseException {
        List<Task> tasks = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setInt(1, taskOwnerId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Task task = getTaskFromResultSet(resultSet);
                tasks.add(task);
            }

        } catch (SQLException e) {
            LOGGER.error("Creating tasks was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return tasks;
    }
    private Task getTaskFromResultSet(ResultSet resultSet) throws SQLException, DatabaseException {
        Task task = new Task();

        int taskId = resultSet.getInt("id");
        task.setId(taskId);

        String taskName = resultSet.getString("name");
        task.setName(taskName);

/*        int companyId = resultSet.getInt("company_id");
        if(companyId != 0) {
            Company company = daoFactory.getCompanyDao().getCompanyById(companyId);
            task.setCompany(company);
        }

        int contactId = resultSet.getInt("contact_id");
        if(contactId != 0) {
            Contact contact = daoFactory.getContactDao().getContactById(contactId);
            task.setContact(contact);
        }

        int dealId = resultSet.getInt("deal_id");
        if(dealId != 0) {
            Deal deal = daoFactory.getDealDao().getDealById(dealId);
            task.setDeal(deal);
        }*/

        /*int createdById = resultSet.getInt("created_by");
        User created_by = daoFactory.getUserDao().getUserById(createdById);
        task.setCreatedByUser(created_by);*/

        Timestamp dateCreate = resultSet.getTimestamp("date_create");
        task.setCreationDate(dateCreate);

        String description = resultSet.getString("description");
        task.setDescription(description);

        Timestamp finishDate = resultSet.getTimestamp("finish_date");
        task.setFinishDate(finishDate);

       /* int responsibleId = resultSet.getInt("responsible_id");
        User responsible = daoFactory.getUserDao().getUserById(responsibleId);
        task.setResponsibleUser(responsible);*/

        int periodOrdinal = resultSet.getInt("period");
        Period period = Period.values()[periodOrdinal];
        task.setPeriod(period);

        int taskTypeId = resultSet.getInt("task_type_id");
        TaskType task_type = daoFactory.getTaskTypeDao().getTaskTypeById(taskTypeId);
        task.setTaskType(task_type);

        return task;
    }
}
