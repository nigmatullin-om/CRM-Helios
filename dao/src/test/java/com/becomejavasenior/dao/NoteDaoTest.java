package com.becomejavasenior.dao;

import com.becomejavasenior.dao.impl.*;
import com.becomejavasenior.model.*;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class NoteDaoTest extends AbstractTestDao {
    private static final Logger log = LogManager.getLogger(NoteDaoTest.class);
    public static final String NOTE_TEST_DATA_XML = "noteTestData.xml";
    private DealDao dealDao = new DealDaoImpl(getDataSource());
    private UserDao userDao = new UserDaoImpl(getDataSource());
    private NoteDao noteDao = new NoteDaoImpl(getDataSource());
    private final int NOTE5 = 5;
    private final int USER5 = 5;
    private final int DEAL5 = 5;

    @Test
    public void testCreateWithId() throws DatabaseException {
        User user = userDao.getUserById(USER5);
        Note note = noteDao.getNoteById(NOTE5);
        note.setCreationDate(new Date());
        note.setCreatedByUser(user);
        int result = noteDao.createWithId(note);
        assertThat(result, Matchers.greaterThan(0));
    }

    @Test
    public void testAddNoteToDeal() throws DatabaseException {
        Note note = noteDao.getNoteById(NOTE5);
        Deal deal = dealDao.getDealById(DEAL5);
        int result = noteDao.addNoteToDeal(note.getId(), deal.getId());
        assertThat(result, Matchers.greaterThan(0));
    }

    @Override
    protected IDataSet getSpecificDataSet() throws DataSetException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(NOTE_TEST_DATA_XML);
        ReplacementDataSet contactDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(resourceAsStream));
        contactDataSet.addReplacementObject("[null]", null);
        return contactDataSet;
    }
}
