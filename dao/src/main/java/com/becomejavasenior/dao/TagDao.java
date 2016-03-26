package com.becomejavasenior.dao;


import com.becomejavasenior.dao.impl.DatabaseException;
import com.becomejavasenior.model.Tag;

import java.util.List;

public interface TagDao {

    void create(Tag tag)throws DatabaseException;
    Tag read(int id)throws DatabaseException;
    void update(Tag tag)throws DatabaseException;
    void delete(Tag tag)throws DatabaseException;
    List<Tag> findAll()throws DatabaseException;
    List<Tag> findAllByDealId(int id) throws DatabaseException;

}
