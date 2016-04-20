package com.becomejavasenior.service;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.Tag;

public interface TagService {
    int  createWithId(Tag tag) throws DatabaseException;
    int  addTagToDeal(Tag tag, Deal deal) throws DatabaseException;
    Tag getTagByName (String name) throws DatabaseException;
}
