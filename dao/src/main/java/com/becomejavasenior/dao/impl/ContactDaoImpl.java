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

import com.becomejavasenior.model.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContactDaoImpl extends CommonDao implements ContactDao {

    private static final Logger LOGGER = LogManager.getLogger(ContactDaoImpl.class);

    private static final String READ_CONTACT = "SELECT id, name, phone, email, skype, position, phone_type_id, date_create, deleted FROM contact WHERE id=?";

    private static final String CREATE_CONTACT = "INSERT INTO contact (name, phone, email, skype, position, responsible_id," +
            " phone_type_id, company_id, created_by, date_create, deleted) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_CONTACT = "UPDATE contact SET name=?, phone=?, email=?, skype=?, position=?, responsible_id=?," +
            " phone_type_id=?, company_id=?, created_by=?, date_create=?, deleted=? WHERE id=?";

    private static final String DELETE_CONTACT = "DELETE FROM contact WHERE id=?";
    private static final String FIND_ALL_CONTACTS = "SELECT id, name, phone, email, skype, position, phone_type_id, date_create, deleted FROM contact";
    private static final String GET_ALL_CONTACTS_COUNT = "SELECT count(*) FROM contact";
    private static final String FIND_ALL_CONTACTS_BY_DEAL_ID = "SELECT * FROM contact JOIN deal_contact " +
            "ON contact.id = deal_contact.contact_id AND deal_id = ?";
    private static final String FIND_CONTACTS_BY_COMPANY_ID = "SELECT * FROM contact WHERE company_id = ?";

    private static final String GET_CONTACT_FOR_TASK = "SELECT contact.id, contact.name, contact.phone, contact.email, " +
            "contact.skype,  contact.position,  contact.phone_type_id,  contact.date_create,  contact.deleted " +
            "FROM contact INNER JOIN task ON contact.id = task.contact_id WHERE task.id = ?";

    public ContactDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Contact contact) throws DatabaseException {
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
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Creating a contact was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Contact getContactById(int id) throws DatabaseException {
        Contact contact = getContactByStatement(READ_CONTACT, id);
        if (contact == null) {
            throw new DatabaseException("no result for id=" + id);
        }
        return contact;
    }

    @Override
    public int update(Contact contact) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CONTACT)) {
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
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Updating a contact was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int delete(Contact contact) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CONTACT)) {
            preparedStatement.setInt(1, contact.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Deleting a contact was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Contact> findAll() throws DatabaseException {
        return getContactsListByStatement(FIND_ALL_CONTACTS);
    }

    @Override
    public List<Contact> findAllByDealId(int id) throws DatabaseException {
        return getContactsListByStatement(FIND_CONTACTS_BY_COMPANY_ID, id);
    }

    @Override
    public List<Contact> findAllByCompanyId(int id) throws DatabaseException {
        return getContactsListByStatement(FIND_CONTACTS_BY_COMPANY_ID, id);
    }


    @Override
    public int getCount() throws DatabaseException {
        int count = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CONTACTS_COUNT);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Counting contacts was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return count;
    }

    @Override
    public Contact getContactForTask(Task task) throws DatabaseException {

        return getContactByStatement(GET_CONTACT_FOR_TASK, task.getId());
    }

    private Contact getContactFromResultSet(ResultSet resultSet) throws SQLException {
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
        return contact;
    }

    private List<Contact> getContactsListByStatement(String statement, int idClause) throws DatabaseException {
        List<Contact> contacts = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.setInt(1, idClause);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                contacts.add(getContactFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Getting contacts was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return contacts;
    }

    private List<Contact> getContactsListByStatement(String statement) throws DatabaseException {
        return getContactsListByStatement(statement, 0);
    }

    private Contact getContactByStatement(String statement, int idClause) throws DatabaseException {
        List<Contact> contacts = getContactsListByStatement(statement, idClause);

        if(contacts.size() == 0) {
            return null;
        }
        else {
            return contacts.get(0);
        }
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