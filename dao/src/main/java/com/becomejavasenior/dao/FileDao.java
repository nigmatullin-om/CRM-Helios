package com.becomejavasenior.dao;


import com.becomejavasenior.File;

import java.util.List;

public interface FileDao {
    public int create(File file);
    public File read(int id);
    public boolean update(File file);
    public boolean delete(File file);
    public List<File> findAll();
}
