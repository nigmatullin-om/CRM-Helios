package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.ContactDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Task;

import javax.sql.DataSource;
import java.util.List;

public class ContactDaoImpl extends CommonDao implements ContactDao {

    public ContactDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Contact contact) throws DatabaseException {
        return 0;
    }

    @Override
    public Contact getContactById(int id) throws DatabaseException {
        return null;
    }

    @Override
    public int update(Contact contact) throws DatabaseException {
        return 0;
    }

    @Override
    public int delete(Contact contact) throws DatabaseException {
        return 0;
    }

    @Override
    public List<Contact> findAll() throws DatabaseException {
        return null;
    }

    @Override
    public int getCount() throws DatabaseException {
        return 0;
    }

    @Override
    public List<Contact> findAllByDealId(int id) throws DatabaseException {
        return null;
    }

    @Override
    public Contact getContactForTask(Task task) throws DatabaseException {
        return null;
    }

    @Override
    public int createWithId(Contact contact) throws DatabaseException {
        return 0;
    }

    @Override
    public int addContactToDeal(int contactId, int dealId) throws DatabaseException {
        return 0;
    }

    @Override
    public List<Contact> findAllByCompanyId(int id) throws DatabaseException {
        return null;
    }

    @Override
    public int createContactForDeal(int dealId, Contact contact) throws DatabaseException {
        return 0;
    }
}
