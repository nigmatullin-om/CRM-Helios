package com.becomejavasenior.dao.integration;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.DealDao;
import com.becomejavasenior.dao.TagDao;
import com.becomejavasenior.dao.UserDao;
import com.becomejavasenior.dao.impl.DealDaoImpl;
import com.becomejavasenior.dao.impl.TagDaoImpl;
import com.becomejavasenior.dao.impl.UserDaoImpl;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.Tag;
import com.becomejavasenior.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.hamcrest.Matchers;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.InputStream;
import java.util.Date;

public class TagDaoTest extends AbstractTestDao {
    private static final Logger log = LogManager.getLogger(TagDaoTest.class);
    public static final String TAG_TEST_DATA_XML = "tagTestData.xml";
    private final int USER5 = 1;
    private final int DEAL5 = 5;
    private DealDao dealDao = new DealDaoImpl(getDataSource());
    private UserDao userDao = new UserDaoImpl(getDataSource());
    private TagDao tagDao = new TagDaoImpl(getDataSource());

    @Test
    public void testCreateAndGetTagByName() throws Exception{
        String tagName  = Double.toString(Math.random());
        User user = userDao.getUserById(USER5);
        Tag tag = new Tag();
        tag.setName(tagName);
        tag.setCreatedByUser(user);
        tag.setCreationDate(new Date());
        int id = tagDao.createWithId(tag);
        assertThat(id, Matchers.greaterThan(0));

        Tag controlTag = tagDao.getTagByName(tagName);
        assertThat(controlTag, Matchers.notNullValue());
        assertThat(controlTag.getId(), Matchers.greaterThan(0));
    }

    @Test
    public void testAddTagToDeal() throws DatabaseException {
        String tagName  = Double.toString(Math.random());
        User user = userDao.getUserById(USER5);
        Tag tag = new Tag();
        tag.setName(tagName);
        tag.setCreatedByUser(user);
        tag.setCreationDate(new Date());
        int id = tagDao.createWithId(tag);
        assertThat(id, Matchers.greaterThan(0));
        tag.setId(id);
        Deal deal = dealDao.getDealById(DEAL5);
        assertThat(deal, Matchers.notNullValue());

        int result = tagDao.addTagToDeal(tag.getId(), deal.getId());
        assertThat(result, Matchers.greaterThan(0));

    }

    @Override
    protected IDataSet getSpecificDataSet() throws DataSetException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(TAG_TEST_DATA_XML);
        ReplacementDataSet contactDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(resourceAsStream));
        contactDataSet.addReplacementObject("[null]", null);
        return contactDataSet;
    }
}
