package com.becomejavasenior.service;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Company;

import java.util.List;

/**
 * Created by aivlev on 3/23/16.
 */
public interface CompanyService {

    void create(Company company) throws DatabaseException;

    Company getCompanyById(int id) throws DatabaseException;

    void update(Company company) throws DatabaseException;

    void delete(Company company) throws DatabaseException;

    List<Company> findAll() throws DatabaseException;

    int getCount() throws DatabaseException;

}
