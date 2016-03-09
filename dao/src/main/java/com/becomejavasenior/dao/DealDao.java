package com.becomejavasenior.dao;

import com.becomejavasenior.Deal;

import java.util.List;

public interface DealDao {
    public int create(Deal deal);
    public Deal read(int id);
    public boolean update(Deal deal);
    public boolean delete(Deal deal);
    public List<Deal> findAll();
}
