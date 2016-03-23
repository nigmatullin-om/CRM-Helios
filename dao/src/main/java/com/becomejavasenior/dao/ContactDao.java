package com.becomejavasenior.dao;


import com.becomejavasenior.dao.impl.DatabaseException;
import com.becomejavasenior.model.Contact;

import java.util.List;

public interface ContactDao {
    void create(Contact contact) throws DatabaseException;
    Contact read(int id) throws DatabaseException;
    void update(Contact contact) throws DatabaseException;
    void delete(Contact contact) throws DatabaseException;
    List<Contact> findAll() throws DatabaseException;
    int getCount() throws DatabaseException;
}
