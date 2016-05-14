package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.*;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.PhoneType;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.becomejavasenior.model.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.core.Field;
import org.postgresql.core.Oid;

public class ContactDaoImpl extends CommonDao implements ContactDao {
    private static final Logger LOGGER = LogManager.getLogger(ContactDaoImpl.class);

    private static final String TABLE_NAME = "contact";
    private static final Field FIELD_ID = new Field("id", Oid.INT4);
    private static final Field FIELD_NAME = new Field("name", Oid.TEXT);
    private static final Field FIELD_PHONE = new Field("phone", Oid.TEXT);
    private static final Field FIELD_EMAIL = new Field("email", Oid.TEXT);
    private static final Field FIELD_SKYPE = new Field("skype", Oid.TEXT);
    private static final Field FIELD_POSITION = new Field("position", Oid.TEXT);
    private static final Field FIELD_RESPONSIBLE = new Field("responsible_id", Oid.INT4);
    private static final Field FIELD_PHONE_TYPE = new Field("phone_type_id", Oid.INT4);
    private static final Field FIELD_COMPANY_ID = new Field("company_id", Oid.INT4);
    private static final Field FIELD_CREATED_BY = new Field("created_by", Oid.INT4);
    private static final Field FIELD_DATE_CREATE = new Field("date_create", Oid.DATE);
    private static final Field FIELD_DELETED = new Field("deleted", Oid.BOOL);
    private static final Field FIELD_DATE_MODIFY = new Field("date_modify", Oid.DATE);
    private static final Field FIELD_USER_MODIFY = new Field("user_modify_id", Oid.INT4);

    private static final String ADD_CONTACT_TO_DEAL = "INSERT INTO deal_contact (deal_id, contact_id) VALUES (?, ?)";

    private static final String FIND_CONTACTS_BY_COMPANY_ID = "SELECT * FROM contact WHERE company_id = ?";

    private static final String FIND_CONTACTS_BY_DEAL_ID = "SELECT contact.* FROM contact INNER JOIN deal_contact ON " +
            "contact.id = deal_contact.contact_id WHERE deal_contact.deal_id = ?";

    private static final String GET_CONTACT_FOR_TASK = "SELECT contact.* " +
            "FROM contact INNER JOIN task ON contact.id = task.contact_id WHERE task.id = ?";
    private static final String CREATE_CONTACT_FOR_DEAL = "INSERT INTO deal_contact (contact_id, deal_id) VALUES (?, ?)";

    private UserDao userDao;
    private CompanyDao companyDao;


    public ContactDaoImpl(DataSource dataSource) {
        super(dataSource, TABLE_NAME,
                new ArrayList<>(Arrays.asList(FIELD_ID, FIELD_NAME, FIELD_PHONE, FIELD_EMAIL, FIELD_SKYPE, FIELD_POSITION, FIELD_RESPONSIBLE,
                        FIELD_PHONE_TYPE, FIELD_COMPANY_ID, FIELD_CREATED_BY, FIELD_DATE_CREATE, FIELD_DELETED, FIELD_DATE_MODIFY, FIELD_USER_MODIFY)));
        FIELD_ID.setAutoIncrement(true);

        userDao = new UserDaoImpl(dataSource);
        companyDao = new CompanyDaoImpl(dataSource);
    }

    @Override
    public int create(Contact contact) throws DatabaseException {
        int key;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSql().create(), Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getPhone());
            preparedStatement.setString(3, contact.getEmail());
            preparedStatement.setString(4, contact.getSkype());
            preparedStatement.setString(5, contact.getPosition());
            preparedStatement.setInt(6, contact.getResponsibleUser().getId());
            preparedStatement.setInt(7, contact.getPhoneType().ordinal() + 1);
            preparedStatement.setInt(8, contact.getCompany().getId());
            preparedStatement.setInt(9, contact.getResponsibleUser().getId());
            preparedStatement.setDate(10, new java.sql.Date(contact.getCreationDate().getTime()));
            preparedStatement.setBoolean(11, contact.getDeleted());

            if (contact.getModificationDate() == null) {
                preparedStatement.setNull(12, Types.INTEGER);
            } else {
                preparedStatement.setDate(12, new java.sql.Date(contact.getModificationDate().getTime()));
            }
            if (contact.getModifiedByUser() == null) {
                preparedStatement.setNull(13, Types.INTEGER);
            } else {
                preparedStatement.setInt(13, contact.getModifiedByUser().getId());
            }

            int affectedRows = preparedStatement.executeUpdate();
            LOGGER.debug("affectedRows = " + affectedRows);
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys();) {
                if (resultSet.next()) {
                    key = resultSet.getInt(1);
                    LOGGER.info("new contact id = " + key);
                } else {
                    LOGGER.error("Couldn't create the contact entity!");
                    throw new DatabaseException("Couldn't create the contact entity!");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Couldn't create the contact entity because of some SQL exception!", e);
            throw new DatabaseException(e.getMessage());
        }
        return key;
    }

    @Override
    public Contact getContactById(int id) throws DatabaseException {
        Contact contact = getContactByStatement(getSql().readById(), id);
        if (contact == null) {
            throw new DatabaseException("no result for id=" + id);
        }
        return contact;
    }

    @Override
    public int update(Contact contact) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSql().update())) {
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getPhone());
            preparedStatement.setString(3, contact.getEmail());
            preparedStatement.setString(4, contact.getSkype());
            preparedStatement.setString(5, contact.getPosition());
            preparedStatement.setInt(6, contact.getResponsibleUser().getId());
            preparedStatement.setInt(7, contact.getPhoneType().ordinal() + 1);
            preparedStatement.setInt(8, contact.getCompany().getId());
            preparedStatement.setInt(9, contact.getResponsibleUser().getId());
            preparedStatement.setDate(10, new java.sql.Date(contact.getCreationDate().getTime()));
            preparedStatement.setBoolean(11, contact.getDeleted());
            preparedStatement.setDate(12, new java.sql.Date(contact.getModificationDate().getTime()));
            preparedStatement.setInt(13, contact.getModifiedByUser().getId());
            preparedStatement.setInt(14, contact.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Updating a contact was failed. Error - ", e);
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int delete(Contact contact) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSql().delete())) {
            preparedStatement.setInt(1, contact.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Deleting a contact was failed. Error - ", e);
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Contact> findAll() throws DatabaseException {
        return getContactsListByStatement(getSql().readAll());
    }

    @Override
    public List<Contact> findAllByDealId(int id) throws DatabaseException {
        return getContactsListByStatement(FIND_CONTACTS_BY_DEAL_ID, id);
    }

    @Override
    public List<Contact> findAllByCompanyId(int id) throws DatabaseException {
        return getContactsListByStatement(FIND_CONTACTS_BY_COMPANY_ID, id);
    }

    @Override
    public int createContactForDeal(int dealId, Contact contact) throws DatabaseException {
        if (contact.getId() == 0) {
            int contactId = this.create(contact);
            contact.setId(contactId);
        }

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CONTACT_FOR_DEAL)) {
            preparedStatement.setInt(1, contact.getId());
            preparedStatement.setInt(2, dealId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Creating a deal_contact was failed. Error - ", e);
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int getCount() throws DatabaseException {
        int count = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSql().readCount());
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Counting contacts was failed. Error - {}", e);
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
        contact.setId(resultSet.getInt(FIELD_ID.getColumnName()));
        contact.setName(resultSet.getString(FIELD_NAME.getColumnName()));
        contact.setPhone(resultSet.getString(FIELD_PHONE.getColumnName()));
        contact.setEmail(resultSet.getString(FIELD_EMAIL.getColumnName()));
        contact.setSkype(resultSet.getString(FIELD_SKYPE.getColumnName()));
        contact.setPosition(resultSet.getString(FIELD_POSITION.getColumnName()));
        try {
            contact.setResponsibleUser(userDao.getUserById(resultSet.getInt(FIELD_RESPONSIBLE.getColumnName())));
        } catch (DatabaseException e) {
            LOGGER.error("Getting Responsible user failed. Error - ", e);
        }
        contact.setPhoneType(PhoneType.values()[resultSet.getInt(FIELD_PHONE_TYPE.getColumnName())]);

        try {
            contact.setCompany(companyDao.getCompanyById(resultSet.getInt(FIELD_COMPANY_ID.getColumnName())));
        } catch (DatabaseException e) {
            LOGGER.error("Getting Company for contract failed. Error - ", e);
        }
        contact.setCreationDate(resultSet.getDate(FIELD_DATE_CREATE.getColumnName()));
        contact.setDeleted(resultSet.getBoolean(FIELD_DELETED.getColumnName()));
        contact.setModificationDate(resultSet.getDate(FIELD_DATE_MODIFY.getColumnName()));
        int modifiedByUser = resultSet.getInt(FIELD_USER_MODIFY.getColumnName());
        if(modifiedByUser > 0 ) {
            try {
                contact.setModifiedByUser(userDao.getUserById(modifiedByUser));
            } catch (DatabaseException e) {
                LOGGER.error("Getting Modify User user failed. Error - ", e);
            }
        }

        return contact;
    }

    @Override
    public int addContactToDeal(int contactId, int dealId) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_CONTACT_TO_DEAL)) {
            preparedStatement.setInt(1, dealId);
            preparedStatement.setInt(2, contactId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("adding contact to deal was failed. Error - {}", e);
            throw new DatabaseException(e.getMessage());
        }
    }


    private List<Contact> getContactsListByStatement(String statement, int idClause) throws DatabaseException {
        List<Contact> contacts = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            if (idClause != 0) {
                preparedStatement.setInt(1, idClause);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                contacts.add(getContactFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Getting contacts was failed. Error - {}", e);
            throw new DatabaseException(e.getMessage());
        }
        return contacts;
    }

    private List<Contact> getContactsListByStatement(String statement) throws DatabaseException {
        return getContactsListByStatement(statement, 0);
    }

    private Contact getContactByStatement(String statement, int idClause) throws DatabaseException {
        List<Contact> contacts = getContactsListByStatement(statement, idClause);

        if (!contacts.isEmpty()) {
            return contacts.get(0);
        } else
            return null;

    }

}