package com.becomejavasenior.service.impl;


import com.becomejavasenior.dao.ContactDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.service.ContactService;

import java.util.List;

/**
 * Created by aivlev on 3/23/16.
 */
public class ContactServiceImpl implements ContactService {

    private ContactDao contactDao;

    public ContactServiceImpl() {
        this.contactDao = new DaoFactoryImpl().getContactDao();
    }

    @Override
    public void create(Contact contact) throws DatabaseException {
        contactDao.create(contact);
    }

    @Override
    public Contact getContactById(int id) throws DatabaseException {
        return contactDao.getContactById(id);
    }

    @Override
    public void update(Contact contact) throws DatabaseException {
        contactDao.update(contact);
    }

    @Override
    public void delete(Contact contact) throws DatabaseException {
        contactDao.delete(contact);
    }

    @Override
    public List<Contact> findAll() throws DatabaseException {
        return contactDao.findAll();
    }

    @Override
    public int getCount() throws DatabaseException {
        return contactDao.getCount();
    }
}
