package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.FileDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.File;
import com.becomejavasenior.service.FileService;

public class FileServiceImpl implements FileService {
    private FileDao fileDao;

    public FileServiceImpl() {
        fileDao = new DaoFactoryImpl().getFileDao();
    }

    @Override
    public int createWithId(File file) throws DatabaseException {
        return fileDao.createWithId(file);
    }

    @Override
    public int addFileToDeal(File file, Deal deal) throws DatabaseException {
        return fileDao.addFileToDeal(file.getId(), deal.getId());
    }
}
