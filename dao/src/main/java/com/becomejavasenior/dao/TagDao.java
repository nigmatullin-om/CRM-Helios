package com.becomejavasenior.dao;


import com.becomejavasenior.model.Tag;

import java.util.List;

public interface TagDao {
    int create(Tag tag)throws DatabaseException;
    Tag getTagById(int id)throws DatabaseException;
    int update(Tag tag)throws DatabaseException;
    int delete(Tag tag)throws DatabaseException;
    List<Tag> findAll()throws DatabaseException;
    List<Tag> findAllByDealId(int id) throws DatabaseException;
    List<Tag> findAllByCompanyId(int id) throws DatabaseException;
    List<Tag> findAllByAllContacts() throws DatabaseException;
}
