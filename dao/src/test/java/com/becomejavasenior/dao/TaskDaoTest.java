package com.becomejavasenior.dao;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.impl.TaskDaoImpl;
import com.becomejavasenior.model.Task;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;


import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class TaskDaoTest extends AbstractTestDao {

    public static final int TEST_TASK_ID = 1;
    public static final int TEST_CONTACT_ID = 1;
    public static final int TEST_COMPANY_ID = 1;
    public static final int TEST_DEAL_ID = 1;
    public static final String TASK_TEST_DATA_XML = "taskTestData.xml";
    TaskDao taskDao = new TaskDaoImpl(getDataSource());

    @Test
    public void testReadTask() throws DatabaseException {
        Task task = taskDao.getTaskById(TEST_TASK_ID);
        assertThat(task, is(notNullValue()));
    }

    @Test
    public void testAddTask() throws DatabaseException {
        Task task = taskDao.getTaskById(TEST_TASK_ID);
        int newTaskId = taskDao.create(task);
        Task newTask = taskDao.getTaskById(newTaskId);
        assertThat(task.getName(), equalTo(newTask.getName()));
    }


    @Test(expected = DatabaseException.class)
    public void testDeleteTask() throws DatabaseException {
        Task task = taskDao.getTaskById(TEST_TASK_ID);
        taskDao.delete(task);
        Task deletedTask = taskDao.getTaskById(1);
    }


    @Test
    public void testUpdatedTask() throws DatabaseException {
        Task task = taskDao.getTaskById(TEST_TASK_ID);
        String newName = "new name";
        task.setName(newName);
        taskDao.update(task);

        Task updatedTask = taskDao.getTaskById(1);

        assertThat(updatedTask.getName(), equalTo(newName));
    }

    @Override
    protected IDataSet getSpecificDataSet() throws DataSetException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(TASK_TEST_DATA_XML);
        return new FlatXmlDataSetBuilder().build(resourceAsStream);
    }

/*    @Test
    public void testGetAllTasksForContactById() throws DatabaseException {
        List<Task> allTasksForContactBy1 = taskDao.getAllTasksForContactById(TEST_CONTACT_ID);
        int taskCountForCompanyId1 = 2;
        assertThat(allTasksForContactBy1, hasSize(taskCountForCompanyId1));
    }

    @Test
    public void testGetAllTasksForCompanyById() throws DatabaseException {
        List<Task> allTasksForCompanyBy1 = taskDao.getAllTasksForCompanyById(TEST_COMPANY_ID);
        int taskCountForCompanyId1 = 3;
        assertThat(allTasksForCompanyBy1, hasSize(taskCountForCompanyId1));
    }

    @Test
    public void testGetAllTasksForDealById() throws DatabaseException {
        List<Task> allTasksForDealBy1 = taskDao.getAllTasksForDealById(TEST_DEAL_ID);
        int taskCountForDealId1 = 24;
        assertThat(allTasksForDealBy1, hasSize(taskCountForDealId1));
    }

    @Test
    public void testGetAllTasks() throws DatabaseException {
        List<Task> allTasksForDealBy1 = taskDao.findAll();
        int allTaskCount = 31;
        assertThat(allTasksForDealBy1, hasSize(allTaskCount));
    }

    */
}
