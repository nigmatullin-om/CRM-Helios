package com.becomejavasenior.dao;

import com.becomejavasenior.dao.impl.ContactDaoImpl;
import com.becomejavasenior.dao.impl.DealDaoImpl;
import com.becomejavasenior.dao.impl.TaskDaoImpl;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.Task;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;

public class DealDaoTest2 extends AbstractTestDao {
    public static final String DEAL_TEST_DATA_XML = "dealTestData.xml";
    public static final int NO_DEAL_FOR_TASK2 = 2;
    public static final int DEAL4_FOR_TASK4 = 4;
    private DealDao dealDao = new DealDaoImpl(getDataSource());
    private TaskDao taskDao = new TaskDaoImpl(getDataSource());

    @Test
    public void testGetDealForTaskReturnNull() throws DatabaseException {
        Task task1 = taskDao.getTaskById(NO_DEAL_FOR_TASK2);
        Deal deal = dealDao.getDealForTask(task1);
        assertThat(deal, nullValue());
    }

    @Test
    public void testGetDealForTaskReturnDeal() throws DatabaseException {
        Task task4 = taskDao.getTaskById(DEAL4_FOR_TASK4);
        Deal deal = dealDao.getDealForTask(task4);
        assertThat(deal, Matchers.notNullValue());
    }

    @Override
    protected IDataSet getSpecificDataSet() throws DataSetException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(DEAL_TEST_DATA_XML);
        ReplacementDataSet contactDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(resourceAsStream));
        contactDataSet.addReplacementObject("[null]", null);
        return contactDataSet;
    }
}
