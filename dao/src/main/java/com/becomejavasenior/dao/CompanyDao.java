package com.becomejavasenior.dao;


import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Task;

import java.sql.Timestamp;
import java.util.List;

public interface CompanyDao {
    int create(Company company) throws DatabaseException;
    Company getCompanyById(int id) throws DatabaseException;
    int update(Company company) throws DatabaseException;
    int delete(Company company) throws DatabaseException;
    List<Company> findAll() throws DatabaseException;
    int getCount() throws DatabaseException;
    Company getCompanyForTask(Task task) throws DatabaseException;
    List<Integer> withoutTasks() throws DatabaseException;
    List<Integer> withOutdatedTasks() throws DatabaseException;
    List<Integer> markedDelete() throws DatabaseException;
    List<Integer> byPeriod(Timestamp period, String createdOrModified) throws DatabaseException;
    List<Integer> byTask(Timestamp byTask) throws DatabaseException;
    List<Integer> byUser(String userName) throws DatabaseException;
    List<Integer> byTag(String tagName) throws DatabaseException;
    List<Integer> byStage(String[] byStages) throws DatabaseException;
}
