package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.TagDao;
import com.becomejavasenior.model.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagDaoImpl extends CommonDao implements TagDao {

    private static final Logger LOGGER = LogManager.getLogger(TagDaoImpl.class);

    private static final String FIND_ALL_BY_DEAL_ID = "SELECT * FROM tag JOIN tag_deal ON" +
            " tag.id = tag_deal.tag_id AND deal_id = ?";
    private static final String FIND_ALL_BY_ALL_CONTACTS_ID = "SELECT * FROM tag JOIN tag_contact_company ON" +
            " tag.id = tag_contact_company.tag_id";

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
    public List<Tag> findAllByAllContacts() throws DatabaseException {
        List<Tag> tags = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_ALL_CONTACTS_ID);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            DaoFactoryImpl daoFactory = new DaoFactoryImpl();
            while (resultSet.next()) {
                Tag tag = new Tag();
                tag.setId(resultSet.getInt("id"));
                tag.setName(resultSet.getString("name"));
                int user = resultSet.getInt("id");
                tag.setCreatedByUser(daoFactory.getUserDao().getUserById(user));
                tag.setCreationDate(resultSet.getDate("date_create"));
                tags.add(tag);
            }
        } catch (SQLException e) {
            LOGGER.error("Getting tags was failed. Error - {}", new Object[]{e.getMessage()});
            throw new DatabaseException(e.getMessage());
        }
        return tags;
    }
}
