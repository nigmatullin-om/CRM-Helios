package com.becomejavasenior.dao;

import com.becomejavasenior.Contact;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDaoImpl extends CommonDao implements ContactDao {
    private final String READ_CONTACT= "SELECT * FROM contact WHERE id=?";

    private final String CREATE_CONTACT = "INSERT INTO contact (name, phone, email, skype, position, responsible_id," +
            " phone_type_id, company_id, created_by, date_create) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final String UPDATE_CONTACT = "UPDATE contact SET name=?, phone=?, email=?, skype=?, position=?, responsible_id=?," +
                                    " phone_type_id=?, company_id=?, created_by=?, date_create=? WHERE id=?";

    private final String DELETE_CONTACT = "DELETE FROM contact WHERE id=?";
    private final String FIND_ALL_CONTACT = "SELECT * FROM contact";

    public int create(Contact contact) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(CREATE_CONTACT);
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getPhone());
            preparedStatement.setString(3, contact.getEmail());
            preparedStatement.setString(4, contact.getSkype());
            preparedStatement.setString(5, contact.getPosition());
            preparedStatement.setInt(6, contact.getResponsibleUser().getId());
                /*preparedStatement.setInt(7, contact.getPhoneType());*/
            preparedStatement.setInt(8, contact.getCompany().getId());
            preparedStatement.setInt(9, contact.getResponsibleUser().getId());
                /*preparedStatement.setDate(10, new java.sql.Date(contact.getCreationDate().getTime()));*/
            preparedStatement.execute();
            preparedStatement.execute();
        } catch (SQLException e) {
            exceptions.add(e);
        }
        finally {
            try {
                preparedStatement.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                connection.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            if(exceptions.size() != 0) {
                throw new DatabaseException(exceptions);
            }
        }
        return 1;
    }

    public Contact read(int id) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Contact contact = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(READ_CONTACT);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                contact = new Contact();
                contact.setId(resultSet.getInt(1));
                contact.setName(resultSet.getString(2));
                contact.setPhone(resultSet.getString(3));
                contact.setEmail(resultSet.getString(4));
                contact.setSkype(resultSet.getString(5));
                contact.setPosition(resultSet.getString(6));
                /*contact.setPhoneType(); 8*/
                /*contact.setCreationDate(resultSet.getDate(11));*/
            }
        } catch (SQLException e){
            exceptions.add(e);
        }
        finally {
            try {
                resultSet.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                preparedStatement.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                connection.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            if(exceptions.size() != 0) {
                throw new DatabaseException(exceptions);
            }
        }
        return contact;


    }

    public boolean update(Contact contact) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_CONTACT);
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getPhone());
            preparedStatement.setString(3, contact.getEmail());
            preparedStatement.setString(4, contact.getSkype());
            preparedStatement.setString(5, contact.getPosition());
            preparedStatement.setInt(6, contact.getResponsibleUser().getId());
                /*preparedStatement.setInt(7, contact.getPhoneType());*/
            preparedStatement.setInt(8, contact.getCompany().getId());
            preparedStatement.setInt(9, contact.getResponsibleUser().getId());
                /*preparedStatement.setDate(10, new java.sql.Date(contact.getCreationDate().getTime()));*/
            preparedStatement.setInt(11, contact.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            exceptions.add(e);
        }
        finally {
            try {
                preparedStatement.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                connection.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            if(exceptions.size() != 0) {
                throw new DatabaseException(exceptions);
            }
        }
        return true;


    }

    public boolean delete(Contact contact) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_CONTACT);
            preparedStatement.setInt(1, contact.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            exceptions.add(e);
        }
        finally {
            try {
                preparedStatement.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                connection.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            if(exceptions.size() != 0) {
                throw new DatabaseException(exceptions);
            }
        }
        return true;


    }

    public List<Contact> findAll() throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Contact> contacts = new ArrayList<Contact>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_CONTACT);
            resultSet = preparedStatement.executeQuery();
            contacts = new ArrayList<Contact>();
            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setId(resultSet.getInt(1));
                contact.setName(resultSet.getString(2));
                contact.setPhone(resultSet.getString(3));
                contact.setEmail(resultSet.getString(4));
                contact.setSkype(resultSet.getString(5));
                contact.setPosition(resultSet.getString(6));
                    /*contact.setPhoneType(); 8*/
                    /*contact.setCreationDate(resultSet.getDate(11));*/
                contacts.add(contact);
            }
        } catch (SQLException e) {
            exceptions.add(e);
        }
        finally {
            try {
                resultSet.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                preparedStatement.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                connection.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            if(exceptions.size() != 0) {
                throw new DatabaseException(exceptions);
            }
        }
        return contacts;


    }

    public ContactDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
