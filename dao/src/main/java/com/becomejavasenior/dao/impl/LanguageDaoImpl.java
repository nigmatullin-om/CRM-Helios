package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.LanguageDao;
import com.becomejavasenior.model.Language;

import javax.sql.DataSource;
import java.util.List;

public class LanguageDaoImpl extends CommonDao implements LanguageDao {
    public void create(Language language) {
    }

    public Language read(int id) {
        return null;
    }

    public void update(Language language) {
    }

    public void delete(Language language) {
    }

    public List<Language> findAll() {
        return null;
    }

    public LanguageDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
