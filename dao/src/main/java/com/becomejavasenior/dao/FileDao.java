package com.becomejavasenior.dao;



import com.becomejavasenior.model.File;

import java.util.List;

public interface FileDao {
    void create(File file) throws DatabaseException;
    File getFileById(int id) throws DatabaseException;
    void update(File file) throws DatabaseException;
    void delete(File file)throws DatabaseException;
    List<File> findAll()throws DatabaseException;
}
