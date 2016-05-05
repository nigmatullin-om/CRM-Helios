package com.becomejavasenior.dao.integration;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.Task;
import com.becomejavasenior.model.User;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;

public class ContactDaoTest extends AbstractTestDao {
    public static final String CONTACT_TEST_DATA_XML = "contactTestData.xml";
    public static final int NO_CONTACT_FOR_TASK1 = 1;
    public static final int CONTACT4_FOR_TASK4 = 4;


    public static final int CONTACT5 = 5;
    public static final int DEAL7 = 7;
    public static final int USER5 = 5;
    public static final int COMPANY5 = 5;


    @Test
    public void testGetContactForTaskReturnNull() throws DatabaseException {
        Task task1 = taskDao.getTaskById(NO_CONTACT_FOR_TASK1);
        Contact contact = contactDao.getContactForTask(task1);
        assertThat(contact, nullValue());
    }

    @Test
    public void testGetContactForTaskReturnContact() throws DatabaseException {
        Task task4 = taskDao.getTaskById(CONTACT4_FOR_TASK4);
        Contact contact = contactDao.getContactForTask(task4);
        assertThat(contact, Matchers.notNullValue());
    }

    @Test
    public void testAddContactToDeal() throws DatabaseException {
        Deal deal = dealDao.getDealById(DEAL7);
        Contact contact = contactDao.getContactById(CONTACT5);
        int result = contactDao.addContactToDeal(contact.getId(), deal.getId());
        assertThat(result, Matchers.greaterThan(0));

    }

    @Test
    public void testCreateWithId() throws DatabaseException {
        User user = userDao.getUserById(USER5);
        Contact contact = contactDao.getContactById(CONTACT5);
        contact.setCreationDate(new Date());
        contact.setModificationDate(new Date());
        contact.setResponsibleUser(user);
        contact.setModifiedByUser(user);
        contact.setCompany(companyDao.getCompanyById(COMPANY5));
        int result = contactDao.createWithId(contact);
        assertThat(result, Matchers.greaterThan(0));
    }

    @Override
    protected IDataSet getSpecificDataSet() throws DataSetException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(CONTACT_TEST_DATA_XML);
        ReplacementDataSet contactDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(resourceAsStream));
        contactDataSet.addReplacementObject("[null]", null);
        return contactDataSet;
    }
}
