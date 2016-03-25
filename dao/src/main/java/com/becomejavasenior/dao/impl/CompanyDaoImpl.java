package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.CompanyDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.PhoneType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl extends CommonDao implements CompanyDao {
    private static final String READ_COMPANY= "SELECT id, name, web, email, adress, phone, phone_type_id, date_create, deleted FROM company WHERE id=?";

    private static final String CREATE_COMPANY = "INSERT INTO company (name,  responsible_id, web, email, adress, phone, phone_type_id" +
            "created_by, date_create, deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_COMPANY = "UPDATE company SET name=?, resposible_id=?, web=?, email=?, adress=?, phone=?, phone_type_id=?," +
            "created_by=?, date_create=?, deleted=? WHERE id=?";

    private static final String DELETE_COMPANY = "DELETE FROM company WHERE id=?";
    private static final String FIND_ALL_COMPANIES = "SELECT id, name, web, email, adress, phone, phone_type_id, date_create, deleted FROM company";
    private static final String GET_ALL_COMPANIES_COUNT = "SELECT count(*) FROM company";

    public CompanyDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(Company company) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_COMPANY)) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.setInt(2, company.getResponsibleUser().getId());
            preparedStatement.setString(3, company.getWeb());
            preparedStatement.setString(4, company.getEmail());
            preparedStatement.setString(5, company.getAddress());
            preparedStatement.setString(6, company.getPhone());
            preparedStatement.setInt(7, company.getPhoneType().ordinal());
            preparedStatement.setInt(8, company.getCreatedByUser().getId());
            preparedStatement.setDate(9, new java.sql.Date(company.getCreationDate().getTime()));
            preparedStatement.setBoolean(10, company.getDeleted());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Company getCompanyById(int id) throws DatabaseException {
        Company company = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_COMPANY);) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    company = new Company();
                    company.setId(resultSet.getInt("id"));
                    company.setName(resultSet.getString("name"));
                    company.setWeb(resultSet.getString("web"));
                    company.setEmail(resultSet.getString("email"));
                    company.setAddress(resultSet.getString("adress"));
                    company.setPhone(resultSet.getString("phone"));
                    company.setPhoneType(PhoneType.values()[resultSet.getInt("phone_type_id")]);
                    company.setCreationDate(resultSet.getDate("date_create"));
                    company.setDeleted(resultSet.getBoolean("deleted"));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        if (company == null){
            throw new DatabaseException("no result for id=" + id);
        }
        return company;
    }


    @Override
    public void update(Company company) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COMPANY);) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.setInt(2, company.getResponsibleUser().getId());
            preparedStatement.setString(3, company.getWeb());
            preparedStatement.setString(4, company.getEmail());
            preparedStatement.setString(5, company.getAddress());
            preparedStatement.setString(6, company.getPhone());
            preparedStatement.setInt(7, company.getPhoneType().ordinal());
            preparedStatement.setInt(8, company.getCreatedByUser().getId());
            preparedStatement.setDate(9, new java.sql.Date(company.getCreationDate().getTime()));
            preparedStatement.setBoolean(10, company.getDeleted());
            preparedStatement.setInt(11, company.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void delete(Company company) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMPANY);) {
            preparedStatement.setInt(1, company.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Company> findAll() throws DatabaseException {
        List<Company> companies = new ArrayList<Company>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_COMPANIES);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                Company company = new Company();
                company.setId(resultSet.getInt("id"));
                company.setName(resultSet.getString("name"));
                company.setWeb(resultSet.getString("web"));
                company.setEmail(resultSet.getString("email"));
                company.setAddress(resultSet.getString("adress"));
                company.setPhone(resultSet.getString("phone"));
                company.setPhoneType(PhoneType.values()[resultSet.getInt("phone_type_id")]);
                company.setCreationDate(resultSet.getDate("date_create"));
                company.setDeleted(resultSet.getBoolean("deleted"));
                companies.add(company);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return companies;
    }

    @Override
    public int getCount() throws DatabaseException{
        int count = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_COMPANIES_COUNT);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return count;
    }
}

    /*id serial NOT NULL,
    name character varying(255) NOT NULL,
    responsible_id integer,
    web character varying(255),
    email character varying(255) NOT NULL,
    adress character varying(255),
    phone character varying(45) NOT NULL,
    phone_type_id integer NOT NULL,
    created_by integer NOT NULL,
    date_create timestamp without time zone NOT NULL,
    deleted boolean NOT NULL DEFAULT false,*/