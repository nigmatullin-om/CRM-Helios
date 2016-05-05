package com.becomejavasenior.dao;

import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.Task;
import com.becomejavasenior.model.User;

import java.util.List;

public interface UserDao {
    int create(User user) throws DatabaseException;
    boolean isEmailExist(String email) throws DatabaseException;
    User getUserById(int id) throws DatabaseException;
    User getUserByName(String name) throws DatabaseException;
    int update(User user) throws DatabaseException;
    int delete(User user) throws DatabaseException;
    List<User> findAll() throws DatabaseException;
    User getResponsibleUserForTask(Task task) throws DatabaseException;
    User createdByUserForTask(Task task) throws DatabaseException;
    User getCreatedByUserForDeal(Deal deal) throws DatabaseException;
    User getResponsibleUserForDeal(Deal deal) throws DatabaseException;
}
