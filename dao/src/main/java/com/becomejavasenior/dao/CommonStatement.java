package com.becomejavasenior.dao;

import org.postgresql.core.Field;
import org.postgresql.core.Oid;

import java.util.List;

public class CommonStatement {

    private String tableName;
    private List<Field> fieldList;
    private static final String SELECT = "SELECT ";
    private static final String UPDATE = "UPDATE ";
    private static final String INSERT = "INSERT INTO ";
    private static final String FROM = " FROM ";
    private static final String WHERE = " WHERE ";
    private static final String DELETED_FALSE = " deleted = FALSE ";
    private static final String DELETED_TRUE = " deleted = TRUE ";


    CommonStatement(String tableName, List<Field> fieldList) {
        this.tableName = tableName;
        this.fieldList = fieldList;
    }

    private String getAllFields() {
        return getFieldsStringByParams(true);
    }

    private String getAllNoIncrementFields() {
        return getFieldsStringByParams(false);
    }

    private String getFieldsStringByParams(boolean pk) {

        return getFieldsStringByParams(pk, false);
    }

    private String getFieldsStringByParams(boolean pk, boolean withBindVariable) {
        String fieldsStr = "";
        String bindStr = withBindVariable ? " = ?" : ""; //add '?' after every column if needed
        for (Field field : fieldList) {
            if (pk || !field.getAutoIncrement()) {
                if (fieldsStr.isEmpty()) {
                    fieldsStr = field.getColumnName() + bindStr;
                } else {
                    fieldsStr += ", " + field.getColumnName() + bindStr; //put comma before second and next fields
                }
            }
        }
        return fieldsStr;
    }

    private List<Field> getFieldsList() {
        return fieldList;
    }

    private Field getKeyField() {
        for (Field field : fieldList) {
            if (field.getAutoIncrement()) {
                return field;
            }
        }
        return new Field("id", Oid.INT4);
    }

    private String getTableName() {
        return tableName;
    }

    private String getBindVariable(int times) {
        String string = "";
        for (int i = 0; i < times; i++) {
            if (string.isEmpty()) {
                string = "?";
            } else {
                string += ", ?";
            }
        }
        return string;
    }

    private String getBindVariable() {
        return getBindVariable(1);
    }

    public String readById() {
        return SELECT + getAllFields() +
                FROM + getTableName() +
                WHERE + DELETED_FALSE +
                " AND " + getKeyField().getColumnName() + " = " + getBindVariable();
    }

    public String readAll() {
        return SELECT + getAllFields() +
                FROM + getTableName() +
                WHERE + DELETED_FALSE;
    }

    public String readCount() {
        return SELECT + " COUNT(*) " +
                FROM + getTableName() +
                WHERE + DELETED_FALSE;
    }

    public String readMaxId() {
        return SELECT + " MAX(" + getKeyField().getColumnName() + ") " +
                FROM  + getTableName();
    }

    public String readIdDeleted() {
        return SELECT + getKeyField().getColumnName() +
                FROM + getTableName() +
                WHERE + DELETED_TRUE;
    }


    public String create() {
        return INSERT + getTableName() +
                "(" + getAllNoIncrementFields() + ")" +
                " VALUES (" + getBindVariable(getFieldsList().size() - 1) + ")";
    }

    public String update() {
        return UPDATE + getTableName() +
                " SET " + getFieldsStringByParams(false, true) +
                WHERE + getKeyField().getColumnName() +
                " = " + getBindVariable();
    }

    public String delete() {
        return UPDATE + getTableName() +
                " SET" + DELETED_TRUE +
                WHERE + getKeyField().getColumnName() +
                " = " + getBindVariable();
    }

}
