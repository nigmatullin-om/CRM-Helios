package com.becomejavasenior.dao;

import com.becomejavasenior.model.File;

import java.util.List;

public interface FileDao {
    int create(File file) throws DatabaseException;
    File getFileById(int id) throws DatabaseException;
    int update(File file) throws DatabaseException;
    int delete(File file)throws DatabaseException;
    List<File> findAll()throws DatabaseException;
    List<File> findAllByDealId(int id) throws DatabaseException;
    int createWithId(File file) throws DatabaseException;
    int addFileToDeal(int fileId, int dealId) throws DatabaseException;
}
