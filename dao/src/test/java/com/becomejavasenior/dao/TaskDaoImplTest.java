package com.becomejavasenior.dao;

import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TaskDaoImplTest {
    private static final Logger log = LogManager.getLogger(TaskDaoImplTest.class);
    private UserDao userDao;
    private CompanyDao companyDao;
    private TaskTypeDao taskTypeDao;
    private TaskDao taskDao;
    private final int USER_ID = 1;
    private final int COMPANY_ID = 1;
    private final int TASK_TYPE_ID = 1;
    private final String TASK_NAME = "test task";

    @Before
    public void setUp () {
        DaoFactory daoFactory = new DaoFactoryImpl();
        userDao = daoFactory.getUserDao();
        companyDao = daoFactory.getCompanyDao();
        taskTypeDao = daoFactory.getTaskTypeDao();
        taskDao = daoFactory.getTaskDao();
    }

    /*@Test
    public void testCreateWithId() throws Exception {
        Task  task = getTaskWithName(TASK_NAME);
        int id = taskDao.createWithId(task);
        Assert.assertTrue(id > 0);

        Task controlTask = taskDao.getTaskById(id);
        Assert.assertNotNull(controlTask);
        Assert.assertEquals(TASK_NAME, controlTask.getName());

        int result  = taskDao.delete(controlTask);
        Assert.assertTrue(result > 0);
    }*/

    private Task getTaskWithName (String name){
        Task task = new Task();
        task.setName(name);
        task.setFinishDate(new Date());
        task.setResponsibleUser(getUser());
        task.setPeriod(Period.TOMORROW);
        task.setDescription("test description");
        task.setCreatedByUser(getUser());
        task.setCreationDate(new Date());
        task.setDeleted(false);
        task.setDone(false);
        task.setTaskType(getTaskType());
        return task;
    }

    public User getUser(){
        User user = null;
        try {
            user = userDao.getUserById(USER_ID);
        } catch (DatabaseException e) {
            log.error("error while getting user from DB:" + e);
        }
        return user;
    }

    public Company getCompany(){
        Company company = null;
        try {
            company = companyDao.getCompanyById(COMPANY_ID);
        } catch (DatabaseException e) {
            log.error("error while getting company from DB:" + e);
        }
        return company;
    }

    public TaskType getTaskType(){
       TaskType taskType= null;
        try {
            taskType = taskTypeDao.getTaskTypeById(TASK_TYPE_ID);
        } catch (DatabaseException e) {
            log.error("error while getting task type from DB:" + e);
        }
        return taskType;
    }




}