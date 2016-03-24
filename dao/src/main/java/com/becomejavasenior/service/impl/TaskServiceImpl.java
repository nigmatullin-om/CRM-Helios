package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.DaoFactory;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.TaskDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.DealStage;
import com.becomejavasenior.model.Task;
import com.becomejavasenior.service.TaskService;

import java.util.*;

/**
 * Created by aivlev on 3/24/16.
 */
public class TaskServiceImpl implements TaskService {

    private static final String DONE_TASKS = "doneTasks";
    private static final String NOT_DONE_TASKS = "notDoneTasks";

    private TaskDao taskDao;

    public TaskServiceImpl(){
        this.taskDao = new DaoFactoryImpl().getTaskDao();
    }


    @Override
    public List<Task> findAll() throws DatabaseException {
        return taskDao.findAll();
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
