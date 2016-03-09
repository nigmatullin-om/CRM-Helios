package com.becomejavasenior.dao;

import com.becomejavasenior.Note;

import java.util.List;

public interface NoteDao {
    public int create(Note note);
    public Note read(int id);
    public boolean update(Note note);
    public boolean delete(Note note);
    public List<Note> findAll();
}
