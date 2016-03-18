package com.becomejavasenior.dao;

import com.becomejavasenior.Deal;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DealDaoImpl extends CommonDao implements DealDao {
    private final String READ_DEAL= "SELECT * FROM deal WHERE id=?";

    private final String CREATE_DEAL = "INSERT INTO deal (name, budget, responsible_id, stage_id, company_id, created_by, date_create) " +
                                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private final String UPDATE_DEAL = "UPDATE deal SET name=?, budget=?, responsible_id=?, stage_id=?," +
                                       "company_id=?, created_by=?, date_create=? WHERE id=?";

    private final String DELETE_DEAL = "DELETE FROM deal WHERE id=?";
    private final String FIND_ALL_DEALS = "SELECT * FROM deal";

    public int create(Deal deal) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(CREATE_DEAL);
            preparedStatement.setString(1, deal.getName());
            preparedStatement.setBigDecimal(2, deal.getBudget());
            preparedStatement.setInt(3, deal.getResponsibleUser().getId());
                /*preparedStatement.setInt(4, deal.getDealStage());*/
            preparedStatement.setInt(5, deal.getCompany().getId());
            preparedStatement.setInt(6, deal.getCreatedByUser().getId());
            preparedStatement.setDate(7, new java.sql.Date(deal.getCreationDate().getTime()));
        } catch (SQLException e) {
            exceptions.add(e);
        }
        finally {
            try {
                preparedStatement.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                connection.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            if(exceptions.size() != 0) {
                throw new DatabaseException(exceptions);
            }
        }
        return 1;
    }

    public Deal read(int id) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Deal deal = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(READ_DEAL);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                deal.setId(resultSet.getInt(1));
                deal.setName(resultSet.getString(2));
                deal.setBudget(resultSet.getBigDecimal(3));
                    /*deal.setDealStage(); 4 */
                deal.setCreationDate(resultSet.getDate(8));
            }
        } catch (SQLException e) {
            exceptions.add(e);
        }
        finally {
            try {
                resultSet.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                preparedStatement.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                connection.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            if(exceptions.size() != 0) {
                throw new DatabaseException(exceptions);
            }
        }
        return deal;
    }

    public boolean update(Deal deal) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_DEAL);
            preparedStatement.setString(1, deal.getName());
            preparedStatement.setBigDecimal(2, deal.getBudget());
            preparedStatement.setInt(3, deal.getResponsibleUser().getId());
                /*preparedStatement.setInt(4, deal.getDealStage());*/
            preparedStatement.setInt(5, deal.getCompany().getId());
            preparedStatement.setInt(6, deal.getCreatedByUser().getId());
            preparedStatement.setDate(7, new java.sql.Date(deal.getCreationDate().getTime()));
            preparedStatement.setInt(8, deal.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            exceptions.add(e);
        }
        finally {
            try {
                preparedStatement.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                connection.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            if(exceptions.size() != 0) {
                throw new DatabaseException(exceptions);
            }
        }
        return true;
    }

    public boolean delete(Deal deal) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_DEAL);
            preparedStatement.setInt(1, deal.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            exceptions.add(e);
        }
        finally {
            try {
                preparedStatement.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                connection.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            if(exceptions.size() != 0) {
                throw new DatabaseException(exceptions);
            }
        }
        return true;
    }

    public List<Deal> findAll() throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Deal> deals = new ArrayList<Deal>();
        try {
            preparedStatement = connection.prepareStatement(FIND_ALL_DEALS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Deal deal = new Deal();
                deal.setId(resultSet.getInt(1));
                deal.setName(resultSet.getString(2));
                deal.setBudget(resultSet.getBigDecimal(3));
                    /*deal.setDealStage(); 4 */
                deal.setCreationDate(resultSet.getDate(8));
                deals.add(deal);
            }
        } catch (SQLException e) {
            exceptions.add(e);
        }
        finally {
            try {
                resultSet.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                preparedStatement.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            try {
                connection.close();
            }
            catch(SQLException e) {
                exceptions.add(e);
            }
            if(exceptions.size() != 0) {
                throw new DatabaseException(exceptions);
            }
        }
        return deals;
    }

    public DealDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
