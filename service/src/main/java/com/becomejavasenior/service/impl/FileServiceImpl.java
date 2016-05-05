package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.FileDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.File;
import com.becomejavasenior.service.FileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FileDao fileDao;

    public FileDao getFileDao() {
        return fileDao;
    }

    public void setFileDao(FileDao fileDao) {
        this.fileDao = fileDao;
    }

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
