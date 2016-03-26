package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DealDao;
import com.becomejavasenior.model.*;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.DealStage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DealDaoImpl extends CommonDao implements DealDao {

    private final String READ_DEAL= "SELECT id, name, budget,responsible_id, stage_id, company_id, date_create, created_by, deleted " +
            "FROM crm_helios.deal  WHERE id=? AND deleted = FALSE";

    private final String CREATE_DEAL = "INSERT INTO crm_helios.deal (id, name, budget, responsible_id, stage_id, company_id, created_by, date_create, deleted) " +
                                        "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)";

    private final String UPDATE_DEAL = "UPDATE crm_helios.deal SET name=?, budget=?, responsible_id=?, stage_id=?," +
                                       "company_id=?, created_by=?, date_create=?, deleted=? WHERE id=?";

    private final String DELETE_DEAL = "DELETE FROM crm_helios.deal WHERE id=?";

    private final String FIND_ALL_DEALS = "SELECT id, name, budget,responsible_id, stage_id, company_id, date_create, created_by, deleted " +
            " FROM crm_helios.deal WHERE deleted = FALSE ";

    private final String FIND_ALL_DEAL_FOR_CONTACT = "SELECT id, name, budget,responsible_id, stage_id, company_id, date_create, created_by, deleted " +
            "FROM crm_helios.deal JOIN crm_helios.deal_contact ON deal.id = deal_contact.deal_id AND contact_id = ? AND deleted = FALSE ";

    private final String FIND_DEAL_FOR_COMPANY = "SELECT id, name, budget,responsible_id, stage_id, company_id, date_create, created_by, deleted " +
            " FROM crm_helios.deal WHERE company_id=? AND deleted = FALSE ";

    DaoFactoryImpl  daoFactory;



    public DealDaoImpl(DataSource dataSource) {
        super(dataSource);
        daoFactory = new DaoFactoryImpl();
    }


    static final Logger log = LogManager.getLogger(DealDaoImpl.class);

    public void create(Deal deal) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_DEAL)) {
            preparedStatement.setInt(1,deal.getId());
            preparedStatement.setString(2, deal.getName());
            preparedStatement.setBigDecimal(3, deal.getBudget());
            preparedStatement.setInt(4, deal.getResponsibleUser().getId());
            preparedStatement.setInt(5, deal.getDealStage().ordinal());
            preparedStatement.setInt(6, deal.getCompany().getId());
            preparedStatement.setInt(7, deal.getCreatedByUser().getId());
            preparedStatement.setDate(8, new java.sql.Date(deal.getCreationDate().getTime()));
            preparedStatement.setBoolean(9, deal.getDeleted());
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error("Couldn't create the deal entity because of some SQL exception!");
            throw new  DatabaseException(e.getMessage());
        }
    }

    public Deal read(int id) throws DatabaseException {
        Deal deal;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_DEAL)) {
            int i = 1;
            preparedStatement.setInt(i,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            deal = getDealByResultSet(resultSet);
        } catch (SQLException e) {
            log.error("Couldn't read from company entity because of some SQL exception!");
            throw new DatabaseException(e.getMessage());
        }
        if (deal == null){
            throw new DatabaseException("no result for id=" + id);
        }
        return deal;
    }

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
            log.error("Couldn't update the deal entity because of some SQL exception!");
            throw new DatabaseException(e.getMessage());
        }
    }

    public void delete(Deal deal) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DEAL);) {
            preparedStatement.setInt(1, deal.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error("Couldn't delete the deal entity because of some SQL exception!");
            throw new DatabaseException(e.getMessage());
        }
    }

    public List<Deal> findAll() throws DatabaseException {
        List<Deal> deals = new ArrayList<Deal>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_DEALS);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                Deal deal = read(resultSet.getInt("id"));
                deals.add(deal);
            }
        } catch (SQLException e) {
            log.error("Couldn't find from deal entity because of some SQL exception!");
            throw new DatabaseException(e.getMessage());
        }
        return deals;
    }

    @Override
    public List<Deal> getDealsForContactById(Contact contact) throws DatabaseException {
        List<Deal> deals = new ArrayList<Deal>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_DEAL_FOR_CONTACT)){
            int i  = 1;
            preparedStatement.setInt(i,contact.getId());
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    Deal deal = read(resultSet.getInt("id"));
                    deals.add(deal);
                }
            } catch (SQLException e) {
                throw new DatabaseException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return deals;
    }

    @Override
    public List<Deal> getDealsForCompanyById(Company company) throws DatabaseException {
        List<Deal> deals =  new ArrayList<>();
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_DEAL_FOR_COMPANY)){
            int i  = 1;
            preparedStatement.setInt(i, company.getId());
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                Deal deal = read(resultSet.getInt(1));
                deals.add(deal);}
            }catch (SQLException e) {
                throw new DatabaseException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return  deals;
    }

    private Deal getDealByResultSet(ResultSet resultSet) throws DatabaseException, SQLException {
        List<Note> notes = new ArrayList<Note>();
        List<File> files = new ArrayList<File>();
        List<Tag> tags = new ArrayList<Tag>();
        List<Contact> contacts = new ArrayList<Contact>();

        Deal deal = new Deal();
        deal.setId(resultSet.getInt("id"));
        deal.setName(resultSet.getString("name"));
        deal.setBudget(resultSet.getBigDecimal("budget"));
        deal.setResponsibleUser(daoFactory.getUserDao().read(resultSet.getInt("responsible_id")));
        deal.setDealStage(DealStageImpl.getDealStageById(resultSet.getInt("stage_id")));
        deal.setCompany(daoFactory.getCompanyDao().read(resultSet.getInt("company_id")));
        deal.setCreatedByUser(daoFactory.getUserDao().read(resultSet.getInt("created_by")));
        deal.setCreationDate(resultSet.getDate("date_create"));
        deal.setDeleted(resultSet.getBoolean("deleted"));

        notes = daoFactory.getNoteDao().findAllByDealId(deal.getId());

        files = daoFactory.getFileDao().findAllByDealId(deal.getId());

        tags =  daoFactory.getTagDao().findAllByDealId(deal.getId());

        contacts = daoFactory.getContactDao().findAllByDealId(deal.getId());

        deal.setNotes(notes);
        deal.setFiles(files);
        deal.setTags(tags);
        deal.setContacts(contacts);
        return deal;
    }
}