package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.DealDao;
import com.becomejavasenior.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;

public class DealDaoImpl extends CommonDao implements DealDao {

    private static final Logger LOGGER = LogManager.getLogger(DealDaoImpl.class);

    private static final String READ_DEAL = "SELECT id, name, budget, responsible_id, stage_id, company_id, created_by, date_create, deleted FROM deal WHERE id=?";

    private static final String CREATE_DEAL = "INSERT INTO deal (name, budget, responsible_id, stage_id, company_id, created_by, date_create, deleted) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String CREATE_RELATION_BETWEEN_CONTACT_AND_DEAL = "INSERT INTO deal_contact(deal_id, contact_id) " +
            "VALUES(?, ?)";

    private static final String UPDATE_DEAL = "UPDATE deal SET name=?, budget=?, responsible_id=?, stage_id=?," +
            "company_id=?, created_by=?, date_create=?, deleted=? WHERE id=?";

    private static final String DELETE_DEAL = "UPDATE deal SET deleted=TRUE WHERE id=?";
    private static final String GET_MAX_ID = "SELECT MAX(id) FROM deal";
    private static final String FIND_ALL_DEALS = "SELECT id, name, budget, responsible_id, stage_id, company_id, created_by, date_create, deleted FROM deal";
    private static final String COUNT_DEALS_WITH_TASKS = "SELECT count(*) FROM deal d WHERE d.id IN (SELECT t.id FROM task t)";
    private static final String COUNT_DEALS_WITHOUT_TASKS = "SELECT count(*) FROM deal d WHERE d.id NOT IN (SELECT t.id FROM task t)";

    private final String FIND_ALL_DEAL_FOR_CONTACT = "SELECT id, name, budget,responsible_id, stage_id, company_id, date_create, created_by, deleted " +
            "FROM deal JOIN deal_contact ON deal.id = deal_contact.deal_id AND contact_id = ? AND deleted = FALSE ";

    private final String FIND_DEAL_FOR_COMPANY = "SELECT id, name, budget,responsible_id, stage_id, company_id, date_create, created_by, deleted " +
            " FROM deal WHERE company_id=? AND deleted = FALSE ";

    private static final String GET_DEAL_FOR_TASK = "SELECT deal.id, deal.name, deal.budget, deal.responsible_id, deal.stage_id, deal.company_id, deal.created_by, deal.date_create, deal.deleted " +
            "FROM deal INNER JOIN task ON deal.id = task.deal_id WHERE task.id = ?";

    private static final String CREATE_DEAL_FOR_CONTACT = "INSERT INTO deal_contact (contact_id, deal_id) VALUES (?, ?)";

    public DealDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Deal deal) throws DatabaseException {
        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement(CREATE_DEAL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, deal.getName());
            ps.setBigDecimal(2, deal.getBudget());
            ps.setInt(3, deal.getResponsibleUser().getId());
            ps.setInt(4, DealStage.getIdForDealStage(deal.getDealStage()));
            ps.setInt(5, deal.getCompany().getId());
            ps.setInt(6, deal.getCreatedByUser().getId());
            ps.setTimestamp(7, new Timestamp(deal.getCreationDate().getTime()));
            ps.setBoolean(8, deal.getDeleted());

            return ps;
        };
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int row = getJdbcTemplate().update(psc, keyHolder);

        if (row > 0)
            return (int)keyHolder.getKeys().get("id");
        else {
            throw new DatabaseException("Couldn't create the task entity!");
        }
    }

    @Override
    public Deal getDealById(int id) throws DatabaseException {
        try {
            return getJdbcTemplate().queryForObject(READ_DEAL, new Object[]{id}, DealRowMapper.dealRowMapper());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int update(Deal deal) throws DatabaseException {
        try {
            return getJdbcTemplate().update(UPDATE_DEAL, deal.getName(),
                                                         deal.getBudget(),
                                                         deal.getResponsibleUser().getId(),
                                                         DealStage.getIdForDealStage(deal.getDealStage()),
                                                         deal.getCompany().getId(),
                                                         deal.getCreatedByUser().getId(),
                                                         new Timestamp(deal.getCreationDate().getTime()),
                                                         deal.getDeleted(),
                                                         deal.getId());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int updateDealContact(Deal deal) throws DatabaseException {
        try {
            return getJdbcTemplate().update(CREATE_RELATION_BETWEEN_CONTACT_AND_DEAL, deal.getId(), deal.getContacts().get(0).getId());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int getMaxId() throws DatabaseException {
        try {
            return getJdbcTemplate().queryForObject(GET_MAX_ID, Integer.class);
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int delete(Deal deal) throws DatabaseException {
        try {
            return getJdbcTemplate().update(DELETE_DEAL, deal.getId());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Deal> findAll() throws DatabaseException {
        try {
            return getJdbcTemplate().query(FIND_ALL_DEALS, DealRowMapper.dealRowMapper());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int countDealsWithTasks() throws DatabaseException {
        try {
            return getJdbcTemplate().queryForObject(COUNT_DEALS_WITH_TASKS, Integer.class);
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int countDealsWithoutTasks() throws DatabaseException {
        try {
            return getJdbcTemplate().queryForObject(COUNT_DEALS_WITHOUT_TASKS, Integer.class);
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Deal> getDealsForContactById(Contact contact) throws DatabaseException {
        try {
            return getJdbcTemplate().query(FIND_ALL_DEAL_FOR_CONTACT, new Object[]{contact.getId()}, DealRowMapper.dealRowMapper());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Deal> getDealsForCompanyById(Company company) throws DatabaseException {
        try {
            return getJdbcTemplate().query(FIND_DEAL_FOR_COMPANY, new Object[]{company.getId()}, DealRowMapper.dealRowMapper());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Deal getDealForTask(Task task) throws DatabaseException {
        try {
            return getJdbcTemplate().queryForObject(GET_DEAL_FOR_TASK, new Object[]{task.getId()}, DealRowMapper.dealRowMapper());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int createWithId(Deal deal) throws DatabaseException {
        try {
            return create(deal);
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int createDealForContact(int contactId, Deal deal) throws DatabaseException {
        try {
            return getJdbcTemplate().update(CREATE_DEAL_FOR_CONTACT, contactId, deal.getId());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
