package com.becomejavasenior.dao;

import org.postgresql.core.Field;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class CommonDao {
    private DataSource dataSource;
    private CommonStatement sql;


    protected CommonDao(DataSource dataSource, String tableName, List<Field> fieldList) {
        this.dataSource = dataSource;
        this.sql = new CommonStatement(tableName, fieldList);
    }

    protected CommonDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    protected CommonStatement getSql() {
        return sql;
    }

    protected DataSource getDataSource() {
        return dataSource;
    }

}
