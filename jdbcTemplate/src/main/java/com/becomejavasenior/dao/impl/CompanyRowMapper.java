package com.becomejavasenior.dao.impl;

import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.PhoneType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyRowMapper implements RowMapper<Company> {
    private static final Logger log = LogManager.getLogger(CompanyRowMapper.class);

    public CompanyRowMapper() {
    }

    @Override
    public Company mapRow(ResultSet resultSet, int i) throws SQLException {
        Company company = new Company();
        company.setId(resultSet.getInt("id"));
        company.setName(resultSet.getString("name"));
        company.setWeb(resultSet.getString("web"));
        company.setEmail(resultSet.getString("email"));
        company.setAddress(resultSet.getString("adress"));
        company.setPhone(resultSet.getString("phone"));
        company.setPhoneType(PhoneType.values()[resultSet.getInt("phone_type_id")-1]);
        company.setCreationDate(resultSet.getDate("date_create"));
        company.setDeleted(resultSet.getBoolean("deleted"));
        company.setModificationDate(resultSet.getDate("date_modify"));
        /*log.error("company row mapper result: " + company.toString());*/
        return company;
    }
}
