package com.becomejavasenior.dao;

import com.becomejavasenior.File;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileDaoImpl extends CommonDao implements FileDao {
    private final String READ_FILE= "SELECT * FROM file WHERE id=?";
    private final String CREATE_FILE = "INSERT INTO file (path, blob_data, contact_id, date_create) VALUES (?, ?, ?, ?)";
    private final String UPDATE_FILE = "UPDATE file SET path=?, blob_data=?, contact_id=?, date_create=? WHERE id=?";
    private final String DELETE_FILE = "DELETE FROM file WHERE id=?";
    private final String FIND_ALL_FILES = "SELECT * FROM file";

    public int create(File file) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(CREATE_FILE);
            preparedStatement.setString(1, file.getPath());
            preparedStatement.setBytes(2, file.getData());
            preparedStatement.setInt(3, file.getContact().getId());
            preparedStatement.setDate(4, new java.sql.Date(file.getCreationDate().getTime()));
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

    public File read(int id) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        File file = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(READ_FILE);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                file = new File();
                file.setId(resultSet.getInt(1));
                file.setPath(resultSet.getString(2));
                file.setData(resultSet.getBytes(3));
                file.setCreationDate(resultSet.getDate(9));
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
        return file;
    }

    public boolean update(File file) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_FILE);
            preparedStatement.setString(1, file.getPath());
            preparedStatement.setBytes(2, file.getData());
            preparedStatement.setInt(3, file.getContact().getId());
            preparedStatement.setDate(4, new java.sql.Date(file.getCreationDate().getTime()));
            preparedStatement.setInt(5, file.getId());
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

    public boolean delete(File file) throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_FILE);
            preparedStatement.setInt(1, file.getId());
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

    public List<File> findAll() throws DatabaseException {
        ArrayList<Exception> exceptions = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<File> files = new ArrayList<File>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_FILES);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                File file = new File();
                file.setId(resultSet.getInt(1));
                file.setPath(resultSet.getString(2));
                file.setData(resultSet.getBytes(3));
                file.setCreationDate(resultSet.getDate(9));
                files.add(file);
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
        return files;
    }

    public FileDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
