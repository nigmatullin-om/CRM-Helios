package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.DealDao;
import com.becomejavasenior.model.*;
import com.becomejavasenior.model.Deal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.core.Field;
import org.postgresql.core.Oid;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DealDaoImpl extends CommonDao implements DealDao {

    private static final Logger LOGGER = LogManager.getLogger(DealDaoImpl.class);

    private static final String TABLE_NAME = "deal";
    private static final Field FIELD_ID = new Field("id", Oid.INT4);
    private static final Field FIELD_NAME = new Field("name", Oid.TEXT);
    private static final Field FIELD_BUDGET = new Field("budget", Oid.MONEY);
    private static final Field FIELD_RESPONSIBLE = new Field("responsible_id", Oid.INT4);
    private static final Field FIELD_STAGE = new Field("stage_id", Oid.INT4);
    private static final Field FIELD_COMPANY_ID = new Field("company_id", Oid.INT4);
    private static final Field FIELD_CREATED_BY = new Field("created_by", Oid.INT4);
    private static final Field FIELD_DATE_CREATE = new Field("date_create", Oid.DATE);
    private static final Field FIELD_DELETED = new Field("deleted", Oid.BOOL);

    private static final String CREATE_RELATION_BETWEEN_CONTACT_AND_DEAL = "INSERT INTO deal_contact(deal_id, contact_id) " +
            "VALUES(?, ?)";

    private static final String COUNT_DEALS_WITH_TASKS = "SELECT count(*) FROM deal d WHERE d.id IN (SELECT t.id FROM task t)";
    private static final String COUNT_DEALS_WITHOUT_TASKS = "SELECT count(*) FROM deal d WHERE d.id NOT IN (SELECT t.id FROM task t)";

    private static final String FIND_ALL_DEAL_FOR_CONTACT = "SELECT id, name, budget,responsible_id, stage_id, company_id, date_create, created_by, deleted " +
            "FROM deal JOIN deal_contact ON deal.id = deal_contact.deal_id AND contact_id = ? AND deleted = FALSE ";

    private static final String FIND_DEAL_FOR_COMPANY = "SELECT id, name, budget,responsible_id, stage_id, company_id, date_create, created_by, deleted " +
            " FROM deal WHERE company_id=? AND deleted = FALSE ";

    private static final String GET_DEAL_FOR_TASK = "SELECT deal.id, deal.name, deal.budget, deal.responsible_id, deal.stage_id, deal.company_id, deal.created_by, deal.date_create, deal.deleted " +
            "FROM deal INNER JOIN task ON deal.id = task.deal_id WHERE task.id = ?";

    private static final String CREATE_DEAL_FOR_CONTACT = "INSERT INTO deal_contact (contact_id, deal_id) VALUES (?, ?)";

    public DealDaoImpl(DataSource dataSource) {
        super(dataSource, TABLE_NAME,
                new ArrayList<>(Arrays.asList(FIELD_ID, FIELD_NAME, FIELD_BUDGET, FIELD_RESPONSIBLE, FIELD_STAGE, FIELD_COMPANY_ID,
                        FIELD_CREATED_BY, FIELD_DATE_CREATE, FIELD_DELETED))
        );
        FIELD_ID.setAutoIncrement(true);

    }

    @Override
    public int create(Deal deal) throws DatabaseException {
        int key;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSql().create(), Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, deal.getName());
            preparedStatement.setBigDecimal(2, deal.getBudget());
            preparedStatement.setInt(3, deal.getResponsibleUser().getId());
            preparedStatement.setInt(4, DealStage.getIdForDealStage(deal.getDealStage()));
            if (deal.getCompany() != null) {
                preparedStatement.setInt(5, deal.getCompany().getId());
            } else {
                preparedStatement.setNull(5, Types.INTEGER);
            }
            if (deal.getCreatedByUser() != null) {
                preparedStatement.setInt(6, deal.getCreatedByUser().getId());
            } else {
                preparedStatement.setNull(6, Types.INTEGER);
            }
            preparedStatement.setDate(7, new java.sql.Date(deal.getCreationDate().getTime()));
            if (deal.getDeleted() != null) {
                preparedStatement.setBoolean(8, deal.getDeleted());
            } else {
                preparedStatement.setBoolean(8, false);
            }
            int affectedRows = preparedStatement.executeUpdate();
            LOGGER.debug("affectedRows = " + affectedRows);
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys();) {
                if (resultSet.next()) {
                    key = resultSet.getInt(1);
                    LOGGER.debug("new deal id = " + key);
                } else {
                    LOGGER.error("Couldn't create the deal entity!");
                    throw new DatabaseException("Couldn't create the deal entity!");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Couldn't create the deal entity because of some SQL exception!", e);
            throw new DatabaseException(e.getMessage());
        }
        return key;
    }

    @Override
    public Deal getDealById(int id) throws DatabaseException {
        Deal deal;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSql().readById())) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            deal = getDealByResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.error("Getting a deal was failed. Error - {}", e);
            throw new DatabaseException(e.getMessage());
        }
        if (deal == null) {
            throw new DatabaseException("no result for id=" + id);
        }
        return deal;
    }

    @Override
    public int update(Deal deal) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSql().update())) {
            preparedStatement.setString(1, deal.getName());
            preparedStatement.setBigDecimal(2, deal.getBudget());
            preparedStatement.setInt(3, deal.getResponsibleUser().getId());
            preparedStatement.setInt(4, DealStage.getIdForDealStage(deal.getDealStage()));
            preparedStatement.setInt(5, deal.getCompany().getId());
            preparedStatement.setInt(6, deal.getCreatedByUser().getId());
            preparedStatement.setDate(7, new java.sql.Date(deal.getCreationDate().getTime()));
            preparedStatement.setBoolean(8, deal.getDeleted());
            preparedStatement.setInt(9, deal.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Updating a deal was failed. Error - {}", e);
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int updateDealContact(Deal deal) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_RELATION_BETWEEN_CONTACT_AND_DEAL)) {
            preparedStatement.setInt(1, deal.getId());
            preparedStatement.setInt(2, deal.getContacts().get(0).getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Creating a deal was failed. Error - {}", e);
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int getMaxId() throws DatabaseException {
        int maxId = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSql().readMaxId())) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                maxId = resultSet.getInt("max");
            }
        } catch (SQLException e) {
            LOGGER.error("Getting Max deal id failed.", e);
        }
        return maxId;
    }

    @Override
    public int delete(Deal deal) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSql().delete())) {
            preparedStatement.setInt(1, deal.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Deleting a deal was failed. Error - {}", e);
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Deal> findAll() throws DatabaseException {
        List<Deal> deals = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSql().readAll());
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Deal deal = getDealByResultSet(resultSet);
                deals.add(deal);
            }
        } catch (SQLException e) {
            LOGGER.error("Getting deals was failed. Error - {}", e);
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
            LOGGER.error("Counting deals with tasks was failed. Error - {}", e);
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
            LOGGER.error("Counting deals without tasks was failed. Error - {}", e);
            throw new DatabaseException(e.getMessage());
        }
        return count;
    }

    @Override
    public List<Deal> getDealsForContactById(Contact contact) throws DatabaseException {
        List<Deal> deals = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_DEAL_FOR_CONTACT)) {
            preparedStatement.setInt(1, contact.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Deal deal = getDealByResultSet(resultSet);
                deals.add(deal);
            }
        } catch (SQLException e) {
            LOGGER.error("Getting deals for contact was failed. Error - {}", e);
            throw new DatabaseException(e.getMessage());
        }
        return deals;
    }

    @Override
    public List<Deal> getDealsForCompanyById(Company company) throws DatabaseException {
        List<Deal> deals = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_DEAL_FOR_COMPANY)) {
            preparedStatement.setInt(1, company.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Deal deal = getDealByResultSet(resultSet);
                deals.add(deal);
            }
        } catch (SQLException e) {
            LOGGER.error("Getting deals for company was failed. Error - {}", e);
            throw new DatabaseException(e.getMessage());
        }
        return deals;
    }

    @Override
    public Deal getDealForTask(Task task) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_DEAL_FOR_TASK)) {
            preparedStatement.setInt(1, task.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getDealByResultSet(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Getting a deal was failed. Error - {}", e);
            throw new DatabaseException(e.getMessage());
        }
        return null;
    }

    @Override
    public int createDealForContact(int contactId, Deal deal) throws DatabaseException {
        if (deal.getId() == 0) {
            int dealId = this.create(deal);
            deal.setId(dealId);
        }

        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_DEAL_FOR_CONTACT)) {
            preparedStatement.setInt(1, contactId);
            preparedStatement.setInt(2, deal.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Creating a deal_contact was failed. Error - {}", e);
            throw new DatabaseException(e.getMessage());
        }
    }

    private Deal getDealByResultSet(ResultSet resultSet) throws DatabaseException, SQLException {

        Deal deal = null;
        if(resultSet.getRow() != 0) {
            deal = new Deal();
            deal.setId(resultSet.getInt(FIELD_ID.getColumnName()));
            deal.setName(resultSet.getString(FIELD_NAME.getColumnName()));
            deal.setBudget(resultSet.getBigDecimal(FIELD_BUDGET.getColumnName()));
            deal.setDealStage(DealStage.getDealStageById(resultSet.getInt(FIELD_STAGE.getColumnName())));
            deal.setCreationDate(resultSet.getDate(FIELD_DATE_CREATE.getColumnName()));
            deal.setDeleted(resultSet.getBoolean(FIELD_DELETED.getColumnName()));
        }
        return deal;
    }

}