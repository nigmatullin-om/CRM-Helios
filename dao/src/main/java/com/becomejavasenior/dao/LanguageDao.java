package com.becomejavasenior.dao;


import com.becomejavasenior.model.Language;

import java.util.List;

public interface LanguageDao {
    void create(Language language);
    Language getLanguageById(int id);
    void update(Language language);
    void delete(Language language);
    List<Language> findAll();
}
