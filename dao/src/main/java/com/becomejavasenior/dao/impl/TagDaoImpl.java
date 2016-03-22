package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.DaoFactory;
import com.becomejavasenior.dao.TagDao;
import com.becomejavasenior.model.Tag;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TagDaoImpl extends CommonDao implements TagDao {
    private String FIND_ALL_BY_DEAL_ID ="SELECT * FROM crm_helios.tag JOIN crm_helios.tag_deal ON" +
            " tag.id = tag_deal.tag_id AND deal_id = ?";

    public int create(Tag tag)throws DatabaseException {
        return 0;
    }

    public Tag read(int id)throws DatabaseException {
        return null;
    }

    public boolean update(Tag tag)throws DatabaseException {
        return false;
    }

    public boolean delete(Tag tag)throws DatabaseException {
        return false;
    }

    public List<Tag> findAll()throws DatabaseException {
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
                    tag.setCreatedByUser(daoFactory.getUserDao().read(resultSet.getInt(3)));
                }
            }
        }
         catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TagDaoImpl(DataSource dataSource)throws DatabaseException {
        super(dataSource);
    }
}
