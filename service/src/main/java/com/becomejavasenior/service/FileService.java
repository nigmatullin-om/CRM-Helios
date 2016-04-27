package com.becomejavasenior.service;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.File;

public interface FileService {
    int createWithId(File file) throws DatabaseException;
    int addFileToDeal(File file, Deal deal) throws DatabaseException;
}
