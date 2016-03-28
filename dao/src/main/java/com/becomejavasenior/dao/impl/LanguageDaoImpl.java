package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.LanguageDao;
import com.becomejavasenior.model.Language;

import javax.sql.DataSource;
import java.util.List;

public class LanguageDaoImpl extends CommonDao implements LanguageDao {

    @Override
    public void create(Language language) {
    }

    @Override
    public Language getLanguageById(int id) {
        return null;
    }

    @Override
    public void update(Language language) {
    }

    @Override
    public void delete(Language language) {
    }

    @Override
    public List<Language> findAll() {
        return null;
    }

    public LanguageDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
