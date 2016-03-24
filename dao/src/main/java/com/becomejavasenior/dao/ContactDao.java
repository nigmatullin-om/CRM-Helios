package com.becomejavasenior.dao;


import com.becomejavasenior.model.Contact;

import java.util.List;

public interface ContactDao {
    void create(Contact contact) throws DatabaseException;
    Contact getContactById(int id) throws DatabaseException;
    void update(Contact contact) throws DatabaseException;
    void delete(Contact contact) throws DatabaseException;
    List<Contact> findAll() throws DatabaseException;
    int getCount() throws DatabaseException;
}
