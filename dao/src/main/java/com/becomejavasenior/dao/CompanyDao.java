package com.becomejavasenior.dao;

import com.becomejavasenior.Company;

import java.util.List;

public interface CompanyDao {
    public int create(Company company) throws DatabaseException;
    public Company read(int id) throws DatabaseException;
    public boolean update(Company company) throws DatabaseException;
    public boolean delete(Company company) throws DatabaseException;
    public List<Company> findAll() throws DatabaseException;
}
