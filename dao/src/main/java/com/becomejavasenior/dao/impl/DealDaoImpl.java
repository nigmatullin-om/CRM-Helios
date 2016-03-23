package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.DealDao;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.DealStage;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DealDaoImpl extends CommonDao implements DealDao {
    private static final String READ_DEAL= "SELECT id, name, budget, stage_id, date_create, deleted FROM deal WHERE id=?";

    private static final String CREATE_DEAL = "INSERT INTO deal (name, budget, responsible_id, stage_id, company_id, created_by, date_create, deleted) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_DEAL = "UPDATE deal SET name=?, budget=?, responsible_id=?, stage_id=?," +
            "company_id=?, created_by=?, date_create=?, deleted=? WHERE id=?";

    private static final String DELETE_DEAL = "DELETE FROM deal WHERE id=?";
    private static final String FIND_ALL_DEALS = "SELECT id, name, budget, stage_id, date_create, deleted FROM deal";
    private static final String COUNT_DEALS_WITH_TASKS = "Select count(*) from deal d WHERE d.id IN (Select t.id from task t)";
    private static final String COUNT_DEALS_WITHOUT_TASKS = "Select count(*) from deal d WHERE d.id NOT IN (Select t.id from task t)";

    @Override
    public void create(Deal deal) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_DEAL)) {
            preparedStatement.setString(1, deal.getName());
            preparedStatement.setBigDecimal(2, deal.getBudget());
            preparedStatement.setInt(3, deal.getResponsibleUser().getId());
            preparedStatement.setInt(4, deal.getDealStage().ordinal());
            preparedStatement.setInt(5, deal.getCompany().getId());
            preparedStatement.setInt(6, deal.getCreatedByUser().getId());
            preparedStatement.setDate(7, new java.sql.Date(deal.getCreationDate().getTime()));
            preparedStatement.setBoolean(8, deal.getDeleted());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new  DatabaseException(e.getMessage());
        }
    }

    @Override
    public Deal getDealById(int id) throws DatabaseException {
        Deal deal = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_DEAL)) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()){
                    deal = new Deal();
                    deal.setId(resultSet.getInt("id"));
                    deal.setName(resultSet.getString("name"));
                    deal.setBudget(resultSet.getBigDecimal("budget"));
                    deal.setDealStage(DealStage.values()[resultSet.getInt("stage_id")]);
                    deal.setCreationDate(resultSet.getDate("date_create"));
                    deal.setDeleted(resultSet.getBoolean("deleted"));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        if (deal == null){
            throw new DatabaseException("no result for id=" + id);
        }
        return deal;
    }

    @Override
    public void update(Deal deal) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DEAL);) {
            preparedStatement.setString(1, deal.getName());
            preparedStatement.setBigDecimal(2, deal.getBudget());
            preparedStatement.setInt(3, deal.getResponsibleUser().getId());
            preparedStatement.setInt(4, deal.getDealStage().ordinal());
            preparedStatement.setInt(5, deal.getCompany().getId());
            preparedStatement.setInt(6, deal.getCreatedByUser().getId());
            preparedStatement.setDate(7, new java.sql.Date(deal.getCreationDate().getTime()));
            preparedStatement.setBoolean(8, deal.getDeleted());
            preparedStatement.setInt(9, deal.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void delete(Deal deal) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DEAL);) {
            preparedStatement.setInt(1, deal.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Deal> findAll() throws DatabaseException {
        List<Deal> deals = new ArrayList<Deal>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_DEALS);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                Deal deal = new Deal();
                deal.setId(resultSet.getInt("id"));
                deal.setName(resultSet.getString("name"));
                deal.setBudget(resultSet.getBigDecimal("budget"));
                deal.setDealStage(DealStage.values()[resultSet.getInt("stage_id")]);
                deal.setCreationDate(resultSet.getDate("date_create"));
                deal.setDeleted(resultSet.getBoolean("deleted"));
                deals.add(deal);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return deals;
    }

    @Override
    public int countDealsWithTasks() throws DatabaseException {
        int count = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_DEALS_WITH_TASKS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return count;
    }

    @Override
    public int countDealsWithoutTasks() throws DatabaseException {
        int count = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_DEALS_WITHOUT_TASKS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return count;
    }

    public DealDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}