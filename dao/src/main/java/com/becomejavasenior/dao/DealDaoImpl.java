package com.becomejavasenior.dao;

import com.becomejavasenior.Deal;

import javax.sql.DataSource;
import java.util.List;

public class DealDaoImpl extends AbstractDao implements DealDao {
    public int create(Deal deal) {
        return 0;
    }

    public Deal read(int id) {
        return null;
    }

    public boolean update(Deal deal) {
        return false;
    }

    public boolean delete(Deal deal) {
        return false;
    }

    public List<Deal> findAll() {
        return null;
    }

    public DealDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
