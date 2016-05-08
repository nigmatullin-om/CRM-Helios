package com.becomejavasenior.dao.impl;

import com.becomejavasenior.model.*;
import org.springframework.jdbc.core.RowMapper;

public class DealRowMapper {
    public static RowMapper<Deal> dealRowMapper() {

        return (resultSet, i) -> {
            Deal deal = new Deal();
            deal.setId(resultSet.getInt("id"));
            deal.setName(resultSet.getString("name"));
            deal.setBudget(resultSet.getBigDecimal("budget"));
            deal.setDealStage(DealStage.getDealStageById(resultSet.getInt("stage_id")));
            deal.setCreationDate(resultSet.getDate("date_create"));
            deal.setDeleted(resultSet.getBoolean("deleted"));
            return deal;
        };
    }
}
