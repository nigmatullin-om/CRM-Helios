package com.becomejavasenior.dao;


import com.becomejavasenior.File;

import java.util.List;

public interface FileDao {
    public int create(File file) throws DatabaseException;
    public File read(int id) throws DatabaseException;
    public boolean update(File file) throws DatabaseException;
    public boolean delete(File file)throws DatabaseException;
    public List<File> findAll()throws DatabaseException;
}
