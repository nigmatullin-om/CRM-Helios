package com.becomejavasenior.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.core.Field;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class CommonDao {
    private static final Logger LOGGER = LogManager.getLogger(CommonDao.class);

    private DataSource dataSource;
    private CommonStatement sql;
    private Connection connection;

    protected CommonDao(DataSource dataSource, String tableName, List<Field> fieldList) {
        this.dataSource = dataSource;
        this.sql = new CommonStatement(tableName, fieldList);
    }

    protected CommonDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = dataSource.getConnection();
        }
        return this.connection;
    }

    protected CommonStatement getSql() {
        return sql;
    }

    protected DataSource getDataSource() {
        return dataSource;
    }


    protected void finalize() throws Throwable {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Couldn't close a connection. Error: ", e);
        }
        super.finalize();
    }

}
