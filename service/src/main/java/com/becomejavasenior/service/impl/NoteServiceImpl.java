package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.NoteDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.Note;
import com.becomejavasenior.service.NoteService;

public class NoteServiceImpl implements NoteService {
    private NoteDao noteDao;

    @Override
    public int createWithId(Note note) throws DatabaseException {
        return noteDao.createWithId(note);
    }

    @Override
    public int addNoteToDeal(Note note, Deal deal) throws DatabaseException {
        return noteDao.addNoteToDeal(note.getId(), deal.getId());
    }

    public NoteServiceImpl() {
        noteDao = new DaoFactoryImpl().getNoteDao();
    }
}
