package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.ContactDao;
import com.becomejavasenior.dao.DaoFactory;
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

    private final String READ_CONTACT= "SELECT id, name, phone, email, skype, position, phone_type_id, date_create, deleted FROM crm_helios.contact WHERE id=?";

    private final String CREATE_CONTACT = "INSERT INTO crm_helios.contact (name, phone, email, skype, position, responsible_id," +
            " phone_type_id, company_id, created_by, date_create, deleted) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final String UPDATE_CONTACT = "UPDATE crm_helios.contact SET name=?, phone=?, email=?, skype=?, position=?, " +
            "responsible_id=?, phone_type_id=?, company_id=?, created_by=?, date_create=?, deleted=? WHERE id=?";


    private final String DELETE_CONTACT = "DELETE FROM crm_helios.contact WHERE id=?";
    private final String FIND_ALL_CONTACTS = "SELECT id, name, phone, email, skype, position, phone_type_id, date_create, deleted FROM crm_helios.contact";
    private final String FIND_ALL_CONTACTS_BY_DEAL_ID = "SELECT * FROM crm_helios.contact JOIN crm_helios.deal_contact " +
            "ON contact.id = deal_contact.contact_id AND deal_id = ?";

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

    public Contact read(int id) throws DatabaseException {
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

    public void delete(Contact contact) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CONTACT);) {
            preparedStatement.setInt(1, contact.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

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
    public List<Contact> findAllByDealId(int id) throws DatabaseException {
        List<Contact> contacts = new ArrayList<Contact>();
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_CONTACTS_BY_DEAL_ID)){
            int i = 1;
            preparedStatement.setInt(i, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    Contact  contact =  new Contact();
                    contact.setId(resultSet.getInt(1));
                    contact.setName(resultSet.getString(2));
                    contact.setPhone(resultSet.getString(3));
                    contact.setEmail(resultSet.getString(4));
                    contact.setSkype(resultSet.getString(5));
                    contact.setPosition(resultSet.getString(6));
                    contact.setPhoneType(PhoneType.values()[resultSet.getInt(8)]);
                    contact.setCreationDate(resultSet.getDate(11));
                   //contact.setDeleted(resultSet.getBoolean(12));
                    contacts.add(contact);
                }
            }catch (SQLException e) {
                throw new DatabaseException(e.getMessage());
            }
        }catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return contacts;
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