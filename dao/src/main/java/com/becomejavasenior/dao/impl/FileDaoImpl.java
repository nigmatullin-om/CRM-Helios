package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.FileDao;
import com.becomejavasenior.model.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileDaoImpl extends CommonDao implements FileDao {
    private final String READ_FILE= "SELECT id, path, blob_data, date_create FROM file WHERE id=?";
    private final String CREATE_FILE = "INSERT INTO file (path, blob_data, contact_id, created_by, date_create) VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE_FILE = "UPDATE file SET path=?, blob_data=?, contact_id=?, created_by=?, date_create=? WHERE id=?";
    private final String DELETE_FILE = "DELETE FROM file WHERE id=?";
    private final String FIND_ALL_FILES = "SELECT id, path, blob_data, date_create FROM file";

    static final Logger log = LogManager.getLogger(FileDaoImpl.class);

    public void create(File file) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FILE)) {
            preparedStatement.setString(1, file.getPath());
            preparedStatement.setBytes(2, file.getData());
            preparedStatement.setInt(3, file.getContact().getId());
            preparedStatement.setInt(4, file.getCreatedByUser().getId());
            preparedStatement.setDate(5, new java.sql.Date(file.getCreationDate().getTime()));
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error("Couldn't create the file entity because of some SQL exception!");
            throw new DatabaseException(e.getMessage());
        }
    }

    public File read(int id) throws DatabaseException {
        File file = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_FILE);) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery();){
                if (resultSet.next()){
                    file = new File();
                    file.setId(resultSet.getInt("id"));
                    file.setPath(resultSet.getString("path"));
                    file.setData(resultSet.getBytes("blob_data"));
                    file.setCreationDate(resultSet.getDate("date_create"));
                }
            }
        } catch (SQLException e) {
            log.error("Couldn't read from file entity because of some SQL exception!");
            throw new DatabaseException(e.getMessage());
        }
        if (file == null){
            throw new DatabaseException("no result for id=" + id);
        }
        return file;
    }

    public void update(File file) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FILE);) {
            preparedStatement.setString(1, file.getPath());
            preparedStatement.setBytes(2, file.getData());
            preparedStatement.setInt(3, file.getContact().getId());
            preparedStatement.setInt(4, file.getCreatedByUser().getId());
            preparedStatement.setDate(5, new java.sql.Date(file.getCreationDate().getTime()));
            preparedStatement.setInt(6, file.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error("Couldn't update the file entity because of some SQL exception!");
            throw new DatabaseException(e.getMessage());
        }
    }

    public void delete(File file) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FILE);) {
            preparedStatement.setInt(1, file.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error("Couldn't delete the file entity because of some SQL exception!");
            throw new DatabaseException(e.getMessage());
        }
    }

    public List<File> findAll() throws DatabaseException {
        List<File> files = new ArrayList<File>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_FILES);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                File file = new File();
                file.setId(resultSet.getInt("id"));
                file.setPath(resultSet.getString("path"));
                file.setData(resultSet.getBytes("blob_data"));
                file.setCreationDate(resultSet.getDate("date_create"));
                files.add(file);
            }
        } catch (SQLException e) {
            log.error("Couldn't find from file entity because of some SQL exception!");
            throw new DatabaseException(e.getMessage());
        }
        return files;
    }

    public FileDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
