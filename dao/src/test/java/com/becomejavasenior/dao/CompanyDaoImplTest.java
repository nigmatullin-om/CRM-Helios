package com.becomejavasenior.dao;

import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.PhoneType;
import com.becomejavasenior.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class CompanyDaoImplTest {
    private static final Logger log = LogManager.getLogger(NoteDaoImplTest.class);

    private UserDao userDao;
    private CompanyDao companyDao;
    private final int USER_ID = 1;
    private final String  COMPANY_NAME = "test company";

    @Before
    public void setUp(){
        DaoFactory daoFactory = new DaoFactoryImpl();
        userDao = daoFactory.getUserDao();
        companyDao = daoFactory.getCompanyDao();
    }

    /*@Test
    public void testCreateWithId() throws Exception {
        Company company = getCompanyWithName(COMPANY_NAME);
        int id = companyDao.createWithId(company);
        Assert.assertTrue(id > 0);

        Company controlCompany = companyDao.getCompanyById(id);
        Assert.assertNotNull(controlCompany);
        Assert.assertEquals(COMPANY_NAME,controlCompany.getName());

        int result = companyDao.delete(controlCompany);
        Assert.assertTrue(result > 0);


    }*/

    public Company getCompanyWithName (String name){
        Company company = new Company();
        company.setName(name);
        company.setResponsibleUser(getUser());
        company.setWeb("test web");
        company.setEmail("test email");
        company.setAddress("test adress");
        company.setPhone("test phone");
        company.setPhoneType(PhoneType.DIRECT_WORK_PHONE);
        company.setCreatedByUser(getUser());
        company.setCreationDate(new Date());
        company.setDeleted(false);
        company.setModifiedByUser(getUser());
        company.setModificationDate(new Date());
        return company;
    }

    public User getUser(){
        User user = null;
        try {
            user = userDao.getUserById(USER_ID);
        } catch (DatabaseException e) {
            log.error("error while getting user from DB:" + e);
        }
        return user;
    }
}