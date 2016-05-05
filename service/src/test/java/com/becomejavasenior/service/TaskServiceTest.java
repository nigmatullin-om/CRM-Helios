package com.becomejavasenior.service;

import com.becomejavasenior.dao.*;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.*;
import com.becomejavasenior.service.impl.TaskServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class TaskServiceTest {

    private static final String NOT_FINISHED_DATE_VALUE = "2020-01-01 00:00:00";
    private static final String FINISHED_DATE_VALUE = "2016-03-23 00:00:00";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DONE_TASKS = "doneTasks";
    private static final String NOT_DONE_TASKS = "notDoneTasks";

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskDao taskDao;

    @Mock
    private ContactDao contactDao;

    @Mock
    private CompanyDao companyDao;

    @Mock
    private DealDao dealDao;

    @Mock
    private UserDao userDao;

    @Mock
    private TaskTypeDao taskTypeDao;

    @Mock
    private Task task;

    @Mock
    private Contact contact;

    @Mock
    private Company company;

    @Mock
    private Deal deal;

    @Mock
    private User user;

    @Mock
    private TaskType taskType;

    @Mock
    DaoFactoryImpl daoFactory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(contactDao.getContactForTask(any())).thenReturn(contact);
        when(companyDao.getCompanyForTask(any())).thenReturn(company);
        when(dealDao.getDealForTask(any())).thenReturn(deal);
        when(userDao.getResponsibleUserForTask(any())).thenReturn(user);
        when(userDao.createdByUserForTask(any())).thenReturn(user);
        when(taskTypeDao.getTaskTypeForTask(any())).thenReturn(taskType);
    }

    @Test
    public void testCreate() throws Exception {
        when(taskDao.create(any(Task.class))).thenReturn(1);
        taskService.create(task);

        verify(taskDao).create(task);

    }

    @Test
    public void testGetTaskById() throws Exception {
        when(taskDao.getTaskById(1)).thenReturn(task);

        Task resultTask = taskService.getTaskById(1);

        verify(taskDao).getTaskById(1);
        Assert.assertEquals(task, resultTask);

    }

    @Test
    public void testUpdate() throws Exception {
        when(taskDao.update(any(Task.class))).thenReturn(1);
        taskService.update(task);

        verify(taskDao).update(task);

    }

    @Test
    public void testDelete() throws Exception {
        when(taskDao.delete(any(Task.class))).thenReturn(1);
        taskService.delete(task);

        verify(taskDao).delete(task);
    }

    @Test
    public void testFindAll() throws DatabaseException {



        List<Task> tasks = new LinkedList<>();

        Task firstTask = new Task();
        firstTask.setId(1);
        firstTask.setName("First Task");

        tasks.add(firstTask);

        Task secondTask = new Task();
        secondTask.setId(2);
        secondTask.setName("Second Task");

        tasks.add(secondTask);

        when(taskDao.findAll()).thenReturn(tasks);
        List<Task> result = taskService.findAll();

        verify(taskDao).findAll();
        assertEquals(2, result.size());

    }

    @Test
    public void testFilterTasksByDone() throws Exception {
        List<Task> tasks = new LinkedList<>();

        Task firstTask = new Task();
        firstTask.setId(1);
        firstTask.setName("First Task");
        firstTask.setDone(true);

        tasks.add(firstTask);

        Task secondTask = new Task();
        secondTask.setId(2);
        secondTask.setName("Second Task");
        secondTask.setDone(false);

        tasks.add(secondTask);

        Map<String, List<Task>> mapTasks = taskService.filterTasksByDone(tasks);

        assertEquals(1, mapTasks.get(DONE_TASKS).size());
        assertEquals(1, mapTasks.get(NOT_DONE_TASKS).size());

    }

    @Test
    public void testGetRunningTasks() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date finishDate = dateFormat.parse(NOT_FINISHED_DATE_VALUE);

        List<Task> tasks = new LinkedList<>();
        Task firstTask = new Task();
        firstTask.setId(1);
        firstTask.setName("First Task");
        firstTask.setDone(false);
        firstTask.setFinishDate(finishDate);

        tasks.add(firstTask);

        Task secondTask = new Task();
        secondTask.setId(2);
        secondTask.setName("Second Task");
        secondTask.setDone(false);

        finishDate = dateFormat.parse(FINISHED_DATE_VALUE);
        secondTask.setFinishDate(finishDate);

        tasks.add(secondTask);

        List<Task> result = taskService.getRunningTasks(tasks);

        assertEquals(1, result.size());



    }
}