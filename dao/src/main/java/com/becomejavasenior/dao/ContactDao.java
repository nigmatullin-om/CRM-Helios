package com.becomejavasenior.dao;

import com.becomejavasenior.Contact;

import java.util.List;

public interface ContactDao {
    public int create(Contact contact);
    public Contact read(int id);
    public boolean update(Contact contact);
    public boolean delete(Contact contact);
    public List<Contact> findAll();
}
