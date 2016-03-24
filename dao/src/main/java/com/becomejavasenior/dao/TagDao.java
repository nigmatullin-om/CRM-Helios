package com.becomejavasenior.dao;


import com.becomejavasenior.model.Tag;

import java.util.List;

public interface TagDao {
    void create(Tag tag);
    Tag read(int id);
    void update(Tag tag);
    void delete(Tag tag);
    List<Tag> findAll();
}
