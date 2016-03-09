package com.becomejavasenior.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDao {
    private DataSource dataSource;

    private Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public AbstractDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
