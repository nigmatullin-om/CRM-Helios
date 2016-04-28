package com.becomejavasenior.dao.integration;

import com.becomejavasenior.dao.CompanyDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.DealDao;
import com.becomejavasenior.dao.TaskDao;
import com.becomejavasenior.dao.impl.CompanyDaoImpl;
import com.becomejavasenior.dao.impl.DealDaoImpl;
import com.becomejavasenior.dao.impl.TaskDaoImpl;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.Task;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;

public class CompanyDaoTest extends AbstractTestDao {
    public static final String COMPANY_TEST_DATA_XML = "companyTestData.xml";
    public static final int NO_COMPANY_FOR_TASK1 = 1;
    public static final int COMPANY4_FOR_TASK4 = 4;
    public static final int NO_COMPANY_FOR_DEAL4 = 4;
    public static final int COMPANY1_FOR_DEAL1 = 1;
    public static final String USERNAME_FOR_COMPANIES = "Andrey Yarmolenko";
    public static final String[] STAGE_FOR_COMPANIES_WITHOUT_DEALS = new String[]{"without deals"};
    public static final String[] STAGE_FOR_COMPANIES_WITHOUT_OPEN_DEALS = new String[]{"without open deals"};
    public static final Timestamp PERIOD_FROM = Timestamp.valueOf("2016-03-10 10:10:10.0");
    public static final Timestamp PERIOD_FROM_FOR_TASK = Timestamp.valueOf("2016-12-10 10:10:10.0");
    public static final String CREATED = "created";
    public static final String MODIFIED = "modified";
    public static final String TAG = "# Firs";
    public static final String[] STAGENAME_FOR_COMPANIES = new String[]{"approval of the contract"};
    private CompanyDao companyDao = new CompanyDaoImpl(getDataSource());
    private TaskDao taskDao = new TaskDaoImpl(getDataSource());
    private DealDao dealDao = new DealDaoImpl(getDataSource());

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
    public void testGetCompanyForDealReturnCompany() throws DatabaseException {
        Deal deal1 = dealDao.getDealById(COMPANY1_FOR_DEAL1);
        Company company = companyDao.getCompanyForDeal(deal1);
        assertThat(company, Matchers.notNullValue());
    }

    @Override
    protected IDataSet getSpecificDataSet() throws DataSetException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(COMPANY_TEST_DATA_XML);
        ReplacementDataSet contactDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(resourceAsStream));
        contactDataSet.addReplacementObject("[null]", null);
        return contactDataSet;
    }

    @Test
    public void testGetCompaniesForUsername() throws DatabaseException {
        ArrayList<Integer> companies_id_expected = new ArrayList<>();
        companies_id_expected.add(1);
        List<Integer> companies_id = companyDao.byUser(USERNAME_FOR_COMPANIES);
        Assert.assertEquals(companies_id_expected, companies_id);
    }

    @Test
    public void testGetCompaniesWithoutTasks() throws DatabaseException {
        ArrayList<Integer> companies_id_expected = new ArrayList<>();
        companies_id_expected.add(2);
        List<Integer> companies_id = companyDao.withoutTasks();
        Assert.assertEquals(companies_id_expected, companies_id);
    }

    @Test
    public void testGetCompaniesWithoutDeals() throws DatabaseException {
        ArrayList<Integer> companies_id_expected = new ArrayList<>();
        companies_id_expected.add(5);
        List<Integer> companies_id = companyDao.byStage(STAGE_FOR_COMPANIES_WITHOUT_DEALS);
        Assert.assertEquals(companies_id_expected, companies_id);
    }

    @Test
    public void testGetCompaniesWithoutOpenDeals() throws DatabaseException {
        ArrayList<Integer> companies_id_expected = new ArrayList<>();
        companies_id_expected.add(5);
        List<Integer> companies_id = companyDao.byStage(STAGE_FOR_COMPANIES_WITHOUT_OPEN_DEALS);
        Assert.assertEquals(companies_id_expected, companies_id);
    }

    @Test
    public void testGetCompaniesWithOutdatedTasks() throws DatabaseException {
        ArrayList<Integer> companies_id_expected = new ArrayList<>();
        List<Integer> companies_id = companyDao.withOutdatedTasks();
        Assert.assertEquals(companies_id_expected, companies_id);
    }

    @Test
    public void testGetCompaniesMarkedDelete() throws DatabaseException {
        ArrayList<Integer> companies_id_expected = new ArrayList<>();
        List<Integer> companies_id = companyDao.markedDelete();
        Assert.assertEquals(companies_id_expected, companies_id);
    }

    @Test
    public void testGetCompaniesCreatedByPeriod() throws DatabaseException {
        ArrayList<Integer> companies_id_expected = new ArrayList<>();
        companies_id_expected.add(1);
        companies_id_expected.add(2);
        companies_id_expected.add(5);
        List<Integer> companies_id = companyDao.byPeriod(PERIOD_FROM, CREATED);
        Assert.assertEquals(companies_id_expected, companies_id);
    }

    @Test
    public void testGetCompaniesModifiedByPeriod() throws DatabaseException {
        ArrayList<Integer> companies_id_expected = new ArrayList<>();
        List<Integer> companies_id = companyDao.byPeriod(PERIOD_FROM, MODIFIED);
        Assert.assertEquals(companies_id_expected, companies_id);
    }

    @Test
    public void testGetCompaniesForTaskByPeriod() throws DatabaseException {
        ArrayList<Integer> companies_id_expected = new ArrayList<>();
        companies_id_expected.add(1);
        companies_id_expected.add(5);
        List<Integer> companies_id = companyDao.byTask(PERIOD_FROM_FOR_TASK);
        Assert.assertEquals(companies_id_expected, companies_id);
    }

    @Test
    public void testGetCompaniesForTagname() throws DatabaseException {
        ArrayList<Integer> companies_id_expected = new ArrayList<>();
        companies_id_expected.add(1);
        List<Integer> companies_id = companyDao.byTag(TAG);
        Assert.assertEquals(companies_id_expected, companies_id);
    }

    @Test
    public void testGetCompaniesForStagename() throws DatabaseException {
        ArrayList<Integer> companies_id_expected = new ArrayList<>();
        companies_id_expected.add(2);
        List<Integer> companies_id = companyDao.byStage(STAGENAME_FOR_COMPANIES);
        Assert.assertEquals(companies_id_expected, companies_id);
    }

    @Test
    public void testGetModifiedCompanies() throws DatabaseException {
        ArrayList<Integer> companies_id_expected = new ArrayList<>();
        List<Integer> companies_id = companyDao.modified();
        Assert.assertEquals(companies_id_expected, companies_id);
    }
}
