package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.CompanyDao;
import com.becomejavasenior.dao.ContactDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.dao.impl.DatabaseException;
import com.becomejavasenior.service.CompanyService;

/**
 * Created by aivlev on 3/23/16.
 */
public class CompanyServiceImpl implements CompanyService {

    private CompanyDao companyDao;

    public CompanyServiceImpl() {
        this.companyDao = new DaoFactoryImpl().getCompanyDao();
    }

    @Override
    public int getCount() throws DatabaseException {
        return companyDao.getCount();
    }
}
