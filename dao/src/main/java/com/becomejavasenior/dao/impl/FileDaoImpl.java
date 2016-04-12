package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.FileDao;
import com.becomejavasenior.model.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileDaoImpl extends CommonDao implements FileDao {

    private static final Logger LOGGER = LogManager.getLogger(FileDaoImpl.class);

    private static final String READ_FILE = "SELECT id, path, blob_data, date_create FROM file WHERE id=?";
    private static final String CREATE_FILE = "INSERT INTO file (path, blob_data, contact_id, created_by, date_create) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_FILE = "UPDATE file SET path=?, blob_data=?, contact_id=?, created_by=?, date_create=? WHERE id=?";
    private static final String DELETE_FILE = "DELETE FROM file WHERE id=?";
    private static final String FIND_ALL_FILES = "SELECT id, path, blob_data, date_create FROM file";
    private static final String FIND_ALL_FILES_BY_DEAL_ID = "SELECT * FROM file WHERE deal_id = ?";
    private static final String ADD_FILE_TO_DEAL = "UPDATE file SET deal_id=? WHERE id=?";

    public FileDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(File file) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FILE)) {
            preparedStatement.setString(1, file.getPath());
            preparedStatement.setBytes(2, file.getData());
            preparedStatement.setInt(3, file.getContact().getId());
            preparedStatement.setInt(4, file.getCreatedByUser().getId());
            preparedStatement.setDate(5, new java.sql.Date(file.getCreationDate().getTime()));
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Creating a file was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public File getFileById(int id) throws DatabaseException {
        File file = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_FILE)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    file = new File();
                    file.setId(resultSet.getInt("id"));
                    file.setPath(resultSet.getString("path"));
                    file.setData(resultSet.getBytes("blob_data"));
                    file.setCreationDate(resultSet.getDate("date_create"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Getting a file was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        if (file == null) {
            throw new DatabaseException("no result for id=" + id);
        }
        return file;
    }

    @Override
    public int update(File file) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FILE)) {
            preparedStatement.setString(1, file.getPath());
            preparedStatement.setBytes(2, file.getData());
            preparedStatement.setInt(3, file.getContact().getId());
            preparedStatement.setInt(4, file.getCreatedByUser().getId());
            preparedStatement.setDate(5, new java.sql.Date(file.getCreationDate().getTime()));
            preparedStatement.setInt(6, file.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Updating a file was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int delete(File file) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FILE)) {
            preparedStatement.setInt(1, file.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Deleting a file was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<File> findAll() throws DatabaseException {
        List<File> files = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_FILES);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                File file = new File();
                file.setId(resultSet.getInt("id"));
                file.setPath(resultSet.getString("path"));
                file.setData(resultSet.getBytes("blob_data"));
                files.add(file);
            }
        } catch (SQLException e) {
            LOGGER.error("Getting files was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return files;
    }

    @Override
    public List<File> findAllByDealId(int id) throws DatabaseException {
        List<File> files = new ArrayList<>();
        DaoFactoryImpl daoFactory = new DaoFactoryImpl();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_FILES_BY_DEAL_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                File file = new File();
                file.setId(resultSet.getInt("id"));
                file.setPath(resultSet.getString("path"));
                file.setData(resultSet.getBytes(3));
                file.setCreationDate(resultSet.getDate(9));
                file.setCreatedByUser(daoFactory.getUserDao().getUserById(resultSet.getInt("created_by")));
                files.add(file);
            }
        } catch (SQLException e) {
            LOGGER.error("Getting files was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return files;
    }

    @Override
    public int createWithId(File file) throws DatabaseException {
        int key;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FILE, Statement.RETURN_GENERATED_KEYS)) {

            if (file.getPath() == null){
                preparedStatement.setNull(1, Types.VARCHAR);
            }
            else{
                preparedStatement.setString(1, file.getPath());
            }

            preparedStatement.setBytes(2, file.getData());

            if (file.getContact() == null){
                preparedStatement.setNull(3, Types.INTEGER);
            }
            else {
                preparedStatement.setInt(3, file.getContact().getId());
            }

            preparedStatement.setInt(4, file.getCreatedByUser().getId());
            preparedStatement.setDate(5, new java.sql.Date(file.getCreationDate().getTime()));
            int affectedRows = preparedStatement.executeUpdate();
            LOGGER.info("affectedRows = " + affectedRows);
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys();) {
                if (resultSet.next()){
                    key = resultSet.getInt(1);
                    LOGGER.info("new file id = " + key);
                }
                else {
                    LOGGER.error("Couldn't create the file entity!");
                    throw new DatabaseException("Couldn't create the file entity!");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Couldn't create the file entity because of some SQL exception!");
            throw new  DatabaseException(e.getMessage());
        }
        return key;
    }

    @Override
    public int addFileToDeal(int fileId, int dealId) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_FILE_TO_DEAL)) {
            preparedStatement.setInt(1, dealId);
            preparedStatement.setInt(2, fileId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Adding a file to deal was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
    }
}
