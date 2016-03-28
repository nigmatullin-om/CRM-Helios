package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.TagDao;
import com.becomejavasenior.model.Tag;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TagDaoImpl extends CommonDao implements TagDao {

    private static final String FIND_ALL_BY_DEAL_ID ="SELECT * FROM tag JOIN tag_deal ON" +
            " tag.id = tag_deal.tag_id AND deal_id = ?";


    @Override
    public void create(Tag tag) {

    }

    @Override
    public Tag getTagById(int id) {
        return null;
    }

    @Override
    public void update(Tag tag) {
    }

    @Override
    public void delete(Tag tag) {
    }

    public List<Tag> findAll(){
        return null;
    }

    @Override
    public List<Tag> findAllByDealId(int id) throws DatabaseException {
        try (Connection connection =  getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_DEAL_ID)){
             preparedStatement.setInt(1, id);
            DaoFactoryImpl daoFactory = new DaoFactoryImpl();
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    Tag tag = new Tag();
                    tag.setId(resultSet.getInt(1));
                    tag.setName(resultSet.getString(2));
                    tag.setCreationDate(resultSet.getDate(4));
                    tag.setCreatedByUser(daoFactory.getUserDao().getUserById(resultSet.getInt(3)));
                }
            }
        }
         catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TagDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
