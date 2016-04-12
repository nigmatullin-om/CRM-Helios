package com.becomejavasenior.dao;


import com.becomejavasenior.model.Note;

import java.util.List;

public interface NoteDao {

    int create(Note note) throws DatabaseException;
    Note getNoteById(int id) throws DatabaseException;
    int update(Note note) throws DatabaseException;
    int delete(Note note) throws DatabaseException;
    List<Note> findAll() throws DatabaseException;
    List<Note> findAllByDealId(int id) throws DatabaseException;
    int createWithId(Note note) throws DatabaseException;
    public int addNoteToDeal(int noteId, int dealId) throws DatabaseException;
}
