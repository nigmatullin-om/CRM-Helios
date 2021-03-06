package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.*;
import com.becomejavasenior.model.Note;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDaoImpl extends CommonDao implements NoteDao {

    private static final Logger LOGGER = LogManager.getLogger(NoteDaoImpl.class);

    private static final String READ_NOTE = "SELECT id, text, date_create,contact_id,deal_id,company_id FROM note WHERE id=?";
    private static final String CREATE_NOTE = "INSERT INTO note (text, created_by, date_create,contact_id,deal_id,company_id) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_NOTE = "UPDATE note SET text=?, created_by=?, date_create=? WHERE id=?";
    private static final String DELETE_NOTE = "DELETE FROM note WHERE id=?";
    private static final String FIND_ALL_NOTES = "SELECT id, text, date_create FROM note";
    private static final String FIND_ALL_NOTES_BY_DEAL_ID = "SELECT * FROM note WHERE deal_id = ?";
    private static final String ADD_NOTE_TO_DEAL = "UPDATE note SET deal_id=? WHERE id=? ";
    private static final String FIND_NOTES_BY_COMPANY_ID = "SELECT * FROM note WHERE company_id = ?";
    private static final String GET_MAX_ID = "SELECT MAX(id) FROM note";

    private ContactDao contactDao = new ContactDaoImpl(getDataSource());
    private CompanyDao companyDao = new CompanyDaoImpl(getDataSource());
    private DealDao dealDao = new DealDaoImpl(getDataSource());

    public NoteDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Note note) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NOTE)) {
            preparedStatement.setString(1, note.getText());
            if(note.getCreatedByUser()!=null){
                preparedStatement.setInt(2, note.getCreatedByUser().getId());
            }
            else{
                preparedStatement.setNull(2, Types.NULL);
            }
            preparedStatement.setDate(3, new java.sql.Date(note.getCreationDate().getYear(),note.getCreationDate().getMonth(),
                    note.getCreationDate().getDay()) );

            if(note.getContact()!=null){
                preparedStatement.setInt(4, note.getContact().getId());
            }else{
                preparedStatement.setNull(4,  Types.NULL);
            }

            if(note.getDeal()!=null){
                preparedStatement.setInt(5, note.getDeal().getId());
            }else{
                preparedStatement.setNull(5,  Types.NULL);
            }

            if(note.getCompany()!=null){
                preparedStatement.setInt(6,note.getCompany().getId());
            }else{
                preparedStatement.setNull(6, Types.NULL);
            }
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Creating a note was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Note getNoteById(int id) throws DatabaseException {
        Note note = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_NOTE)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    note = new Note();
                    note.setId(resultSet.getInt("id"));
                    note.setText(resultSet.getString("text"));
                    note.setCreationDate(resultSet.getDate("date_create"));

                    int contactId = resultSet.getInt("contact_id");
                    if(contactId > 0) {
                        note.setContact(contactDao.getContactById(contactId));
                    }
                    int companyId = resultSet.getInt("company_id");
                    if(companyId > 0) {
                        note.setCompany(companyDao.getCompanyById(companyId));
                    }
                    int dealId = resultSet.getInt("deal_id");
                    if(dealId > 0) {
                        note.setDeal(dealDao.getDealById(dealId));
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Getting a note was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        if (note == null) {
            throw new DatabaseException("no result for id=" + id);
        }
        return note;
    }

    @Override
    public int update(Note note) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NOTE)) {
            preparedStatement.setString(1, note.getText());
            preparedStatement.setInt(2, note.getCreatedByUser().getId());
            preparedStatement.setDate(3, new java.sql.Date(note.getCreationDate().getTime()));
            preparedStatement.setInt(4, note.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Updating a note was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int delete(Note note) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_NOTE)) {
            preparedStatement.setInt(1, note.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Deleting a note was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int getMaxId() throws DatabaseException {
        int maxId = 0;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_MAX_ID)){
            ResultSet resultSet  = preparedStatement.executeQuery();
            if(resultSet.next()){
                maxId = resultSet.getInt("max");
            }
        }catch (SQLException e) {
            LOGGER.error(new Object[]{e.getMessage()});
        }
        return maxId;
    }

    @Override
    public List<Note> findAll() throws DatabaseException {
        List<Note> notes = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_NOTES);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Note note = new Note();
                note.setId(resultSet.getInt("id"));
                note.setText(resultSet.getString("text"));
                note.setCreationDate(resultSet.getDate("date_create"));
                notes.add(note);
            }
        } catch (SQLException e) {
            LOGGER.error("Getting notes was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return notes;
    }

    @Override
    public List<Note> findAllByDealId(int id) throws DatabaseException {
        List<Note> notes = new ArrayList<>();
      //  DaoFactoryImpl daoFactory = new DaoFactoryImpl();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_NOTES_BY_DEAL_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Note note = new Note();
                note.setId(resultSet.getInt("id"));
                note.setText(resultSet.getString("text"));
                note.setCreationDate(resultSet.getDate(7));
          //      note.setCreatedByUser(daoFactory.getUserDao().getUserById(resultSet.getInt("created_by")));
                notes.add(note);
            }
        } catch (SQLException e) {
            LOGGER.error("Getting notes was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return notes;
    }

    @Override
    public int createWithId(Note note) throws DatabaseException {
        int key;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NOTE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, note.getText());
            preparedStatement.setInt(2, note.getCreatedByUser().getId());
            preparedStatement.setDate(3, new java.sql.Date(note.getCreationDate().getTime()));

            if(note.getContact()!=null){
                preparedStatement.setInt(4, note.getContact().getId());
            }else{
                preparedStatement.setNull(4,  Types.NULL);
            }

            if(note.getDeal()!=null){
                preparedStatement.setInt(5, note.getDeal().getId());
            }else{
                preparedStatement.setNull(5,  Types.NULL);
            }

            if(note.getCompany()!=null){
                preparedStatement.setInt(6,note.getCompany().getId());
            }else{
                preparedStatement.setNull(6, Types.NULL);
            }

            int affectedRows = preparedStatement.executeUpdate();
            LOGGER.info("affectedRows = " + affectedRows);
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys();) {
                if (resultSet.next()){
                    key = resultSet.getInt(1);
                    LOGGER.info("new note id = " + key);
                }
                else {
                    LOGGER.error("Couldn't create the note entity!");
                    throw new DatabaseException("Couldn't create the note entity!");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Couldn't create the note entity because of some SQL exception!");
            throw new  DatabaseException(e.getMessage());
        }
        return key;
    }
    @Override
    public List<Note> findAllByCompanyId(int id) throws DatabaseException {
        List<Note> notes = new ArrayList<>();
       // DaoFactoryImpl daoFactory = new DaoFactoryImpl();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_NOTES_BY_COMPANY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Note note = new Note();
                note.setId(resultSet.getInt("id"));
                note.setText(resultSet.getString("text"));
                note.setCreationDate(resultSet.getDate(7));
           //     note.setCreatedByUser(daoFactory.getUserDao().getUserById(resultSet.getInt("created_by")));
                notes.add(note);
            }
        } catch (SQLException e) {
            LOGGER.error("Getting notes was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return notes;
    }

    @Override
    public List<Note> findAllByContactId(int id) throws DatabaseException {
        return null;
    }


    @Override
    public int addNoteToDeal(int noteId, int dealId) throws DatabaseException {
        int affectedRows;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_NOTE_TO_DEAL)) {
            preparedStatement.setInt(1, dealId);
            preparedStatement.setInt(2, noteId);
            affectedRows = preparedStatement.executeUpdate();
            LOGGER.info("affectedRows = " + affectedRows);
        } catch (SQLException e) {
            LOGGER.error("Couldn't create the note entity because of some SQL exception!");
            throw new  DatabaseException(e.getMessage());
        }
        return affectedRows;
    }
}
