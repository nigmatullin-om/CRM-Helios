package com.becomejavasenior.dao;


import com.becomejavasenior.model.Deal;

import java.util.List;

public interface DealDao {
    void create(Deal deal) throws DatabaseException;
    Deal getDealById(int id) throws DatabaseException;
    void update(Deal deal) throws DatabaseException;
    void delete(Deal deal) throws DatabaseException;
    List<Deal> findAll() throws DatabaseException;
}
