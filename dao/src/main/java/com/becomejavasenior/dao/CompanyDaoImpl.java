package com.becomejavasenior.dao;

import com.becomejavasenior.Company;

import javax.sql.DataSource;
import java.util.List;

public class CompanyDaoImpl extends AbstractDao implements CompanyDao {

    public CompanyDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    public int create(Company company) {
        return 0;
    }

    public Company read(int id) {
        return null;
    }

    public boolean update(Company company) {
        return false;
    }

    public boolean delete(Company company) {
        return false;
    }

    public List<Company> findAll() {
        return null;
    }
}
