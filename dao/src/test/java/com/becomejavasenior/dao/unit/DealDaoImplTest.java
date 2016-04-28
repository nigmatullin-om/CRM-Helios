package com.becomejavasenior.dao.unit;

import com.becomejavasenior.dao.*;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.DealStage;
import com.becomejavasenior.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import java.math.BigDecimal;
import java.util.Date;

public class DealDaoImplTest {
    private static final Logger log = LogManager.getLogger(DealDaoImplTest.class);
    private UserDao userDao;
    private DealDao dealDao;
    private CompanyDao companyDao;
    private final int USER_ID = 1;
    private final int COMPANY_ID = 1;
    private final String DEAL_NAME = "test deal";

    @Before
    public void setUp () {
        DaoFactory daoFactory = new DaoFactoryImpl();
        userDao = daoFactory.getUserDao();
        dealDao = daoFactory.getDealDao();
        companyDao = daoFactory.getCompanyDao();
    }

   /* @Test
    public void testCreateWithId() throws Exception {
        Deal deal = getDealWithName(DEAL_NAME);
        int id = dealDao.createWithId(deal);
        if (id == 0) throw new AssertionError();

        Deal controlDeal = dealDao.getDealById(id);
        if (!controlDeal.getName().equals(DEAL_NAME)) throw new AssertionError();

        int result = dealDao.delete(controlDeal);
        if (result == 0) throw new AssertionError();
    }*/

    public Deal getDealWithName(String name){
        Deal deal = new Deal();
        deal.setName(name);
        deal.setBudget(new BigDecimal(10));
        deal.setResponsibleUser(getUser());
        deal.setDealStage(DealStage.AGREEMENT);
        deal.setCompany(getCompany());
        deal.setCreatedByUser(getUser());
        deal.setCreationDate(new Date());
        deal.setDeleted(false);
        return deal;
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

    public Company getCompany(){
        Company company = null;
        try {
            company = companyDao.getCompanyById(COMPANY_ID);
        } catch (DatabaseException e) {
            log.error("error while getting company from DB:" + e);
        }
        return company;
    }

}