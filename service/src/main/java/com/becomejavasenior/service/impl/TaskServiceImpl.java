package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.*;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.*;
import com.becomejavasenior.service.TaskService;

import java.util.*;


public class TaskServiceImpl implements TaskService {

    private static final String DONE_TASKS = "doneTasks";
    private static final String NOT_DONE_TASKS = "notDoneTasks";

    private TaskDao taskDao;
    private ContactDao contactDao;
    private CompanyDao companyDao;
    private DealDao dealDao;
    private UserDao userDao;

    public TaskServiceImpl(){
        DaoFactoryImpl daoFactory = new DaoFactoryImpl();
        this.taskDao = daoFactory.getTaskDao();
        this.contactDao = daoFactory.getContactDao();
        this.companyDao = daoFactory.getCompanyDao();
        this.dealDao = daoFactory.getDealDao();
        this.userDao = daoFactory.getUserDao();
    }


    @Override
    public int create(Task task) throws DatabaseException {
        return taskDao.create(task);
    }

    @Override
    public Task getTaskById(int id) throws DatabaseException {
        Task taskById = taskDao.getTaskById(id);
        Contact contactForTask = contactDao.getContactForTask(taskById);
        taskById.setContact(contactForTask);

        Company companyForTask = companyDao.getCompanyForTask(taskById);
        taskById.setCompany(companyForTask);

        Deal dealForTask = dealDao.getDealForTask(taskById);
        taskById.setDeal(dealForTask);

        User responsibleUserForTask = userDao.getResponsibleUserForTask(taskById);
        taskById.setResponsibleUser(responsibleUserForTask);

        User createdByUserForTask = userDao.createdByUserForTask(taskById);
        taskById.setCreatedByUser(createdByUserForTask);

        return taskById;
    }

    @Override
    public int update(Task task) throws DatabaseException {
        return taskDao.update(task);
    }

    @Override
    public int delete(Task task) throws DatabaseException {
        return taskDao.delete(task);
    }

    @Override
    public List<Task> findAll() throws DatabaseException {
        List<Task> rawTasks = taskDao.findAll();
        List<Task> tasksWithAllFields = new ArrayList<>();

        for(Task task : rawTasks)
        {
           tasksWithAllFields.add(getTaskById(task.getId()));
        }
        return tasksWithAllFields;
    }

    @Override
    public Map<String, List<Task>> filterTasksByDone(List<Task> tasks) {
        Map<String, List<Task>> mapTasks = new HashMap<>();
        List<Task> doneTasks = new LinkedList<>();
        List<Task> notDoneTasks = new LinkedList<>();
        ListIterator<Task> listIterator = tasks.listIterator();
        while(listIterator.hasNext()){
            Task tempTask = listIterator.next();
            if(tempTask.isDone()){
                doneTasks.add(tempTask);
            } else {
                notDoneTasks.add(tempTask);
            }
        }
        mapTasks.put(DONE_TASKS, doneTasks);
        mapTasks.put(NOT_DONE_TASKS, notDoneTasks);
        return mapTasks;
    }

    @Override
    public List<Task> getRunningTasks(List<Task> notDoneTasks) {
        long currentTime = System.currentTimeMillis();
                List<Task> runningTasks = new LinkedList<>();
        ListIterator<Task> listIterator = notDoneTasks.listIterator();
        while(listIterator.hasNext()){
            Task tempTask = listIterator.next();
            if(tempTask.getFinishDate().getTime() > currentTime){
                runningTasks.add(tempTask);
            }
        }
        return runningTasks;
    }
}
