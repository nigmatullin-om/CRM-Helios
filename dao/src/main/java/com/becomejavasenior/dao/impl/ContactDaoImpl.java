package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.ContactDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.PhoneType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDaoImpl extends CommonDao implements ContactDao {
    private static final String READ_CONTACT= "SELECT id, name, phone, email, skype, position, phone_type_id, date_create, deleted FROM contact WHERE id=?";

    private static final String CREATE_CONTACT = "INSERT INTO contact (name, phone, email, skype, position, responsible_id," +
            " phone_type_id, company_id, created_by, date_create, deleted) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_CONTACT = "UPDATE contact SET name=?, phone=?, email=?, skype=?, position=?, responsible_id=?," +
            " phone_type_id=?, company_id=?, created_by=?, date_create=?, deleted=? WHERE id=?";

    private static final String DELETE_CONTACT = "DELETE FROM contact WHERE id=?";
    private static final String FIND_ALL_CONTACTS = "SELECT id, name, phone, email, skype, position, phone_type_id, date_create, deleted FROM contact";
    private static final String GET_ALL_CONTACTS_COUNT = "SELECT count(*) FROM contact";

    @Override
    public void create(Contact contact) throws DatabaseException {
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CONTACT)) {
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getPhone());
            preparedStatement.setString(3, contact.getEmail());
            preparedStatement.setString(4, contact.getSkype());
            preparedStatement.setString(5, contact.getPosition());
            preparedStatement.setInt(6, contact.getResponsibleUser().getId());
            preparedStatement.setInt(7, contact.getPhoneType().ordinal());
            preparedStatement.setInt(8, contact.getCompany().getId());
            preparedStatement.setInt(9, contact.getResponsibleUser().getId());
            preparedStatement.setDate(10, new java.sql.Date(contact.getCreationDate().getTime()));
            preparedStatement.setBoolean(11, contact.getDeleted());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Contact getContactById(int id) throws DatabaseException {
        Contact contact = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_CONTACT);) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    contact = new Contact();
                    contact.setId(resultSet.getInt("id"));
                    contact.setName(resultSet.getString("name"));
                    contact.setPhone(resultSet.getString("phone"));
                    contact.setEmail(resultSet.getString("email"));
                    contact.setSkype(resultSet.getString("skype"));
                    contact.setPosition(resultSet.getString("position"));
                    contact.setPhoneType(PhoneType.values()[resultSet.getInt("phone_type_id")]);
                    contact.setCreationDate(resultSet.getDate("date_create"));
                    contact.setDeleted(resultSet.getBoolean("deleted"));
                }
            }
        } catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        }
        if (contact == null){
            throw new DatabaseException("no result for id=" + id);
        }
        return contact;
    }

    @Override
    public void update(Contact contact) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CONTACT);) {
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getPhone());
            preparedStatement.setString(3, contact.getEmail());
            preparedStatement.setString(4, contact.getSkype());
            preparedStatement.setString(5, contact.getPosition());
            preparedStatement.setInt(6, contact.getResponsibleUser().getId());
            preparedStatement.setInt(7, contact.getPhoneType().ordinal());
            preparedStatement.setInt(8, contact.getCompany().getId());
            preparedStatement.setInt(9, contact.getResponsibleUser().getId());
            preparedStatement.setDate(10, new java.sql.Date(contact.getCreationDate().getTime()));
            preparedStatement.setBoolean(11, contact.getDeleted());
            preparedStatement.setInt(12, contact.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void delete(Contact contact) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CONTACT);) {
            preparedStatement.setInt(1, contact.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Contact> findAll() throws DatabaseException {
        List<Contact> contacts = new ArrayList<Contact>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_CONTACTS);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setId(resultSet.getInt("id"));
                contact.setName(resultSet.getString("name"));
                contact.setPhone(resultSet.getString("phone"));
                contact.setEmail(resultSet.getString("email"));
                contact.setSkype(resultSet.getString("skype"));
                contact.setPosition(resultSet.getString("position"));
                contact.setPhoneType(PhoneType.values()[resultSet.getInt("phone_type_id")]);
                contact.setCreationDate(resultSet.getDate("date_create"));
                contact.setDeleted(resultSet.getBoolean("deleted"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return contacts;
    }

    @Override
    public int getCount() throws DatabaseException{
        int count = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CONTACTS_COUNT);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return count;
    }

    public ContactDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
        /*id serial NOT NULL,
        name character varying(255) NOT NULL,
        phone character varying(255),
        email character varying(255),
        skype character varying(255),
        "position" character varying(255) NOT NULL,
        responsible_id integer,
        phone_type_id integer NOT NULL,
        company_id integer,
        created_by integer NOT NULL,
        date_create timestamp without time zone,
        deleted boolean NOT NULL DEFAULT false,*/