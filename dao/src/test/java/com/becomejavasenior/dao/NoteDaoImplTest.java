package com.becomejavasenior.dao;

import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.Note;
import com.becomejavasenior.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class NoteDaoImplTest {
    private static final Logger log = LogManager.getLogger(NoteDaoImplTest.class);

    private UserDao userDao;
    private DealDao dealDao;
    private NoteDao noteDao;
    private final int USER_ID = 1;
    private final int DEAL_ID = 1;
    private final int NOTE_ID = 1;
    private final String  NOTE_TEXT = "test note";


    @Before
    public void setUp(){
        DaoFactory daoFactory = new DaoFactoryImpl();
        userDao = daoFactory.getUserDao();
        dealDao = daoFactory.getDealDao();
        noteDao = daoFactory.getNoteDao();
    }

    @Test
    public void testCreateWithId() throws Exception {
        Note note = getNoteWithText(NOTE_TEXT);
        int id = noteDao.createWithId(note);
        Assert.assertTrue(id > 0);

        Note controlNote = noteDao.getNoteById(id);
        Assert.assertNotNull(controlNote);
        Assert.assertEquals(NOTE_TEXT, controlNote.getText());

        int result  = noteDao.delete(controlNote);
        Assert.assertTrue(result > 0);
    }

    @Test
    public void testAddNoteToDeal() throws Exception {
        Deal deal = getDeal();
        Note note = getNote();
        int result = noteDao.addNoteToDeal(note.getId(), deal.getId());
        Assert.assertTrue(result > 0);
    }

    private Note getNoteWithText (String text){
        Note note = new Note();
        note.setText(text);
        note.setCreatedByUser(getUser());
        note.setCreationDate(new Date());
        return note;
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

    private Note getNote(){
        Note note = null;
        try {
            note = noteDao.getNoteById(NOTE_ID);
        } catch (DatabaseException e) {
            log.error("error while reading note from DB: " + e);
        }
        return note;
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