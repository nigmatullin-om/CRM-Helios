package com.becomejavasenior.dao.unit;

import com.becomejavasenior.dao.*;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.Tag;
import com.becomejavasenior.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import java.util.Date;

public class TagDaoImplTest {
    private static final Logger log = LogManager.getLogger(TagDaoImplTest.class);

    private TagDao tagDao;
    private UserDao userDao;
    private DealDao dealDao;
    private final int USER_ID = 1;
    private final int DEAL_ID = 1;
    private final static String TAG_NAME = "test tag";

    @Before
    public void setUp(){
        DaoFactory daoFactory = new DaoFactoryImpl();
        tagDao = daoFactory.getTagDao();
        userDao = daoFactory.getUserDao();
        dealDao = daoFactory.getDealDao();
    }

    /*@Test
    public void testCreateAndGetTagByName() throws Exception {
        String random  = Double.toString(Math.random());
        String tagName = TAG_NAME + random;
        Tag tag = getTag(tagName);
        int id = tagDao.createWithId(tag);
        Assert.assertTrue(id != 0);

        Tag controlTag = tagDao.getTagByName(tagName);
        Assert.assertTrue(controlTag != null);
        Assert.assertTrue(controlTag.getName().equals(tagName));

        int result = tagDao.delete(controlTag);
        Assert.assertTrue(result > 0);
    }*/

    /*@Test
    public void testAddTagToDeal() throws DatabaseException {
        Deal deal = getDeal();
        String random  = Double.toString(Math.random());
        String tagName = TAG_NAME + random;
        Tag tag = getTag(tagName);
        int id = tagDao.createWithId(tag);
        tag.setId(id);
        int result = tagDao.addTagToDeal(id, deal.getId());
        Assert.assertTrue(result > 0);
    }*/

    private Deal getDeal(){
        Deal deal = null;
        try {
            deal = dealDao.getDealById(DEAL_ID);
        } catch (DatabaseException e) {
            log.error("error while reading deal from DB: " + e);
        }
        return deal;
    }


    private Tag getTag(String name){
        Tag tag = new Tag();
        tag.setName(name);
        tag.setCreationDate(new Date());
        tag.setCreatedByUser(getUser());

        return tag;
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
}