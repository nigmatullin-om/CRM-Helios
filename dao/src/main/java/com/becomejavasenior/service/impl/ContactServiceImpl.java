package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.ContactDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.service.ContactService;

/**
 * Created by aivlev on 3/23/16.
 */
public class ContactServiceImpl implements ContactService{

    private ContactDao contactDao;

    public ContactServiceImpl() {
        this.contactDao = new DaoFactoryImpl().getContactDao();
    }

    @Override
    public int getCount() throws DatabaseException {
        return contactDao.getCount();
    }
}
