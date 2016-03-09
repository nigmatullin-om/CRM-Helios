package com.becomejavasenior.dao;

import com.becomejavasenior.Company;

import java.util.List;

public interface CompanyDao {
    public int create(Company company);
    public Company read(int id);
    public boolean update(Company company);
    public boolean delete(Company company);
    public List<Company> findAll();
}
