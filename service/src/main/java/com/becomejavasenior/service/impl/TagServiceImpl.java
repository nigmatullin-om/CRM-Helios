package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.TagDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.Tag;
import com.becomejavasenior.service.TagService;

public class TagServiceImpl implements TagService {
    private TagDao tagDao;

    public TagServiceImpl() {
        this.tagDao = new DaoFactoryImpl().getTagDao();
    }

    @Override
    public int createWithId(Tag tag) throws DatabaseException {
        return tagDao.createWithId(tag);
    }

    @Override
    public int addTagToDeal(Tag tag, Deal deal) throws DatabaseException {
        return tagDao.addTagToDeal(tag.getId(), deal.getId());
    }

    @Override
    public Tag getTagByName(String name) throws DatabaseException {
        return tagDao.getTagByName(name);
    }
}
