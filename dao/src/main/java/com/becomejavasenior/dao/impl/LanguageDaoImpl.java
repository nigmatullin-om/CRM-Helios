package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.CommonDao;
import com.becomejavasenior.dao.LanguageDao;
import com.becomejavasenior.model.Language;

import javax.sql.DataSource;
import java.util.List;

public class LanguageDaoImpl extends CommonDao implements LanguageDao {
    public int create(Language language) {
        return 0;
    }

    public Language read(int id) {
        return null;
    }

    public boolean update(Language language) {
        return false;
    }

    public boolean delete(Language language) {
        return false;
    }

    public List<Language> findAll() {
        return null;
    }

    public LanguageDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
