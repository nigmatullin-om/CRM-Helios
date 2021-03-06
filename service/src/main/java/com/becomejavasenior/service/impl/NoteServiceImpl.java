package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.NoteDao;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.Note;
import com.becomejavasenior.service.NoteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Resource
    private NoteDao noteDao;

    @Override
    public int create(Note note) throws DatabaseException {
        return noteDao.create(note);
    }

    @Override
    public Note getNoteById(int id) throws DatabaseException {
        return noteDao.getNoteById(id);
    }

    @Override
    public int update(Note note) throws DatabaseException {
        return noteDao.update(note);
    }

    @Override
    public int delete(Note note) throws DatabaseException {
        return noteDao.delete(note);
    }

    @Override
    public int getMaxId() throws DatabaseException {
        return noteDao.getMaxId();
    }

    @Override
    public List<Note> findAll() throws DatabaseException {
        return noteDao.findAll();
    }

    @Override
    public List<Note> findAllByDealId(int id) throws DatabaseException {
        return noteDao.findAllByDealId(id);
    }

    @Override
    public List<Note> findAllByCompanyId(int id) throws DatabaseException {
        return noteDao.findAllByCompanyId(id);
    }

    @Override
    public int createWithId(Note note) throws DatabaseException {
        return noteDao.createWithId(note);
    }

    @Override
    public int addNoteToDeal(Note note, Deal deal) throws DatabaseException {
        return noteDao.addNoteToDeal(note.getId(), deal.getId());
    }
}
