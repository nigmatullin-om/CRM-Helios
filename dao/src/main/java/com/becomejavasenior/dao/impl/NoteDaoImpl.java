package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.NoteDao;
import com.becomejavasenior.model.Note;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteDaoImpl extends CommonDao implements NoteDao {
    private final String READ_NOTE= "SELECT * FROM note WHERE id=?";
    private final String CREATE_NOTE = "INSERT INTO note (text, created_by, date_create) " +
                                        "VALUES (?, ?, ?)";
    private final String UPDATE_NOTE = "UPDATE note SET text=?, created_by=?, date_create=? WHERE id=?";
    private final String DELETE_NOTE = "DELETE FROM note WHERE id=?";
    private final String FIND_ALL_NOTES = "SELECT * FROM note";
    private final String FIND_ALL_NOTES_BY_DEAL_ID = "SELECT * FROM crm_helios.note WHERE deal_id = ?";


    public int create(Note note) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NOTE)) {
            preparedStatement.setString(1, note.getText());
            preparedStatement.setInt(2, note.getCreatedByUser().getId());
            preparedStatement.setDate(3, new java.sql.Date(note.getCreationDate().getTime()));
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return 1;
    }

    public Note read(int id) throws DatabaseException {
        Note note = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_NOTE);) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery();){
                if (resultSet.next()){
                    note.setId(resultSet.getInt(1));
                    note.setText(resultSet.getString(2));
                    note.setCreationDate(resultSet.getDate(7));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return note;
    }

    public boolean update(Note note) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NOTE);) {
            preparedStatement.setString(1, note.getText());
            preparedStatement.setInt(2, note.getCreatedByUser().getId());
            preparedStatement.setDate(3, new java.sql.Date(note.getCreationDate().getTime()));
            preparedStatement.setInt(4, note.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return true;
    }

    public boolean delete(Note note) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_NOTE);) {
            preparedStatement.setInt(1, note.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return true;
    }

    public List<Note> findAll() throws DatabaseException {
        List<Note> notes = new ArrayList<Note>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_NOTES);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                Note note = new Note();
                note.setId(resultSet.getInt(1));
                note.setText(resultSet.getString(2));
                note.setCreationDate(resultSet.getDate(7));
                notes.add(note);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return  notes;
    }

    @Override
    public List<Note> findAllByDealId(int id) throws DatabaseException {
        List<Note> notes = new ArrayList<Note>();
        DaoFactoryImpl daoFactory = new DaoFactoryImpl();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_NOTES_BY_DEAL_ID)) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    Note note = new Note();
                    note.setId(resultSet.getInt("id"));
                    note.setText(resultSet.getString("text"));
                    note.setCreationDate(resultSet.getDate(7));
                    note.setCreatedByUser(daoFactory.getUserDao().read(resultSet.getInt("crated_by")));
                    notes.add(note);
                }
            }
            catch (SQLException e) {
                throw new DatabaseException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return  notes;
    }

    public NoteDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
