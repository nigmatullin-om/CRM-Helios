package com.becomejavasenior.service.impl;


import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.TagDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.dao.impl.TagDaoImpl;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Tag;
import com.becomejavasenior.service.TagService;
import com.becomejavasenior.model.Deal;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagDaoImpl  tagDao;

    public TagDaoImpl getTagDao() {
        return tagDao;
    }

    public void setTagDao(TagDaoImpl tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public int create(Tag tag) throws DatabaseException {
        return tagDao.create(tag);
    }

    @Override
    public Tag getTagById(int id) throws DatabaseException {
        return tagDao.getTagById(id);
    }

    @Override
    public int update(Tag tag) throws DatabaseException {
        return tagDao.update(tag);
    }

    @Override
    public int delete(Tag tag) throws DatabaseException {
        return tagDao.delete(tag);
    }

    @Override
    public int getMaxId() throws DatabaseException {
        return tagDao.getMaxId();
    }

    @Override
    public int updateTagContact(Tag tag, Contact contact, Company company) throws DatabaseException {
        return tagDao.updateTagContact(tag, contact, company);
    }

    @Override
    public Tag getTagByName(String name) throws DatabaseException {
        return tagDao.getTagByName(name);
    }

    @Override
    public boolean isExist(Tag tag) throws DatabaseException {
        List<Tag> tags = tagDao.findAll();
        for(Tag iterator: tags){
            if(iterator.getName().equals(tag.getName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Tag> findAll() throws DatabaseException {
        return tagDao.findAll();
    }

    @Override
    public List<Tag> findAllByDealId(int id) throws DatabaseException {
        return tagDao.findAllByDealId(id);
    }

    @Override
    public List<Tag> findAllByCompanyId(int id) throws DatabaseException {
        return tagDao.findAllByDealId(id);
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
    public Tag findTagByName(String name) throws DatabaseException {
        return tagDao.findTagByName(name);
    }
}
