package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.CompanyDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.UserDao;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.DealStage;
import com.becomejavasenior.model.PhoneType;
import com.becomejavasenior.model.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.core.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class CompanyDaoImpl extends CommonDao implements CompanyDao {

    private static final Logger LOGGER = LogManager.getLogger(CompanyDaoImpl.class);

    private static final String TABLE_NAME = "company";
    private static final Field FIELD_ID = new Field("id", Oid.INT4);
    private static final Field FIELD_NAME = new Field("name", Oid.TEXT);
    private static final Field FIELD_RESPONSIBLE = new Field("responsible_id", Oid.INT4);
    private static final Field FIELD_WEB = new Field("web", Oid.TEXT);
    private static final Field FIELD_EMAIL = new Field("email", Oid.TEXT);
    private static final Field FIELD_ADDRESS = new Field("adress", Oid.TEXT);
    private static final Field FIELD_PHONE = new Field("phone", Oid.TEXT);
    private static final Field FIELD_PHONE_TYPE = new Field("phone_type_id", Oid.INT4);
    private static final Field FIELD_CREATED_BY = new Field("created_by", Oid.INT4);
    private static final Field FIELD_DATE_CREATE = new Field("date_create", Oid.DATE);
    private static final Field FIELD_DELETED = new Field("deleted", Oid.BOOL);
    private static final Field FIELD_DATE_MODIFY = new Field("date_modify", Oid.DATE);
    private static final Field FIELD_USER_MODIFY = new Field("user_modify_id", Oid.INT4);

    private static final String GET_COMPANY_FOR_TASK = "SELECT company.id, company.name, company.responsible_id, company.web, company.email,company.adress, company.phone," +
            " company.phone_type_id, company.created_by, company.date_create, company.deleted, company.date_modify, company.user_modify_id " +
            "FROM company INNER JOIN task ON company.id = task.company_id WHERE task.id = ?";
    private static final String GET_ID_COMPANIES_FOR_USERNAME = "SELECT company.id FROM company INNER JOIN person ON person.id = company.responsible_id WHERE person.name = ? AND company.deleted = false";
    private static final String GET_ID_COMPANIES_WITHOUT_TASKS = "SELECT id FROM company WHERE deleted = false AND NOT EXISTS( SELECT 1 FROM task WHERE task.company_id = company.id)";
    private static final String GET_ID_COMPANIES_WITHOUT_DEALS = "SELECT id FROM company WHERE id NOT IN (SELECT company_id FROM deal) AND deleted = false";
    private static final String GET_ID_COMPANIES_WITHOUT_OPEN_DEALS = "SELECT company.id FROM company LEFT JOIN deal ON company.id = deal.company_id AND deal.stage_id NOT IN(5,6) WHERE company.deleted = false AND deal.id IS NULL";
    private static final String GET_ID_COMPANIES_WITH_OUTDATED_TASKS = "SELECT company.id FROM company JOIN task ON company.id = task.company_id AND done=FALSE AND finish_date < now() AND company.deleted = false";
    private static final String GET_ID_COMPANIES_CREATED_BY_PERIOD = "SELECT id FROM company WHERE date_create > ? AND deleted = false";
    private static final String GET_ID_COMPANIES_MODIFIED_BY_PERIOD = "SELECT id FROM company WHERE date_modify > ? AND deleted = false";
    private static final String GET_ID_COMPANIES_FOR_TASK_BY_PERIOD = "SELECT company.id FROM company JOIN task ON company.id = task.company_id AND finish_date > now() AND finish_date < ? AND company.deleted = false";
    private static final String GET_ID_COMPANIES_FOR_TAG_NAME = "SELECT company.id FROM company JOIN tag_contact_company ON company.id = tag_contact_company.company_id " +
            "JOIN tag ON tag.id = tag_contact_company.tag_id WHERE tag.name = ? AND company.deleted = false";
    private static final String GET_ID_COMPANIES_FOR_STAGE_NAME = "SELECT company.id FROM company JOIN deal ON company.id = deal.company_id " +
            "JOIN stage ON stage.id = deal.stage_id WHERE stage.name = ? AND company.deleted = false";
    private static final String GET_ID_MODIFIED_COMPANIES = "SELECT company.id FROM company WHERE date_modify IS NOT NULL AND user_modify_id IS NOT NULL AND company.deleted = false";

    private static final String GET_COMPANY_FOR_DEAL = "SELECT company.id, company.name, company.responsible_id, company.web, company.email,company.adress, company.phone," +
            " company.phone_type_id, company.created_by, company.date_create, company.deleted, company.date_modify, company.user_modify_id " +
            "FROM company INNER JOIN deal ON company.id = deal.company_id WHERE deal.id = ?";

    UserDao userDao;

    public CompanyDaoImpl(DataSource dataSource) {
        super(dataSource, TABLE_NAME,
                new ArrayList<>(Arrays.asList(FIELD_ID, FIELD_NAME, FIELD_RESPONSIBLE, FIELD_WEB, FIELD_EMAIL, FIELD_ADDRESS, FIELD_PHONE,
                        FIELD_PHONE_TYPE, FIELD_CREATED_BY, FIELD_DATE_CREATE, FIELD_DELETED, FIELD_DATE_MODIFY, FIELD_USER_MODIFY)));
        FIELD_ID.setAutoIncrement(true);

        userDao = new UserDaoImpl(dataSource);
    }



    @Override
    public int create(Company company) throws DatabaseException {
        int key;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSql().create(), Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, company.getName());
            if (company.getResponsibleUser() != null) {
                preparedStatement.setInt(2, company.getResponsibleUser().getId());
            } else {
                preparedStatement.setNull(2, Types.INTEGER);
            }
            preparedStatement.setString(3, company.getWeb());
            preparedStatement.setString(4, company.getEmail());
            preparedStatement.setString(5, company.getAddress());
            preparedStatement.setString(6, company.getPhone());
            preparedStatement.setInt(7, company.getPhoneType().ordinal());
            preparedStatement.setInt(8, company.getCreatedByUser().getId());
            preparedStatement.setDate(9, new java.sql.Date(company.getCreationDate().getTime()));
            preparedStatement.setBoolean(10, company.getDeleted());

            if (company.getModificationDate() == null) {
                preparedStatement.setNull(11, Types.DATE);
            } else {
                preparedStatement.setDate(11, new java.sql.Date(company.getModificationDate().getTime()));
            }
            if (company.getModifiedByUser() == null) {
                preparedStatement.setNull(12, Types.INTEGER);
            } else {
                preparedStatement.setInt(12, company.getModifiedByUser().getId());
            }


            int affectedRows = preparedStatement.executeUpdate();
            LOGGER.debug("AffectedRows = " + affectedRows);
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys();) {
                if (resultSet.next()) {
                    key = resultSet.getInt(1);
                    LOGGER.info("New company id = " + key);
                } else {
                    LOGGER.error("Couldn't create the company entity!");
                    throw new DatabaseException("Couldn't create the company entity!");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Couldn't create the company entity because of some SQL exception!", e);
            throw new DatabaseException(e.getMessage());
        }
        return key;
    }

    @Override
    public Company getCompanyById(int id) throws DatabaseException {
        Company company = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSql().readById())) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    company = getCompanyFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Getting a company was failed. Error - ", e);
            throw new DatabaseException(e.getMessage());
        }
        if (company == null) {
            throw new DatabaseException("Company[id = " + id + "] not found");
        }
        return company;
    }

    @Override
    public int update(Company company) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSql().update())) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.setInt(2, company.getResponsibleUser().getId());
            preparedStatement.setString(3, company.getWeb());
            preparedStatement.setString(4, company.getEmail());
            preparedStatement.setString(5, company.getAddress());
            preparedStatement.setString(6, company.getPhone());
            preparedStatement.setInt(7, company.getPhoneType().ordinal());
            preparedStatement.setInt(8, company.getCreatedByUser().getId());
            preparedStatement.setDate(9, new java.sql.Date(company.getCreationDate().getTime()));
            preparedStatement.setBoolean(10, company.getDeleted());
            preparedStatement.setDate(11, new java.sql.Date(company.getModificationDate().getTime()));
            preparedStatement.setInt(12, company.getModifiedByUser().getId());
            preparedStatement.setInt(13, company.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Updating a company was failed. Error - ", e);
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int getMaxId() throws DatabaseException {
        int maxId = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSql().readMaxId())) {
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                maxId = resultSet.getInt("max");
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return maxId;
    }

    @Override
    public int delete(Company company) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSql().delete())) {
            preparedStatement.setInt(1, company.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Deleting a company was failed. Error - ", e);
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Company> findAll() throws DatabaseException {
        List<Company> companies = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSql().readAll());
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                companies.add(getCompanyFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Getting companies was failed. Error - ", e);
            throw new DatabaseException(e.getMessage());
        }
        return companies;
    }

    @Override
    public int getCount() throws DatabaseException {
        int count = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSql().readCount());
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Counting companies was failed. Error - ", e);
            throw new DatabaseException(e.getMessage());
        }
        return count;
    }

    @Override
    public Company getCompanyForTask(Task task) throws DatabaseException {
        return getCompanyForEntity(task.getId(), GET_COMPANY_FOR_TASK);
    }

    @Override
    public Company getCompanyForDeal(Deal deal) throws DatabaseException {
        return getCompanyForEntity(deal.getId(), GET_COMPANY_FOR_DEAL);
    }

    private Company getCompanyForEntity(int id, String query) throws DatabaseException {
        Company company;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    company = getCompanyFromResultSet(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Getting a company was failed. Error - ", e);
            throw new DatabaseException(e.getMessage());
        }
        return company;
    }

    private Company getCompanyFromResultSet(ResultSet resultSet) throws SQLException {
        Company company;
        company = new Company();
        company.setId(resultSet.getInt(FIELD_ID.getColumnName()));
        company.setName(resultSet.getString(FIELD_NAME.getColumnName()));
        try {
            company.setResponsibleUser(userDao.getUserById(resultSet.getInt(FIELD_RESPONSIBLE.getColumnName())));
        } catch (DatabaseException e) {
            LOGGER.error("Error getting Responsible user", e);
        }
        company.setWeb(resultSet.getString(FIELD_WEB.getColumnName()));
        company.setEmail(resultSet.getString(FIELD_EMAIL.getColumnName()));
        company.setAddress(resultSet.getString(FIELD_ADDRESS.getColumnName()));
        company.setPhone(resultSet.getString(FIELD_PHONE.getColumnName()));
        company.setPhoneType(PhoneType.values()[resultSet.getInt(FIELD_PHONE_TYPE.getColumnName())]);
        try {
            company.setCreatedByUser(userDao.getUserById(resultSet.getInt(FIELD_CREATED_BY.getColumnName())));
        } catch (DatabaseException e) {
            LOGGER.error("Error getting Created by user", e);
        }
        company.setCreationDate(resultSet.getDate(FIELD_DATE_CREATE.getColumnName()));
        company.setDeleted(resultSet.getBoolean(FIELD_DELETED.getColumnName()));
        company.setModificationDate(resultSet.getDate(FIELD_DATE_MODIFY.getColumnName()));
        int modifiedByUser = resultSet.getInt(FIELD_USER_MODIFY.getColumnName());
        if (modifiedByUser > 0 ) {
            try {
                company.setModifiedByUser(userDao.getUserById(modifiedByUser));
            } catch (DatabaseException e) {
                LOGGER.error("Error getting Modified user", e);
            }
        }

        return company;
    }


    private List<Integer> filterWithoutParameters(String filterQuery) throws DatabaseException {
        List<Integer> listIdCompanies = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(filterQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                listIdCompanies.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            LOGGER.error("Getting id companies was failed. Error - ", e);
            throw new DatabaseException(e.getMessage());
        }
        return listIdCompanies;
    }

    @Override
    public List<Integer> modified() throws DatabaseException {
        return filterWithoutParameters(GET_ID_MODIFIED_COMPANIES);
    }

    @Override
    public List<Integer> withoutTasks() throws DatabaseException {
        return filterWithoutParameters(GET_ID_COMPANIES_WITHOUT_TASKS);
    }

    @Override
    public List<Integer> withOutdatedTasks() throws DatabaseException {
        return filterWithoutParameters(GET_ID_COMPANIES_WITH_OUTDATED_TASKS);
    }

    @Override
    public List<Integer> markedDelete() throws DatabaseException {
        return filterWithoutParameters(getSql().readIdDeleted());
    }

    private List<Integer> filterByTimestamp(Timestamp period, String filterQuery) throws DatabaseException {
        List<Integer> listIdCompanies = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(filterQuery)) {
            preparedStatement.setTimestamp(1, period);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listIdCompanies.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            LOGGER.error("Getting id companies was failed. Error - ", e);
            throw new DatabaseException(e.getMessage());
        }
        return listIdCompanies;
    }

    @Override
    public List<Integer> byPeriod(Timestamp period, String createdOrModified) throws DatabaseException {
        switch (createdOrModified) {
            case "created":
                return filterByTimestamp(period, GET_ID_COMPANIES_CREATED_BY_PERIOD);
            case "modified":
                return filterByTimestamp(period, GET_ID_COMPANIES_MODIFIED_BY_PERIOD);
            default:
                DatabaseException e = new DatabaseException("The createdOrModified field has incorrect value[" + createdOrModified + "]");
                LOGGER.error(e);
                throw e;
        }
    }

    @Override
    public List<Integer> byTask(Timestamp byTask) throws DatabaseException {
        return filterByTimestamp(byTask, GET_ID_COMPANIES_FOR_TASK_BY_PERIOD);
    }

    private List<Integer> filterByName(String name, String filterQuery) throws DatabaseException {
        List<Integer> listIdCompanies = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(filterQuery)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listIdCompanies.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            LOGGER.error("Getting the id companies was failed. Error - ", e);
            throw new DatabaseException(e.getMessage());
        }
        return listIdCompanies;
    }

    @Override
    public List<Integer> byUser(String userName) throws DatabaseException {
        return filterByName(userName, GET_ID_COMPANIES_FOR_USERNAME);
    }

    @Override
    public List<Integer> byTag(String tagName) throws DatabaseException {
        return filterByName(tagName, GET_ID_COMPANIES_FOR_TAG_NAME);
    }

    @Override
    public List<Integer> byStage(String[] byStages) throws DatabaseException {
        HashSet<Integer> listIdCompanies = new HashSet<>();
        for (String stage : byStages) {
            switch (stage) {
                case "without deals":
                    listIdCompanies.addAll(filterWithoutParameters(GET_ID_COMPANIES_WITHOUT_DEALS));
                    break;
                case "without open deals":
                    listIdCompanies.addAll(filterWithoutParameters(GET_ID_COMPANIES_WITHOUT_OPEN_DEALS));
                    break;
                default:
                    listIdCompanies.addAll(filterByName(stage, GET_ID_COMPANIES_FOR_STAGE_NAME));
            }
        }
        return new ArrayList<>(listIdCompanies);
    }
}
