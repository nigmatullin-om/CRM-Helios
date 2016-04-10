package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.NoteDao;
import com.becomejavasenior.model.Note;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteDaoImpl extends CommonDao implements NoteDao {

    private static final Logger LOGGER = LogManager.getLogger(NoteDaoImpl.class);

    private static final String READ_NOTE = "SELECT id, text, date_create FROM note WHERE id=?";
    private static final String CREATE_NOTE = "INSERT INTO note (text, created_by, date_create) " +
            "VALUES (?, ?, ?)";
    private static final String UPDATE_NOTE = "UPDATE note SET text=?, created_by=?, date_create=? WHERE id=?";
    private static final String DELETE_NOTE = "DELETE FROM note WHERE id=?";
    private static final String FIND_ALL_NOTES = "SELECT id, text, date_create FROM note";
    private static final String FIND_ALL_NOTES_BY_DEAL_ID = "SELECT * FROM note WHERE deal_id = ?";
    private static final String FIND_NOTES_BY_COMPANY_ID = "SELECT * FROM note WHERE company_id = ?";

    public NoteDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Note note) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NOTE)) {
            preparedStatement.setString(1, note.getText());
            preparedStatement.setInt(2, note.getCreatedByUser().getId());
            preparedStatement.setDate(3, new java.sql.Date(note.getCreationDate().getTime()));
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
        DaoFactoryImpl daoFactory = new DaoFactoryImpl();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_NOTES_BY_DEAL_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Note note = new Note();
                note.setId(resultSet.getInt("id"));
                note.setText(resultSet.getString("text"));
                note.setCreationDate(resultSet.getDate(7));
                note.setCreatedByUser(daoFactory.getUserDao().getUserById(resultSet.getInt("created_by")));
                notes.add(note);
            }
        } catch (SQLException e) {
            LOGGER.error("Getting notes was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return notes;
    }

    @Override
    public List<Note> findAllByCompanyId(int id) throws DatabaseException {
        List<Note> notes = new ArrayList<>();
        DaoFactoryImpl daoFactory = new DaoFactoryImpl();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_NOTES_BY_COMPANY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Note note = new Note();
                note.setId(resultSet.getInt("id"));
                note.setText(resultSet.getString("text"));
                note.setCreationDate(resultSet.getDate(7));
                note.setCreatedByUser(daoFactory.getUserDao().getUserById(resultSet.getInt("created_by")));
                notes.add(note);
            }
        } catch (SQLException e) {
            LOGGER.error("Getting notes was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return notes;
    }


}
