package com.becomejavasenior.dao.impl;

import com.becomejavasenior.model.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;

public class RowMappers {
    public static RowMapper<Task> taskRowMapper() {

        return (resultSet, i) -> {
            Task task = new Task();

            int taskId = resultSet.getInt("id");
            task.setId(taskId);

            String taskName = resultSet.getString("name");
            task.setName(taskName);

            Timestamp dateCreate = resultSet.getTimestamp("date_create");
            task.setCreationDate(dateCreate);

            String description = resultSet.getString("description");
            task.setDescription(description);

            Timestamp finishDate = resultSet.getTimestamp("finish_date");
            task.setFinishDate(finishDate);

            int periodOrdinal = resultSet.getInt("period");
            Period period = Period.values()[periodOrdinal - 1];
            task.setPeriod(period);
            return task;
        };
    }

    public static RowMapper<Company> companyRowMapper() {
        return (resultSet, i) -> {
            Company company = new Company();
            company.setId(resultSet.getInt("id"));
            company.setName(resultSet.getString("name"));
            company.setWeb(resultSet.getString("web"));
            company.setEmail(resultSet.getString("email"));
            company.setAddress(resultSet.getString("adress"));
            company.setPhone(resultSet.getString("phone"));
            company.setPhoneType(PhoneType.values()[resultSet.getInt("phone_type_id") - 1]);
            company.setCreationDate(resultSet.getDate("date_create"));
            company.setDeleted(resultSet.getBoolean("deleted"));
            company.setModificationDate(resultSet.getDate("date_modify"));
        /*log.error("company row mapper result: " + company.toString());*/
            return company;
        };
    }
}
