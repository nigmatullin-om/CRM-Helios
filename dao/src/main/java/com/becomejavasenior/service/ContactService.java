package com.becomejavasenior.service;


import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Contact;

import java.util.List;

/**
 * Created by aivlev on 3/23/16.
 */
public interface ContactService {

    void create(Contact contact) throws DatabaseException;

    Contact getContactById(int id) throws DatabaseException;

    void update(Contact contact) throws DatabaseException;

    void delete(Contact contact) throws DatabaseException;

    List<Contact> findAll() throws DatabaseException;

    int getCount() throws DatabaseException;
}
