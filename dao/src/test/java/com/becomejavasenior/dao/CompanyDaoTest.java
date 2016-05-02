package com.becomejavasenior.dao;

import com.becomejavasenior.dao.impl.CompanyDaoImpl;
import com.becomejavasenior.dao.impl.TaskDaoImpl;
import com.becomejavasenior.dao.impl.UserDaoImpl;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Contact;
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

public class CompanyDaoTest extends AbstractTestDao {
    public static final String COMPANY_TEST_DATA_XML = "companyTestData.xml";
    public static final int NO_COMPANY_FOR_TASK1 = 1;
    public static final int COMPANY4_FOR_TASK4 = 4;

    public static final int COMPANY4 = 5;
    public static final int USER5 = 5;

    private CompanyDao companyDao = new CompanyDaoImpl(getDataSource());
    private TaskDao taskDao = new TaskDaoImpl(getDataSource());
    private UserDao userDao = new UserDaoImpl(getDataSource());

    @Test
    public void testGetCompanyForTaskReturnNull() throws DatabaseException {
        Task task1 = taskDao.getTaskById(NO_COMPANY_FOR_TASK1);
        Company company = companyDao.getCompanyForTask(task1);
        assertThat(company, nullValue());
    }

    @Test
    public void testGetCompanyForTaskReturnCompany() throws DatabaseException {
        Task task4 = taskDao.getTaskById(COMPANY4_FOR_TASK4);
        Company company = companyDao.getCompanyForTask(task4);
        assertThat(company, Matchers.notNullValue());
    }

    @Test
    public void testCreateWithId () throws DatabaseException {
        Company company = companyDao.getCompanyById(COMPANY4);
        User user = userDao.getUserById(USER5);
        company.setResponsibleUser(user);
        company.setCreatedByUser(user);
        company.setModifiedByUser(user);
        company.setModificationDate(new Date());
        company.setCreationDate(new Date());
        int id = companyDao.createWithId(company);
        assertThat(id, Matchers.greaterThan(0));
    }

    @Override
    protected IDataSet getSpecificDataSet() throws DataSetException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(COMPANY_TEST_DATA_XML);
        ReplacementDataSet contactDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(resourceAsStream));
        contactDataSet.addReplacementObject("[null]", null);
        return contactDataSet;
    }
}
