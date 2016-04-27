package com.becomejavasenior.dao.impl;

import com.becomejavasenior.model.Company;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CompanyCreatePreparedStatementCreator implements PreparedStatementCreator {
    private Company company;
    private static final String CREATE_COMPANY = "INSERT INTO company (name,  responsible_id, web, email, adress, phone, phone_type_id," +
            "created_by, date_create, deleted, date_modify, user_modify_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    @Override
    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement(CREATE_COMPANY,
                Statement.RETURN_GENERATED_KEYS);
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
        preparedStatement.setDate(11, new java.sql.Date(company.getModificationDate().getTime()));
        preparedStatement.setInt(12, company.getModifiedByUser().getId());
        return preparedStatement;
    }

    public CompanyCreatePreparedStatementCreator(Company company) {
        this.company = company;
    }
}
