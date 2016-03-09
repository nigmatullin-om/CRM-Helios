package com.becomejavasenior.dao;

import com.becomejavasenior.Tag;

import java.util.List;

public interface TagDao {
    public int create(Tag tag);
    public Tag read(int id);
    public boolean update(Tag tag);
    public boolean delete(Tag tag);
    public List<Tag> findAll();
}
