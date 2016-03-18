package com.becomejavasenior.dao;

import com.becomejavasenior.Note;

import java.util.List;

public interface NoteDao {
    public int create(Note note) throws DatabaseException;
    public Note read(int id) throws DatabaseException;
    public boolean update(Note note) throws DatabaseException;
    public boolean delete(Note note) throws DatabaseException;
    public List<Note> findAll() throws DatabaseException;
}
