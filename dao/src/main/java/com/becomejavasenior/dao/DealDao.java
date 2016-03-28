package com.becomejavasenior.dao;


import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Deal;

import java.util.List;

public interface DealDao {
    void create(Deal deal) throws DatabaseException;
    Deal getDealById(int id) throws DatabaseException;
    void update(Deal deal) throws DatabaseException;
    void delete(Deal deal) throws DatabaseException;
    List<Deal> findAll() throws DatabaseException;
    int countDealsWithTasks() throws DatabaseException;
    int countDealsWithoutTasks() throws DatabaseException;
    List<Deal> getDealsForContactById(Contact contact) throws DatabaseException;
    List<Deal> getDealsForCompanyById(Company company) throws DatabaseException;
}
