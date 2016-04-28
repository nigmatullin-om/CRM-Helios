package com.becomejavasenior.dao.unit;

import com.becomejavasenior.dao.*;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import java.util.Date;

public class ContactDaoImplTest {
    private static final Logger log = LogManager.getLogger(TagDaoImplTest.class);

    private TagDao tagDao;
    private UserDao userDao;
    private DealDao dealDao;
    private CompanyDao companyDao;
    private ContactDao contactDao;
    private final int USER_ID = 1;
    private final int COMPANY_ID = 1;
    private final String CONTACT_NAME = "test contact";
    private final int DEAL_ID = 1;

    @Before
    public void setUp(){
        DaoFactory daoFactory = new DaoFactoryImpl();
        tagDao = daoFactory.getTagDao();
        userDao = daoFactory.getUserDao();
        dealDao = daoFactory.getDealDao();
        companyDao = daoFactory.getCompanyDao();
        contactDao = daoFactory.getContactDao();
    }

    /*@Test
    public void testAddContactToDeal() throws Exception {
        Deal deal = getDeal();
        Contact contact = getCompanyWithName(CONTACT_NAME);
        int id = contactDao.createWithId(contact);
        Assert.assertTrue(id > 0);
        contact.setId(id);

        int result  = contactDao.addContactToDeal(contact.getId(), deal.getId());
        Assert.assertTrue(result > 0);

        contact.setDeleted(true);
        result = contactDao.update(contact);
        Assert.assertTrue(result > 0);
    }*/

    /*@Test
    public void testCreateWithId() throws Exception {
        Contact contact = getCompanyWithName(CONTACT_NAME);
        int id = contactDao.createWithId(contact);
        Assert.assertTrue(id > 0);

        Contact controlContact = contactDao.getContactById(id);
        Assert.assertNotNull(controlContact);
        Assert.assertEquals(CONTACT_NAME, controlContact.getName());

        int result = contactDao.delete(controlContact);
        Assert.assertTrue(result > 0);

    }*/

    public Contact getCompanyWithName(String name){
        Contact contact = new Contact();
        contact.setName(name);
        contact.setPhone("test phone");
        contact.setEmail("test email");
        contact.setSkype("test skype");
        contact.setPosition("test position");
        contact.setResponsibleUser(getUser());
        contact.setPhoneType(PhoneType.DIRECT_WORK_PHONE);
        contact.setCompany(getCompany());
        contact.setCreationDate(new Date());
        contact.setDeleted(false);
        contact.setModificationDate(new Date());
        contact.setModifiedByUser(getUser());
        return contact;
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

    private Deal getDeal(){
        Deal deal = null;
        try {
            deal = dealDao.getDealById(DEAL_ID);
        } catch (DatabaseException e) {
            log.error("error while reading deal from DB: " + e);
        }
        return deal;
    }
}