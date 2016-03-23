package com.becomejavasenior.dao;


import com.becomejavasenior.model.Note;

import java.util.List;

public interface NoteDao {
    void create(Note note) throws DatabaseException;
    Note getNoteById(int id) throws DatabaseException;
    void update(Note note) throws DatabaseException;
    void delete(Note note) throws DatabaseException;
    List<Note> findAll() throws DatabaseException;
}
