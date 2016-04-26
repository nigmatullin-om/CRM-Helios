package com.becomejavasenior.dao;

import com.becomejavasenior.dao.impl.CompanyDaoImpl;
import com.becomejavasenior.dao.impl.TaskDaoImpl;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Task;
import com.becomejavasenior.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;

public class CompanyDaoTest extends AbstractTestDao {
    private static final Logger LOGGER = LogManager.getLogger(CompanyDaoTest.class);
    public static final String COMPANY_TEST_DATA_XML = "companyTestData.xml";
    public static final int NO_COMPANY_FOR_TASK1 = 1;
    public static final int COMPANY4_FOR_TASK4 = 4;
    public static final int COMPANY_5 = 5;
    public static final int USER_ID = 5;
    public static final String COMPANY_NAME = "test company";
    private CompanyDao companyDao = new CompanyDaoImpl(getDataSource());
    private TaskDao taskDao = new TaskDaoImpl(getDataSource());

    @Test
    public void testGetCompanyForTaskReturnNull() throws DatabaseException {
        Task task1 = taskDao.getTaskById(NO_COMPANY_FOR_TASK1);
        Company company = companyDao.getCompanyForTask(task1);
        assertThat(company, nullValue());
    }

    @Test
    public void testGetCompanyForTaskReturnCompany() throws DatabaseException {
        Task task4 = new Task();
        task4.setId(4);
        Company company = companyDao.getCompanyForTask(task4);
        assertThat(company, Matchers.notNullValue());
    }

    @Test
    public void testGetCompanyById () throws DatabaseException  {
        Company company = companyDao.getCompanyById(COMPANY_5);
        assertThat(company, Matchers.notNullValue());
    }

    @Test
    public void testCreateCompany() throws DatabaseException {
        Company company = companyDao.getCompanyById(COMPANY_5);
        User user = new User();
        user.setId(USER_ID);
        company.setCreatedByUser(user);
        company.setResponsibleUser(user);
        company.setModifiedByUser(user);
        company.setModificationDate(new Date());
        company.setCreationDate(new Date());
        int result = companyDao.create(company);
        assertThat(result, Matchers.greaterThan(0));
    }

    @Test
    public void testUpdateCompany() throws DatabaseException {
        Company company = companyDao.getCompanyById(COMPANY_5);
        assertThat(company, Matchers.notNullValue());
        company.setName(COMPANY_NAME);
        User user = new User();
        user.setId(USER_ID);
        company.setCreatedByUser(user);
        company.setResponsibleUser(user);
        company.setModifiedByUser(user);
        company.setModificationDate(new Date());
        company.setCreationDate(new Date());
        companyDao.update(company);
        company = companyDao.getCompanyById(COMPANY_5);
        assertThat(company.getName(),Matchers.equalTo(COMPANY_NAME));
    }

    @Test
    public void testDeleteCompany() throws DatabaseException {
        Company company = companyDao.getCompanyById(COMPANY_5);
        companyDao.delete(company);
        company = companyDao.getCompanyById(COMPANY_5);
        assertThat(company.getDeleted(), Matchers.equalTo(true));
    }

    @Test
    public void testMaxId() throws DatabaseException {
        int result = companyDao.getMaxId();
        assertThat(result, Matchers.greaterThan(0));
        LOGGER.info("maxId: " + result);
    }

    @Test
    public void testGetCompaniesCount() throws DatabaseException {
        int result = companyDao.getCount();
        assertThat(result, Matchers.greaterThan(0));
        LOGGER.info("count: " + result);
    }

    @Test
    public void testFindAll() throws DatabaseException {
        List<Company> companies = companyDao.findAll();
        assertThat(companies, Matchers.notNullValue());
        assertThat(companies.size(), Matchers.greaterThan(0));
        for (Company company : companies){
            LOGGER.error(company.toString());
        }
    }

    @Test
    public void testCreateWithId() throws DatabaseException {
        Company company = companyDao.getCompanyById(COMPANY_5);
        User user = new User();
        user.setId(USER_ID);
        company.setCreatedByUser(user);
        company.setResponsibleUser(user);
        company.setModifiedByUser(user);
        company.setModificationDate(new Date());
        company.setCreationDate(new Date());
        company.setName(COMPANY_NAME);
        int result = companyDao.createWithId(company);
        assertThat(result, Matchers.greaterThan(0));
        LOGGER.error("generated key: " + result);
    }

    @Override
    protected IDataSet getSpecificDataSet() throws DataSetException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(COMPANY_TEST_DATA_XML);
        ReplacementDataSet contactDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(resourceAsStream));
        contactDataSet.addReplacementObject("[null]", null);
        return contactDataSet;
    }
}
