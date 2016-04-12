package com.becomejavasenior.service;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.Note;

public interface NoteService {
    int createWithId(Note note) throws DatabaseException;
    int addNoteToDeal(Note note, Deal deal) throws DatabaseException;
}
