package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DaoFactory;
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


    private final String READ_FILE= "SELECT id, path, blob_data, date_create FROM crm_helios.file WHERE id=?";
    private final String CREATE_FILE = "INSERT INTO crm_helios.file (path, blob_data, contact_id, created_by, date_create)" +
            " VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE_FILE = "UPDATE crm_helios.file SET path=?, blob_data=?, contact_id=?, created_by=?, date_create=? " +
            "WHERE id=?";
    private final String DELETE_FILE = "DELETE FROM crm_helios.file WHERE id=?";
    private final String FIND_ALL_FILES = "SELECT * FROM crm_helios.file";
    private final String FIND_ALL_FILES_BY_DEAL_ID = "SELECT * FROM crm_helios.file WHERE deal_id = ?";

    public FileDaoImpl(DataSource dataSource) {
        super(dataSource);
    }


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
                files.add(file);
            }
        } catch (SQLException e) {
            log.error("Couldn't find from file entity because of some SQL exception!");
            throw new DatabaseException(e.getMessage());
        }
        return files;
    }

    @Override
    public List<File> findAllByDealId(int id) throws DatabaseException {
        List<File> files = new ArrayList<File>();
        DaoFactoryImpl daoFactory = new DaoFactoryImpl();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_FILES_BY_DEAL_ID)) {
                preparedStatement.setInt(1, id);
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    while (resultSet.next()) {
                        File file = new File();
                        file.setId(resultSet.getInt("id"));
                        file.setPath(resultSet.getString("path"));
                        file.setData(resultSet.getBytes(3));
                        file.setCreationDate(resultSet.getDate(9));
                        file.setCreatedByUser(daoFactory.getUserDao().read(resultSet.getInt("created_by")));
                        files.add(file);
                    }
                }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return files;
    }


}
