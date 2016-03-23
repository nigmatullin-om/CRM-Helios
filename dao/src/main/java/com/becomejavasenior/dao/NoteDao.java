package com.becomejavasenior.dao;


import com.becomejavasenior.dao.impl.DatabaseException;
import com.becomejavasenior.model.Note;

import java.util.List;

public interface NoteDao {

    void create(Note note) throws DatabaseException;
    Note read(int id) throws DatabaseException;
    void update(Note note) throws DatabaseException;
    void delete(Note note) throws DatabaseException;
    List<Note> findAll() throws DatabaseException;
    List<Note> findAllByDealId(int id) throws DatabaseException;
}
