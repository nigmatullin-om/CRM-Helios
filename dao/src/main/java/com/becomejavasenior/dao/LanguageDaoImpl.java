package com.becomejavasenior.dao;

import com.becomejavasenior.Language;

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
