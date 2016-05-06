package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.CompanyDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.service.CompanyService;

import java.sql.Timestamp;
import java.util.List;

public class CompanyServiceImpl implements CompanyService {

    private CompanyDao companyDao;

    public CompanyServiceImpl() {
        this.companyDao = new DaoFactoryImpl().getCompanyDao();
    }

    @Override
    public int create(Company company) throws DatabaseException {
        return companyDao.create(company);
    }

    @Override
    public Company getCompanyById(int id) throws DatabaseException {
        return companyDao.getCompanyById(id);
    }

    @Override
    public int update(Company company) throws DatabaseException {
        return companyDao.update(company);
    }

    @Override
    public int delete(Company company) throws DatabaseException {
        return companyDao.delete(company);
    }

    @Override
    public List<Company> findAll() throws DatabaseException {
        return companyDao.findAll();
    }

    @Override
    public int getCount() throws DatabaseException {
        return companyDao.getCount();
    }

    @Override
    public List<Integer> withoutTasks() throws DatabaseException {
        return companyDao.withoutTasks();
    }

    @Override
    public List<Integer> withOutdatedTasks() throws DatabaseException {
        return companyDao.withOutdatedTasks();
    }

    @Override
    public List<Integer> markedDelete() throws DatabaseException {
        return companyDao.markedDelete();
    }

    @Override
    public List<Integer> byPeriod(Timestamp period, String createdOrModified) throws DatabaseException {
        return companyDao.byPeriod(period, createdOrModified);
    }

    @Override
    public List<Integer> byTask(Timestamp byTask) throws DatabaseException {
        return companyDao.byTask(byTask);
    }

    @Override
    public List<Integer> byUser(String userName) throws DatabaseException {
        return companyDao.byUser(userName);
    }

    @Override
    public List<Integer> byTag(String tagName) throws DatabaseException {
        return companyDao.byTag(tagName);
    }

    @Override
    public List<Integer> byStage(String[] byStages) throws DatabaseException {
        return companyDao.byStage(byStages);
    }

    @Override
    public List<Integer> modified() throws DatabaseException {
        return companyDao.modified();
    }

}
