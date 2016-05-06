package com.becomejavasenior.dao;

import com.becomejavasenior.model.*;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/resources/hsqlTextContext.xml"})
public class DealDaoTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private DealDao dealDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private TaskDao taskDao;

    @Test
    public void testCreate() throws Exception {
        Company company = companyDao.getCompanyById(1);
        User user = new User();
        user.setId(1);
        Deal deal = dealDao.getDealById(1);
        deal.setCompany(company);
        deal.setCreatedByUser(user);
        deal.setResponsibleUser(user);
        deal.setCreationDate(new Date());
        int result  = dealDao.createWithId(deal);
        assertThat(result, Matchers.greaterThan(0));
    }

    @Test
    public void testGetDealById() throws Exception {
        Deal deal = dealDao.getDealById(1);
        assertThat(deal, Matchers.notNullValue());
    }

    @Test
    public void testUpdate() throws Exception {
        Company company = companyDao.getCompanyById(1);
        User user = new User();
        user.setId(1);
        Deal deal = dealDao.getDealById(2);
        deal.setCompany(company);
        deal.setCreatedByUser(user);
        deal.setResponsibleUser(user);
        deal.setCreationDate(new Date());
        int result = dealDao.update(deal);
        assertThat(result, equalTo(1));
    }

    @Test
    public void testUpdateDealContact() throws Exception {
        Deal deal = dealDao.getDealById(1);
        Contact contact = new Contact();
        contact.setId(1);
        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact);
        deal.setContacts(contacts);
        int result = dealDao.updateDealContact(deal);
        assertThat(result, equalTo(1));
    }

    @Test
    public void testGetMaxId() throws Exception {
        int result = dealDao.getMaxId();
        assertThat(result, Matchers.greaterThan(0));
    }

    @Test
    public void testDelete() throws Exception {
        Deal deal = dealDao.getDealById(2);
        dealDao.delete(deal);
        deal = dealDao.getDealById(2);
        assertThat(deal.getDeleted(), Matchers.equalTo(true));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Deal> deals = dealDao.findAll();
        assertThat(deals, Matchers.notNullValue());
        assertThat(deals.size(), Matchers.greaterThan(0));
    }

    @Test
    public void testCountDealsWithTasks() throws Exception {
        int result = dealDao.countDealsWithTasks();
        assertThat(result, equalTo(1));
    }

    @Test
    public void testDealsForContactById() throws Exception {
        Contact contact = new Contact();
        contact.setId(1);
        List<Deal> deals = dealDao.getDealsForContactById(contact);
        assertThat(deals, Matchers.notNullValue());
        assertThat(deals.size(), equalTo(1));
    }

    @Test
    public void testDealsForCompanyById() throws Exception {
        Company company = companyDao.getCompanyById(1);
        List<Deal> deals = dealDao.getDealsForCompanyById(company);
        assertThat(deals, Matchers.notNullValue());
        assertThat(deals.size(), equalTo(2));
    }

    @Test
    public void testDealForTask() throws Exception {
        Task task = taskDao.getTaskById(1);
        Deal deal = dealDao.getDealForTask(task);
        assertThat(deal, Matchers.notNullValue());
    }

    @Test
    public void testCreateDealForContact() throws Exception {
        int contact_id = 2;
        Deal deal = dealDao.getDealById(2);
        int result = dealDao.createDealForContact(contact_id, deal);
        assertThat(result, equalTo(1));
    }

    @After
    public void tearDown() throws Exception {
        dataSource.getConnection().close();
    }
}