package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.CompanyDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.DealStage;
import com.becomejavasenior.model.PhoneType;
import com.becomejavasenior.model.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CompanyDaoImpl extends CommonDao implements CompanyDao {

    private static final Logger LOGGER = LogManager.getLogger(CompanyDaoImpl.class);

    private static final String READ_COMPANY = "SELECT id, name, web, email, adress, phone, phone_type_id, date_create, deleted, date_modify, user_modify_id FROM company WHERE id=?";

    private static final String CREATE_COMPANY = "INSERT INTO company (name,  responsible_id, web, email, adress, phone, phone_type_id," +
            "created_by, date_create, deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String CREATE_COMPANY_WITH_ID = "INSERT INTO company (name,  responsible_id, web, email, adress, phone, phone_type_id," +
            "created_by, date_create, deleted, date_modify, user_modify_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_COMPANY = "UPDATE company SET name=?, resposible_id=?, web=?, email=?, adress=?, phone=?, phone_type_id=?," +
            "created_by=?, date_create=?, deleted=?, date_modify=?, user_modify_id=? WHERE id=?";

    private static final String DELETE_COMPANY = "DELETE FROM company WHERE id=?";
    private static final String FIND_ALL_COMPANIES = "SELECT id, name, web, email, adress, phone, phone_type_id, date_create, deleted, date_modify, user_modify_id FROM company";
    private static final String GET_ALL_COMPANIES_COUNT = "SELECT count(*) FROM company";

    private static final String GET_COMPANY_FOR_TASK = "SELECT company.id, company.name, company.web, company.email,company. adress, company.phone," +
            " company.phone_type_id, company.date_create, company.deleted, company.date_modify, company.user_modify_id " +
            "FROM company INNER JOIN task ON company.id = task.company_id WHERE task.id = ?";
    private static final String GET_MAX_ID = "SELECT  max(id) FROM company";
    private static final String GET_ID_COMPANIES_FOR_USERNAME = "SELECT company.id FROM company INNER JOIN person ON person.id = company.responsible_id WHERE person.name = ? AND company.deleted = false";
    private static final String GET_ID_COMPANIES_WITHOUT_TASKS = "SELECT id FROM company WHERE id NOT IN (SELECT company_id FROM task WHERE company.id IS NOT NULL) AND deleted = false";
    private static final String GET_ID_COMPANIES_WITHOUT_DEALS = "SELECT id FROM company WHERE id NOT IN (SELECT company_id FROM deal) AND deleted = false";
    private static final String GET_ID_COMPANIES_WITHOUT_OPEN_DEALS = "SELECT id FROM company WHERE id IN (SELECT company_id FROM deal JOIN stage ON stage.id = deal.stage_id" +
                                                                      " WHERE stage.name = '" + DealStage.getDealStageById(4) + "' AND stage.name = '" + DealStage.getDealStageById(5) + "')" +
                                                                      " OR id NOT IN (SELECT company_id FROM deal) AND deleted = false";
    private static final String GET_ID_COMPANIES_WITH_OUTDATED_TASKS = "SELECT company.id FROM company JOIN task ON company.id = task.company_id AND done=FALSE AND finish_date < now() AND company.deleted = false";
    private static final String GET_ID_DELETED_COMPANIES = "SELECT id FROM company WHERE deleted=TRUE";
    private static final String GET_ID_COMPANIES_CREATED_BY_PERIOD = "SELECT id FROM company WHERE date_create > ? AND deleted = false";
    private static final String GET_ID_COMPANIES_MODIFIED_BY_PERIOD = "SELECT id FROM company WHERE date_modify > ? AND deleted = false";
    private static final String GET_ID_COMPANIES_FOR_TASK_BY_PERIOD = "SELECT company.id FROM company JOIN task ON company.id = task.company_id AND finish_date > now() AND finish_date < ? AND company.deleted = false";
    private static final String GET_ID_COMPANIES_FOR_TAGNAME = "SELECT company.id FROM company JOIN tag_contact_company ON company.id = tag_contact_company.company_id " +
            "JOIN tag ON tag.id = tag_contact_company.tag_id WHERE tag.name = ? AND company.deleted = false";
    private static final String GET_ID_COMPANIES_FOR_STAGENAME = "SELECT company.id FROM company JOIN deal ON company.id = deal.company_id " +
            "JOIN stage ON stage.id = deal.stage_id WHERE stage.name = ? AND company.deleted = false";
    private static final String GET_ID_MODIFIED_COMPANIES = "SELECT company.id FROM company WHERE date_modify IS NOT NULL AND user_modify_id IS NOT NULL AND company.deleted = false";

    public CompanyDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Company company) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_COMPANY)) {
            preparedStatement.setString(1, company.getName());
            if( company.getResponsibleUser()!=null){
                preparedStatement.setInt(2, company.getResponsibleUser().getId());
            }else {
                preparedStatement.setNull(2, Types.INTEGER);
            }
            preparedStatement.setString(3, company.getWeb());
            preparedStatement.setString(4, company.getEmail());
            preparedStatement.setString(5, company.getAddress());
            preparedStatement.setString(6, company.getPhone());
            if(company.getPhoneType()!=null){
                preparedStatement.setInt(7, company.getPhoneType().ordinal());
            }
            else{
                preparedStatement.setNull(7,Types.INTEGER);
            }
            if(company.getCreatedByUser()!=null){
                preparedStatement.setInt(8, company.getCreatedByUser().getId());
            }
            else {
                preparedStatement.setNull(8,Types.INTEGER);
            }
            if(company.getCreationDate()!=null){
                preparedStatement.setDate(9, new java.sql.Date(company.getCreationDate().getYear(),company.getCreationDate().getMonth(),
                        company.getCreationDate().getDay()));
            }
            else
            {
                preparedStatement.setNull(9,Types.DATE);
            }
            if(company.getDeleted()!=null){
                preparedStatement.setBoolean(10, company.getDeleted());
            }
            else {
                preparedStatement.setBoolean(10, false);
            }
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Creating a company was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Company getCompanyById(int id) throws DatabaseException {
        Company company = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_COMPANY)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    company = getCompanyForResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Getting a company was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        if (company == null) {
            throw new DatabaseException("no result for id=" + id);
        }
        return company;
    }

    @Override
    public int update(Company company) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COMPANY)) {
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
            LOGGER.error("Updating a company was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int getMaxId() throws DatabaseException {
        int maxId = 0;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_MAX_ID)){
            ResultSet resultSet  = preparedStatement.getResultSet();
            if(resultSet.next()){
                maxId = resultSet.getInt("max");
            }
        }catch (SQLException e) {
            LOGGER.error(new Object[]{e.getMessage()});
        }
        return maxId;
    }

    @Override
    public int delete(Company company) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMPANY)) {
            preparedStatement.setInt(1, company.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Deleting a company was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Company> findAll() throws DatabaseException {
        List<Company> companies = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_COMPANIES);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Company company = new Company();
                company.setId(resultSet.getInt("id"));
                company.setName(resultSet.getString("name"));
                company.setWeb(resultSet.getString("web"));
                company.setEmail(resultSet.getString("email"));
                company.setAddress(resultSet.getString("adress"));
                company.setPhone(resultSet.getString("phone"));
                company.setPhoneType(PhoneType.values()[resultSet.getInt("phone_type_id")]);
                company.setCreationDate(resultSet.getDate("date_create"));
                company.setDeleted(resultSet.getBoolean("deleted"));
                company.setModificationDate(resultSet.getDate("date_modify"));
                companies.add(company);
            }
        } catch (SQLException e) {
            LOGGER.error("Getting companies was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return companies;
    }

    @Override
    public int getCount() throws DatabaseException {
        int count = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_COMPANIES_COUNT);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Counting companies was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return count;
    }

    @Override
    public Company getCompanyForTask(Task task) throws DatabaseException {
        Company company;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_COMPANY_FOR_TASK)) {
            preparedStatement.setInt(1, task.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    company = getCompanyForResultSet(resultSet);
                }
                else {
                    return null;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Getting a company was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return company;
    }

    private Company getCompanyForResultSet(ResultSet resultSet) throws SQLException {
        Company company;
        company = new Company();
        company.setId(resultSet.getInt("id"));
        company.setName(resultSet.getString("name"));
        company.setWeb(resultSet.getString("web"));
        company.setEmail(resultSet.getString("email"));
        company.setAddress(resultSet.getString("adress"));
        company.setPhone(resultSet.getString("phone"));
        company.setPhoneType(PhoneType.values()[resultSet.getInt("phone_type_id")]);
        company.setCreationDate(resultSet.getDate("date_create"));
        company.setDeleted(resultSet.getBoolean("deleted"));
        company.setModificationDate(resultSet.getDate("date_modify"));
        return company;
    }

    @Override
    public int createWithId(Company company) throws DatabaseException {
        int key;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_COMPANY_WITH_ID, Statement.RETURN_GENERATED_KEYS)) {
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

            if (company.getModificationDate() == null){
                preparedStatement.setNull(11, Types.INTEGER);
            }
            else {
                preparedStatement.setDate(11, new java.sql.Date(company.getModificationDate().getTime()));
            }
            if (company.getModifiedByUser() == null){
                preparedStatement.setNull(12, Types.INTEGER);
            }
            else {
                preparedStatement.setInt(12, company.getModifiedByUser().getId());
            }

            int affectedRows = preparedStatement.executeUpdate();
            LOGGER.info("affectedRows = " + affectedRows);
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys();) {
                if (resultSet.next()){
                    key = resultSet.getInt(1);
                    LOGGER.info("new company id = " + key);
                }
                else {
                    LOGGER.error("Couldn't create the company entity!");
                    throw new DatabaseException("Couldn't create the company entity!");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Couldn't create the company entity because of some SQL exception!");
            throw new  DatabaseException(e.getMessage());
        }
        return key;
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
            LOGGER.error("Getting id companies was failed. Error - {}", new Object[]{e.getMessage()});
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
        return filterWithoutParameters(GET_ID_DELETED_COMPANIES);
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
        }
        catch (SQLException e) {
            LOGGER.error("Getting id companies was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return listIdCompanies;
    }

    @Override
    public List<Integer> byPeriod(Timestamp period, String createdOrModified) throws DatabaseException {
        switch (createdOrModified) {
            case "created": return filterByTimestamp(period, GET_ID_COMPANIES_CREATED_BY_PERIOD);
            case "modified": return filterByTimestamp(period, GET_ID_COMPANIES_MODIFIED_BY_PERIOD);
            default:
                try{
                    throw new DatabaseException();
                } catch (DatabaseException e) {
                    LOGGER.error("The createdOrModified field is incorrect. Error - {}", new Object[]{e.getMessage()});
                    throw e;
                }
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
            LOGGER.error("Getting the id companies was failed. Error - {}", new Object[]{e.getMessage()});
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
        return filterByName(tagName, GET_ID_COMPANIES_FOR_TAGNAME);
    }

    @Override
    public List<Integer> byStage(String[] byStages) throws DatabaseException {
        HashSet<Integer> listIdCompanies = new HashSet<>();
        for(String stage: byStages) {
            switch (stage) {
                case "without deals": listIdCompanies.addAll(filterWithoutParameters(GET_ID_COMPANIES_WITHOUT_DEALS)); break;
                case "without open deals": listIdCompanies.addAll(filterWithoutParameters(GET_ID_COMPANIES_WITHOUT_OPEN_DEALS)); break;
                default: listIdCompanies.addAll(filterByName(stage, GET_ID_COMPANIES_FOR_STAGENAME));
            }
        }
        return new ArrayList<>(listIdCompanies);
    }


}
