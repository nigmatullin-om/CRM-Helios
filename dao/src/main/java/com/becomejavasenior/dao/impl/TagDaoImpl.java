package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.TagDao;
import com.becomejavasenior.model.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class TagDaoImpl extends CommonDao implements TagDao {

    private static final Logger LOGGER = LogManager.getLogger(TagDaoImpl.class);

    private static final String FIND_ALL_BY_DEAL_ID = "SELECT * FROM tag JOIN tag_deal ON" +
            " tag.id = tag_deal.tag_id AND deal_id = ?";

    private static final String CREATE_TAG = "INSERT INTO tag (name, created_by, date_create) VALUES (?, ?, ?)";
    private static final String GET_TAG_BY_NAME = "SELECT id, name, created_by, date_create FROM tag WHERE name=?";
    private static final String ADD_TAG_TO_DEAL = "INSERT INTO tag_deal (tag_id, deal_id) VALUES (?, ?)";

    public TagDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(Tag tag) {
        return 0;
    }

    @Override
    public Tag getTagById(int id) {
        return null;
    }

    @Override
    public int update(Tag tag) {
        return 0;
    }

    @Override
    public int delete(Tag tag) {
        return 0;
    }

    @Override
    public List<Tag> findAll() {
        return null;
    }

    @Override
    public List<Tag> findAllByDealId(int id) throws DatabaseException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_DEAL_ID)) {
            preparedStatement.setInt(1, id);
            DaoFactoryImpl daoFactory = new DaoFactoryImpl();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Tag tag = new Tag();
                tag.setId(resultSet.getInt(1));
                tag.setName(resultSet.getString(2));
                tag.setCreationDate(resultSet.getDate(4));
                tag.setCreatedByUser(daoFactory.getUserDao().getUserById(resultSet.getInt(3)));
            }
        } catch (SQLException e) {
            LOGGER.error("Creating tasks was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Tag> findAllByCompanyId(int id) throws DatabaseException {
        return null;
    }

    @Override
    public Tag getTagByName(String name) throws DatabaseException {
        Tag tag = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TAG_BY_NAME)){
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    tag = new Tag();
                    tag.setId(resultSet.getInt("id"));
                    tag.setName(resultSet.getString("name"));
                    tag.setCreationDate(resultSet.getDate("date_create"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Couldn't read the tag entity because of some SQL exception!");
            throw new  DatabaseException(e.getMessage());
        }
        return tag;
    }

    @Override
    public int createWithId(Tag tag) throws DatabaseException {
        int key;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TAG, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, tag.getName());
            preparedStatement.setInt(2, tag.getCreatedByUser().getId());
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(tag.getCreationDate().getTime()));
            int affectedRows = preparedStatement.executeUpdate();
            LOGGER.info("affectedRows = " + affectedRows);
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys();) {
                if (resultSet.next()){
                    key = resultSet.getInt(1);
                    LOGGER.info("new tag id = " + key);
                }
                else {
                    LOGGER.error("Couldn't create the tag entity!");
                    throw new DatabaseException("Couldn't create the tag entity!");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Couldn't create the tag entity because of some SQL exception!");
            throw new  DatabaseException(e.getMessage());
        }
        return key;
    }

    @Override
    public int addTagToDeal(int tagId, int dealId) throws DatabaseException {
        int affectedRows;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_TAG_TO_DEAL)){
            preparedStatement.setInt(1, tagId);
            preparedStatement.setInt(2, dealId);
            affectedRows = preparedStatement.executeUpdate();
            LOGGER.info("add tag to deal affected rows = " + affectedRows);
        } catch (SQLException e) {
            LOGGER.error("Couldn't add tag to deal because of some SQL exception!");
            throw new  DatabaseException(e.getMessage());
        }
        return affectedRows;
    }
}
