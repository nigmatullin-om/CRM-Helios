package com.becomejavasenior.service;


import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Deal;

import java.util.List;

/**
 * Created by aivlev on 3/23/16.
 */
public interface ContactService {

    int create(Contact contact) throws DatabaseException;

    Contact getContactById(int id) throws DatabaseException;

    int update(Contact contact) throws DatabaseException;

    int delete(Contact contact) throws DatabaseException;

    List<Contact> findAll() throws DatabaseException;

    int getCount() throws DatabaseException;

    int createWithId(Contact contact) throws DatabaseException;

    int addContactToDeal(Contact contact, Deal deal) throws DatabaseException;
}
