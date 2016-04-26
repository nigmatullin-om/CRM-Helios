package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.CompanyDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.PhoneType;
import com.becomejavasenior.model.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl extends CommonDao implements CompanyDao {

    private static final Logger LOGGER = LogManager.getLogger(CompanyDaoImpl.class);

    private static final String READ_COMPANY = "SELECT id, name, web, email, adress, phone, phone_type_id, date_create, deleted, date_modify, user_modify_id FROM company WHERE id=?";

    private static final String CREATE_COMPANY = "INSERT INTO company (name,  responsible_id, web, email, adress, phone, phone_type_id," +
            "created_by, date_create, deleted, date_modify, user_modify_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String CREATE_COMPANY_WITH_ID = "INSERT INTO company (name,  responsible_id, web, email, adress, phone, phone_type_id," +
            "created_by, date_create, deleted, date_modify, user_modify_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_COMPANY = "UPDATE company SET name=?, responsible_id=?, web=?, email=?, adress=?, phone=?, phone_type_id=?," +
            "created_by=?, date_create=?, deleted=?, date_modify=?, user_modify_id=? WHERE id=?";

    private static final String DELETE_COMPANY = "UPDATE company SET deleted=TRUE WHERE id=?";
    private static final String FIND_ALL_COMPANIES = "SELECT id, name, web, email, adress, phone, phone_type_id, date_create, deleted, date_modify, user_modify_id FROM company";
    private static final String GET_ALL_COMPANIES_COUNT = "SELECT count(*) FROM company";

    private static final String GET_COMPANY_FOR_TASK = "SELECT company.id, company.name, company.web, company.email,company. adress, company.phone," +
            " company.phone_type_id, company.date_create, company.deleted, company.date_modify, company.user_modify_id " +
            "FROM company INNER JOIN task ON company.id = task.company_id WHERE task.id = ?";

    private static final String GET_MAX_ID = "SELECT  max(id) FROM company";

    private JdbcTemplate jdbcTemplate;


    public CompanyDaoImpl(DataSource dataSource) {
        super(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int create(Company company) throws DatabaseException {
        jdbcTemplate.update(CREATE_COMPANY, new CompanyCreatePreparedStatementSetter(company));
        return 1;
    }

    @Override
    public Company getCompanyById(int id) throws DatabaseException {
        Company company = (Company)jdbcTemplate.queryForObject(READ_COMPANY, new Object[]{id}, new CompanyRowMapper());
        return company;
    }

    @Override
    public int update(Company company) throws DatabaseException {
        jdbcTemplate.update(UPDATE_COMPANY, new CompanyUpdatePreparedStatementSetter(company));
        return 1;
    }

    @Override
    public int getMaxId() throws DatabaseException {
        int maxId = jdbcTemplate.queryForObject(GET_MAX_ID, Integer.class);
        return maxId;
    }

    @Override
    public int delete(Company company) throws DatabaseException {
        jdbcTemplate.update(DELETE_COMPANY, new Object[]{company.getId()});
        return 1;
    }

    @Override
    public List<Company> findAll() throws DatabaseException {
        List<Company> companies = new ArrayList<>();
        companies = jdbcTemplate.query(FIND_ALL_COMPANIES, new CompanyRowMapper());
        return companies;
    }

    @Override
    public int getCount() throws DatabaseException {
        int count = jdbcTemplate.queryForObject(GET_ALL_COMPANIES_COUNT, Integer.class);
        return count;
    }

    @Override
    public Company getCompanyForTask(Task task) throws DatabaseException {
        Company company = (Company)jdbcTemplate.queryForObject(GET_COMPANY_FOR_TASK, new Object[]{task.getId()}, new CompanyRowMapper());
        return company;
    }

    @Override
    public int createWithId(Company company) throws DatabaseException {
        int key;
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new CompanyCreatePreparedStatementCreator(company), holder);
        return key = Integer.parseInt(holder.getKeys().get("id" ).toString());
    }
}