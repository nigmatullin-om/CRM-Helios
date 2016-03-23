package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.TagDao;
import com.becomejavasenior.model.Tag;

import javax.sql.DataSource;
import java.util.List;

public class TagDaoImpl extends CommonDao implements TagDao {
    public void create(Tag tag) {

    }

    public Tag read(int id) {
        return null;
    }

    public void update(Tag tag) {

    }

    public void delete(Tag tag) {

    }

    public List<Tag> findAll() {
        return null;
    }

    public TagDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
