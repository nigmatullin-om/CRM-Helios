package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
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
    private final String READ_DEAL= "SELECT * FROM deal WHERE id=?";

    private final String CREATE_DEAL = "INSERT INTO deal (name, budget, responsible_id, stage_id, company_id, created_by, date_create, deleted) " +
                                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private final String UPDATE_DEAL = "UPDATE deal SET name=?, budget=?, responsible_id=?, stage_id=?," +
                                       "company_id=?, created_by=?, date_create=?, deleted=? WHERE id=?";

    private final String DELETE_DEAL = "DELETE FROM deal WHERE id=?";
    private final String FIND_ALL_DEALS = "SELECT * FROM deal";

    public int create(Deal deal) throws DatabaseException {
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
        } catch (SQLException e) {
            throw new  DatabaseException(e.getMessage());
        }
        return 1;
    }

    public Deal read(int id) throws DatabaseException {
        Deal deal = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_DEAL)) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()){
                    deal.setId(resultSet.getInt(1));
                    deal.setName(resultSet.getString(2));
                    deal.setBudget(resultSet.getBigDecimal(3));
                    deal.setDealStage(DealStage.values()[resultSet.getInt(4)]);
                    deal.setCreationDate(resultSet.getDate(8));
                    deal.setDeleted(resultSet.getBoolean(9));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return deal;
    }

    public boolean update(Deal deal) throws DatabaseException {
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
        return true;
    }

    public boolean delete(Deal deal) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DEAL);) {
            preparedStatement.setInt(1, deal.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return true;
    }

    public List<Deal> findAll() throws DatabaseException {
        List<Deal> deals = new ArrayList<Deal>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_DEALS);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                Deal deal = new Deal();
                deal.setId(resultSet.getInt(1));
                deal.setName(resultSet.getString(2));
                deal.setBudget(resultSet.getBigDecimal(3));
                deal.setDealStage(DealStage.values()[resultSet.getInt(4)]);
                deal.setCreationDate(resultSet.getDate(8));
                deal.setDeleted(resultSet.getBoolean(9));
                deals.add(deal);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return deals;
    }

    public DealDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}