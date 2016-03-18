package com.becomejavasenior.dao;

import com.becomejavasenior.Note;

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

    public int create(Note note) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(CREATE_NOTE);
            preparedStatement.setString(1, note.getText());
            preparedStatement.setInt(2, note.getCreatedByUser().getId());
            preparedStatement.setDate(3, new java.sql.Date(note.getCreationDate().getTime()));
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
        return 1;
    }

    public Note read(int id) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Note note = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(READ_NOTE);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                note.setId(resultSet.getInt(1));
                note.setText(resultSet.getString(2));
                note.setCreationDate(resultSet.getDate(7));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
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
        return note;
    }

    public boolean update(Note note) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_NOTE);
            preparedStatement.setString(1, note.getText());
            preparedStatement.setInt(2, note.getCreatedByUser().getId());
            preparedStatement.setDate(3, new java.sql.Date(note.getCreationDate().getTime()));
            preparedStatement.setInt(4, note.getId());
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

    public boolean delete(Note note) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            preparedStatement = connection.prepareStatement(DELETE_NOTE);
            preparedStatement.setInt(1, note.getId());
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

    public List<Note> findAll() throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Note> notes = new ArrayList<Note>();
        try {
            preparedStatement = connection.prepareStatement(FIND_ALL_NOTES);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Note note = new Note();
                note.setId(resultSet.getInt(1));
                note.setText(resultSet.getString(2));
                note.setCreationDate(resultSet.getDate(7));
                notes.add(note);
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
        return  notes;
    }

    public NoteDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
