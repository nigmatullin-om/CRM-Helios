package com.becomejavasenior.dao;


import com.becomejavasenior.dao.impl.DatabaseException;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Deal;

import java.util.List;

public interface DealDao {
    public int create(Deal deal) throws DatabaseException;
    public Deal read(int id) throws DatabaseException;
    public boolean update(Deal deal) throws DatabaseException;
    public boolean delete(Deal deal) throws DatabaseException;
    public List<Deal> findAll() throws DatabaseException;
    public List<Deal> getAllDealForContactById(Contact contact) throws DatabaseException;
    public List<Deal> getDealForCompanyById(Company company) throws DatabaseException;

}
