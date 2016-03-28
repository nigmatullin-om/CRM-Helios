package com.becomejavasenior;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.DealDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class DaoDealTest {
//    public static  final String INSERT_TEST_DATA = "insert into crm_helios.deal values " +
//            "(3, 'test data1', 250.00, 1, 1, 2, 1, '2013-03-10 12:00:00'),"+
//            "(4, 'test data2', 200.00, 2, 4, 2, 2, '2012-03-10 11:00:00')";
//    public static  final String DELETE_TEST_DATA = "DELATE FROM crm.helios.deal " +
//            "WHERE id = 3 AND  id = ";
    static DealDao dealDao;
    Deal deal;
    Deal deal1;

    @BeforeClass
    public static void initClass(){
        DaoFactoryImpl daoFactory = new DaoFactoryImpl();
        dealDao =   daoFactory.getDealDao();
    }

    @Before
    public  void  init() {
        User responsibleUser = new User();
        responsibleUser.setId(1);
        Company company = new Company();
        company.setId(1);
        User createdByUser = new User();
        createdByUser.setId(2);

        deal = new Deal();
        deal.setId(3);
        deal.setName("some test deal");
        deal.setBudget(new BigDecimal(2332));
        deal.setResponsibleUser(responsibleUser);
        deal.setDealStage(DealStage.AGREEMENT);
        deal.setCompany(company);
        deal.setCreatedByUser(createdByUser);
        deal.setCreationDate(new Date(122123));
        deal.setDeleted(false);

        deal1 = new Deal();
        deal1.setId(4);
        deal1.setName("some another deal");
        deal1.setBudget(new BigDecimal(332));
        deal1.setResponsibleUser(responsibleUser);
        deal1.setDealStage(DealStage.DECIDING);
        deal1.setCompany(company);
        deal1.setCreatedByUser(createdByUser);
        deal1.setCreationDate(new Date(2123));
        deal1.setDeleted(false);

        try {
            dealDao.create(deal);
            dealDao.create(deal1);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @After
    public  void close(){
        try {
            dealDao.delete(deal1);
            dealDao.delete(deal);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadDeal(){
        try {
            assertEquals("some test deal" ,dealDao.getDealById(3).getName());
            assertEquals("some another deal" ,dealDao.getDealById(4).getName());
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadAllDeals() {
        String result = "";
        ArrayList<Deal> deals = null;
        try {
            deals = (ArrayList<Deal>) dealDao.findAll();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        for (Deal i : deals) {
            result = result + ";" + i.getName();
        }
        assertEquals(";поставка сумок;поставка кошельков;some test deal;some another deal", result);
    }

    @Test
    public void testGetDealsByCompanyId(){
        String result = "";
        ArrayList<Deal> deals = null;
        Company company = new Company();
        company.setId(1);
        try {
            deals = (ArrayList<Deal>) dealDao.getDealsForCompanyById(company);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        for (Deal i : deals) {
            result = result + ";" + i.getName();
        }
        assertEquals(";поставка сумок;some test deal;some another deal", result);
    }

    @Test
    public void testGetDealsByContactId(){
        ArrayList<Deal> deals = null;
        String result = "";
        Contact contact = new Contact();
        contact.setId(1);
        try {
            deals = (ArrayList<Deal>) dealDao.getDealsForContactById(contact);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        for(Deal i: deals){
            result = result + " " + i.getName();
        }
        assertEquals(" поставка сумок", result);
    }
}
