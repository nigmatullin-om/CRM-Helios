package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.impl.ContactDaoImpl;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.service.ContactService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.sql.DataSource;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by aivlev on 3/25/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ContactServiceImpl.class)
public class ContactServiceImplTest {

    private static final int CONTACTS_COUNT = 2;

    private ContactService contactService;

    @Mock
    private ContactDaoImpl contactDao;

    @Mock
    private Contact contact;

    @Mock
    DaoFactoryImpl daoFactory;

    @Before
    public void init() throws Exception {
        whenNew(DaoFactoryImpl.class).withNoArguments().thenReturn(daoFactory);
        when(daoFactory.getContactDao()).thenReturn(contactDao);
        contactService = new ContactServiceImpl();
    }

    @Test
    public void testCreate() throws Exception {
        doNothing().when(contactDao, "create", any(Contact.class));
        contactService.create(contact);

        verify(contactDao).create(contact);

    }

    @Test
    public void testGetContactById() throws Exception {
        when(contactDao.getContactById(1)).thenReturn(contact);

        Contact resultContact = contactService.getContactById(1);

        verify(contactDao).getContactById(1);
        assertEquals(contact, resultContact);
    }

    @Test
    public void testUpdate() throws Exception {
        doNothing().when(contactDao, "update", any(Contact.class));
        contactService.update(contact);

        verify(contactDao).update(contact);
    }

    @Test
    public void testDelete() throws Exception {
        doNothing().when(contactDao, "delete", any(Contact.class));
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