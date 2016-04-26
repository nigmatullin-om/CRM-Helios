package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.DealDao;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.Task;

import javax.sql.DataSource;
import java.util.List;

public class DealDaoImpl extends CommonDao implements DealDao {
    public DealDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Deal deal) throws DatabaseException {
        return 0;
    }

    @Override
    public Deal getDealById(int id) throws DatabaseException {
        return null;
    }

    @Override
    public int update(Deal deal) throws DatabaseException {
        return 0;
    }

    @Override
    public int updateDealContact(Deal deal) throws DatabaseException {
        return 0;
    }

    @Override
    public int getMaxId() throws DatabaseException {
        return 0;
    }

    @Override
    public int delete(Deal deal) throws DatabaseException {
        return 0;
    }

    @Override
    public List<Deal> findAll() throws DatabaseException {
        return null;
    }

    @Override
    public int countDealsWithTasks() throws DatabaseException {
        return 0;
    }

    @Override
    public int countDealsWithoutTasks() throws DatabaseException {
        return 0;
    }

    @Override
    public List<Deal> getDealsForContactById(Contact contact) throws DatabaseException {
        return null;
    }

    @Override
    public List<Deal> getDealsForCompanyById(Company company) throws DatabaseException {
        return null;
    }

    @Override
    public Deal getDealForTask(Task task) throws DatabaseException {
        return null;
    }

    @Override
    public int createWithId(Deal deal) throws DatabaseException {
        return 0;
    }

    @Override
    public int createDealForContact(int contactId, Deal deal) throws DatabaseException {
        return 0;
    }
}
