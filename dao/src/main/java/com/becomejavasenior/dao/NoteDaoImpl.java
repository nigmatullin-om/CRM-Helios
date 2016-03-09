package com.becomejavasenior.dao;

import com.becomejavasenior.Note;

import javax.sql.DataSource;
import java.util.List;

public class NoteDaoImpl extends AbstractDao implements NoteDao {
    public int create(Note note) {
        return 0;
    }

    public Note read(int id) {
        return null;
    }

    public boolean update(Note note) {
        return false;
    }

    public boolean delete(Note note) {
        return false;
    }

    public List<Note> findAll() {
        return null;
    }

    public NoteDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
