package com.becomejavasenior.dao.impl;

import com.becomejavasenior.model.Company;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CompanyCreatePreparedStatementSetter implements PreparedStatementSetter {
    private static final Logger LOGGER = LogManager.getLogger(CompanyCreatePreparedStatementSetter.class);
    private Company company;

    @Override
    public void setValues(PreparedStatement preparedStatement) throws SQLException {
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
    }

    public CompanyCreatePreparedStatementSetter(Company company) {
        this.company = company;
    }
}
