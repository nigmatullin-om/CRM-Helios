package com.becomejavasenior.dao;


import com.becomejavasenior.dao.impl.DatabaseException;
import com.becomejavasenior.model.Company;

import java.util.List;

public interface CompanyDao {
    void create(Company company) throws DatabaseException;
    Company read(int id) throws DatabaseException;
    void update(Company company) throws DatabaseException;
    void delete(Company company) throws DatabaseException;
    List<Company> findAll() throws DatabaseException;
}
