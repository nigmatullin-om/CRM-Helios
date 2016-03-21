package com.becomejavasenior.dao.impl;


import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.TagDao;
import com.becomejavasenior.model.Tag;

import javax.sql.DataSource;
import java.util.List;

public class TagDaoImpl extends CommonDao implements TagDao {
    public int create(Tag tag) {
        return 0;
    }

    public Tag read(int id) {
        return null;
    }

    public boolean update(Tag tag) {
        return false;
    }

    public boolean delete(Tag tag) {
        return false;
    }

    public List<Tag> findAll() {
        return null;
    }

    public TagDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
