package com.becomejavasenior.dao;

import com.becomejavasenior.Company;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl extends CommonDao implements CompanyDao {
    private final String READ_COMPANY= "SELECT * FROM company WHERE id=?";

    private final String CREATE_COMPANY = "INSERT INTO company (name,  responsible_id, web, email, adress, phone, phone_type_id" +
                                            "created_by, date_create) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final String UPDATE_COMPANY = "UPDATE company SET name=?, resposible_id=?, web=?, email=?, adress=?, phone=?, phone_type_id=?," +
                                            "created_by=?, date_create=? WHERE id=?";

    private final String DELETE_COMPANY = "DELETE FROM company WHERE id=?";
    private final String FIND_ALL_COMPANY = "SELECT * FROM company";

    public CompanyDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    public int create(Company company) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(CREATE_COMPANY);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setInt(2, company.getResponsibleUser().getId());
            preparedStatement.setString(3, company.getWeb());
            preparedStatement.setString(4, company.getEmail());
            preparedStatement.setString(5, company.getAddress());
            preparedStatement.setString(6, company.getPhone());
            /*preparedStatement.setInt(7, company.getPhoneType().);*/
            preparedStatement.setInt(8, company.getCreatedByUser().getId());
            preparedStatement.setDate(9, new java.sql.Date(company.getCreationDate().getTime()));
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

    public Company read(int id) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Company company = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(READ_COMPANY);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                company = new Company();
                company.setId(resultSet.getInt(1));
                company.setName(resultSet.getString(2));
                company.setWeb(resultSet.getString(4));
                company.setEmail(resultSet.getString(5));
                company.setAddress(resultSet.getString(6));
                company.setPhone(resultSet.getString(7));
                    /*company.setPhoneType(resultSet.getInt(8));*/
                company.setCreationDate(resultSet.getDate(10));
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
        return company;
    }

    public boolean update(Company company) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_COMPANY);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setInt(2, company.getResponsibleUser().getId());
            preparedStatement.setString(3, company.getWeb());
            preparedStatement.setString(4, company.getEmail());
            preparedStatement.setString(5, company.getAddress());
            preparedStatement.setString(6, company.getPhone());
                /*preparedStatement.setInt(7, company.getPhoneType());*/
            preparedStatement.setInt(8, company.getCreatedByUser().getId());
            preparedStatement.setDate(9, new java.sql.Date(company.getCreationDate().getTime()));
            preparedStatement.setInt(10, company.getId());
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

    public boolean delete(Company company) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_COMPANY);
            preparedStatement.setInt(1, company.getId());
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

    public List<Company> findAll() throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Company> companies = new ArrayList<Company>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_COMPANY);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Company company = new Company();
                company.setId(resultSet.getInt(1));
                company.setName(resultSet.getString(2));
                company.setWeb(resultSet.getString(4));
                company.setEmail(resultSet.getString(5));
                company.setAddress(resultSet.getString(6));
                company.setPhone(resultSet.getString(7));
                    /*company.setPhoneType(resultSet.getInt(8));*/
                company.setCreationDate(resultSet.getDate(10));
                companies.add(company);
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
        return companies;
    }
}