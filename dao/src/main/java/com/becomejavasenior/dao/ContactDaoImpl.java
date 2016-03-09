package com.becomejavasenior.dao;

import com.becomejavasenior.Contact;

import javax.sql.DataSource;
import java.util.List;

public class ContactDaoImpl extends AbstractDao implements ContactDao {
    public int create(Contact contact) {
        return 0;
    }

    public Contact read(int id) {
        return null;
    }

    public boolean update(Contact contact) {
        return false;
    }

    public boolean delete(Contact contact) {
        return false;
    }

    public List<Contact> findAll() {
        return null;
    }

    public ContactDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
