package com.becomejavasenior.dao;


import com.becomejavasenior.dao.impl.DatabaseException;
import com.becomejavasenior.model.Contact;

import java.util.List;

public interface ContactDao {
    public int create(Contact contact) throws DatabaseException;
    public Contact read(int id) throws DatabaseException;
    public boolean update(Contact contact) throws DatabaseException;
    public boolean delete(Contact contact) throws DatabaseException;
    public List<Contact> findAll() throws DatabaseException;

    int getCount() throws DatabaseException;
}
