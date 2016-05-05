package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.CompanyDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.service.CompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Resource
    private CompanyDao companyDao;

    public CompanyDao getCompanyDao() {
        return companyDao;
    }

    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
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
    public int createWithId(Company company) throws DatabaseException {
        return companyDao.createWithId(company);
    }
}
