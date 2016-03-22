package com.becomejavasenior.dao;


import com.becomejavasenior.dao.impl.DatabaseException;
import com.becomejavasenior.model.Tag;

import java.util.List;

public interface TagDao {
    public int create(Tag tag)throws DatabaseException;
    public Tag read(int id)throws DatabaseException;
    public boolean update(Tag tag)throws DatabaseException;
    public boolean delete(Tag tag)throws DatabaseException;
    public List<Tag> findAll()throws DatabaseException;
    public List<Tag> findAllByDealId(int id) throws DatabaseException;
}
