package com.becomejavasenior.service;

import com.becomejavasenior.dao.ContactDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.service.impl.ContactServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ContactServiceTest {

    private static final int CONTACTS_COUNT = 2;

    @InjectMocks
    private ContactServiceImpl contactService;

    @Mock
    private ContactDao contactDao;

    @Mock
    private Contact contact;

    @Mock
    DaoFactoryImpl daoFactory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() throws Exception {
        when(contactDao.create(any(Contact.class))).thenReturn(1);
        contactService.create(contact);

        verify(contactDao).create(contact);

    }

    @Test
    public void testGetContactById() throws Exception {
        when(contactDao.getContactById(1)).thenReturn(contact);

        Contact resultContact = contactService.getContactById(1);

        verify(contactDao).getContactById(1);
        Assert.assertEquals(contact, resultContact);
    }

    @Test
    public void testUpdate() throws Exception {
        when(contactDao.update(any(Contact.class))).thenReturn(1);
        contactService.update(contact);

        verify(contactDao).update(contact);
    }

    @Test
    public void testDelete() throws Exception {
        when(contactDao.delete(any(Contact.class))).thenReturn(1);
        contactService.delete(contact);

        verify(contactDao).delete(contact);

    }

    @Test
    public void testFindAll() throws Exception {
        List<Contact> contacts = new LinkedList<>();

        Contact firstContact = new Contact();
        firstContact.setId(1);
        firstContact.setName("First Contact");

        contacts.add(firstContact);

        Contact secondContact = new Contact();
        secondContact.setId(2);
        secondContact.setName("Second Contact");

        contacts.add(secondContact);

        when(contactDao.findAll()).thenReturn(contacts);
        List<Contact> result = contactService.findAll();

        verify(contactDao).findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetCount() throws DatabaseException {
        when(contactDao.getCount()).thenReturn(CONTACTS_COUNT);
        int result = contactService.getCount();

        verify(contactDao).getCount();
        assertEquals(CONTACTS_COUNT, result);

    }
}