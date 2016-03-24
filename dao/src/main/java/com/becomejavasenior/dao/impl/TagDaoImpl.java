package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.TagDao;
import com.becomejavasenior.model.Tag;

import javax.sql.DataSource;
import java.util.List;

public class TagDaoImpl extends CommonDao implements TagDao {
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

    @Override
    public List<Tag> findAll() {
        return null;
    }

    public TagDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
