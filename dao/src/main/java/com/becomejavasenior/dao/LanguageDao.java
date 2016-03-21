package com.becomejavasenior.dao;


import com.becomejavasenior.model.Language;

import java.util.List;

public interface LanguageDao {
    public int create(Language language);
    public Language read(int id);
    public boolean update(Language language);
    public boolean delete(Language language);
    public List<Language> findAll();
}
