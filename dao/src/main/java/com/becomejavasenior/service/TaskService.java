package com.becomejavasenior.service;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Task;

import java.util.List;
import java.util.Map;

/**
 * Created by aivlev on 3/24/16.
 */
public interface TaskService {
    void create(Task task) throws DatabaseException;

    Task getTaskById(int id) throws DatabaseException;

    void update(Task task) throws DatabaseException;

    void delete(Task task) throws DatabaseException;

    List<Task> findAll() throws DatabaseException;

    Map<String,List<Task>> filterTasksByDone(List<Task> tasks);

    List<Task> getRunningTasks(List<Task> notDoneTasks);
}
