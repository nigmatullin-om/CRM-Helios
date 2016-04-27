package com.becomejavasenior.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class CommonDao {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    protected JdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }

    public CommonDao(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
