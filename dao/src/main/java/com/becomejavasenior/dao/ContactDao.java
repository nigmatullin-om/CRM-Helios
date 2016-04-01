package com.becomejavasenior.dao;


import com.becomejavasenior.model.Contact;

import java.util.List;

public interface ContactDao {

    int create(Contact contact) throws DatabaseException;
    Contact getContactById(int id) throws DatabaseException;
    int update(Contact contact) throws DatabaseException;
    int delete(Contact contact) throws DatabaseException;
    List<Contact> findAll() throws DatabaseException;
    int getCount() throws DatabaseException;
    List<Contact> findAllByDealId (int id) throws DatabaseException;
}
