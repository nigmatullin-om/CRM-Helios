package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.CompanyDao;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.PhoneType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl extends CommonDao implements CompanyDao {
    private final String READ_COMPANY= "SELECT * FROM company WHERE id=?";

    private final String CREATE_COMPANY = "INSERT INTO company (name,  responsible_id, web, email, adress, phone, phone_type_id" +
                                            "created_by, date_create, deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final String UPDATE_COMPANY = "UPDATE company SET name=?, resposible_id=?, web=?, email=?, adress=?, phone=?, phone_type_id=?," +
                                            "created_by=?, date_create=?, deleted=? WHERE id=?";

    private final String DELETE_COMPANY = "DELETE FROM company WHERE id=?";
    private final String FIND_ALL_COMPANIES = "SELECT * FROM company";

    static final Logger log = LogManager.getLogger(CompanyDaoImpl.class);

    public CompanyDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    public int create(Company company) throws DatabaseException {
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
        } catch (SQLException e) {
            log.error("Couldn't create the company entity because of some SQL exception!");
            throw new DatabaseException(e.getMessage());
        }
        return 1;
    }

    public Company read(int id) throws DatabaseException {
        Company company = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_COMPANY);) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    company = new Company();
                    company.setId(resultSet.getInt(1));
                    company.setName(resultSet.getString(2));
                    company.setWeb(resultSet.getString(4));
                    company.setEmail(resultSet.getString(5));
                    company.setAddress(resultSet.getString(6));
                    company.setPhone(resultSet.getString(7));
                    company.setPhoneType(PhoneType.values()[resultSet.getInt(8)]);
                    company.setCreationDate(resultSet.getDate(10));
                    company.setDeleted(resultSet.getBoolean(11));
                }
            }
        } catch (SQLException e) {
            log.error("Couldn't read from company entity because of some SQL exception!");
            throw new DatabaseException(e.getMessage());
        }
        return company;
    }

    public boolean update(Company company) throws DatabaseException {
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
            log.error("Couldn't update the company entity because of some SQL exception!");
            throw new DatabaseException(e.getMessage());
        }
        return true;
    }

    public boolean delete(Company company) throws DatabaseException {
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMPANY);) {
            preparedStatement.setInt(1, company.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error("Couldn't delete the company entity because of some SQL exception!");
            throw new DatabaseException(e.getMessage());
        }
        return true;
    }

    public List<Company> findAll() throws DatabaseException {
        List<Company> companies = new ArrayList<Company>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_COMPANIES);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                Company company = new Company();
                company.setId(resultSet.getInt(1));
                company.setName(resultSet.getString(2));
                company.setWeb(resultSet.getString(4));
                company.setEmail(resultSet.getString(5));
                company.setAddress(resultSet.getString(6));
                company.setPhone(resultSet.getString(7));
                company.setPhoneType(PhoneType.values()[resultSet.getInt(8)] );
                company.setCreationDate(resultSet.getDate(10));
                company.setDeleted(resultSet.getBoolean(11));
                companies.add(company);
            }
        } catch (SQLException e) {
            log.error("Couldn't find from company entity because of some SQL exception!");
            throw new DatabaseException(e.getMessage());
        }
        return companies;
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