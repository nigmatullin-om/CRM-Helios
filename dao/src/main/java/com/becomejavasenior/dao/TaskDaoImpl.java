package com.becomejavasenior.dao;

import com.becomejavasenior.Task;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDaoImpl extends AbstractDao implements TaskDao {
    private final String READ_TASK = "SELECT * FROM crm_helios.task WHERE id=?";
    private final String CREATE_TASK = "INSERT INTO crm_helios.task (name, finish_date, responsible_id, description, " +
                                    "contact_id, deal_id, company_id, created_by, date_create) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_TASK = "UPDATE crm_helios.task SET name=?, finish_date=?, responsible_id=?, description=?, " +
                                    "contact_id=?, deal_id=?, company_id=?, created_by=?, date_create=?";
    private final String DELETE_TASK = "DELETE FROM crm_helios WHERE id=?";
    private final String FIND_ALL_TASKS = "SELECT * FROM crm_helios.task";

    public int create(Task task) throws DaoException {
        Connection connection = null;
        connection = getConnection();
        if (connection != null){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TASK);
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
                connection.close();
                return 1;
            } catch (SQLException e) {
                throw  new DaoException(e.getMessage());
            }
        }
        else {
            throw new DaoException("Can not get connection");
        }
    }

    public Task read(int id) throws DaoException {
        Connection connection = null;
        connection = getConnection();
        if (connection != null){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(READ_TASK);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    Task task = new Task();
                    task.setId(resultSet.getInt(1));
                    task.setName(resultSet.getString(2));
                    task.setFinishDate(resultSet.getDate(3));
                    /*int resposibleId = resultSet.getInt(4);*/
                    task.setDescription(resultSet.getString(5));
/*                    int contactId = resultSet.getInt(6);
                    int dealId = resultSet.getInt(7);
                    int companyId = resultSet.getInt(8);
                    int createdById = resultSet.getInt(9);*/
                    task.setCreationDate(resultSet.getDate(10));

                    /*DaoFactory daoFactory = new DaoFactoryImpl();
                    UserDao userDao = daoFactory.getUserDao();
                    task.setResponsibleUser(userDao.read(resposibleId));
                    task.setCreatedByUser(userDao.read(createdById));

                    CompanyDao companyDao = daoFactory.getCompanyDao();
                    task.setCompany(companyDao.read(companyId));

                    ContactDao contactDao = daoFactory.getContactDao();
                    task.setContact(contactDao.read(contactId));

                    DealDao dealDao = daoFactory.getDealDao();
                    task.setDeal(dealDao.read(dealId));*/
                    connection.close();
                    return task;
                }
                else {
                    return null;
                }
            } catch (SQLException e) {
                throw new DaoException(e.getMessage());
            }
        }
        else {
            throw new DaoException("Can not get connection");
        }
    }

    public boolean update(Task task) throws DaoException {
        Connection connection = null;
        connection = getConnection();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TASK);
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
                connection.close();
                return true;
            } catch (SQLException e) {
                throw new DaoException(e.getMessage());
            }
        }
        else {
            throw new DaoException("Can not get connection");
        }
    }

    public boolean delete(Task task) throws DaoException {
        Connection connection = null;
        connection = getConnection();
        if (connection != null){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TASK);
                preparedStatement.setInt(1, task.getId());
                preparedStatement.execute();
                connection.close();
                return true;
            } catch (SQLException e) {
                throw new DaoException(e.getMessage());
            }
        }
        else {
            throw new DaoException("Can not get connection");
        }
    }

    public List<Task> findAll() throws DaoException {
        Connection connection = null;
        connection = getConnection();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_TASKS);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Task> tasks = new ArrayList<Task>();
                while (resultSet.next()) {
                    Task task = new Task();
                    task.setId(resultSet.getInt(1));
                    task.setName(resultSet.getString(2));
                    task.setFinishDate(resultSet.getDate(3));
                    /*int resposibleId = resultSet.getInt(4);*/
                    task.setDescription(resultSet.getString(5));
                    /*int contactId = resultSet.getInt(6);
                    int dealId = resultSet.getInt(7);
                    int companyId = resultSet.getInt(8);
                    int createdById = resultSet.getInt(9);*/
                    task.setCreationDate(resultSet.getDate(10));

                    /*DaoFactory daoFactory = new DaoFactoryImpl();
                    UserDao userDao = daoFactory.getUserDao();
                    task.setResponsibleUser(userDao.read(resposibleId));
                    task.setCreatedByUser(userDao.read(createdById));

                    CompanyDao companyDao = daoFactory.getCompanyDao();
                    task.setCompany(companyDao.read(companyId));

                    ContactDao contactDao = daoFactory.getContactDao();
                    task.setContact(contactDao.read(contactId));

                    DealDao dealDao = daoFactory.getDealDao();
                    task.setDeal(dealDao.read(dealId));*/

                    tasks.add(task);
                }
                connection.close();
                return tasks;
            } catch (SQLException e) {
                throw new DaoException(e.getMessage());
            }
        }
        else {
            throw new DaoException("Can not get connection");
        }
    }

    public TaskDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
