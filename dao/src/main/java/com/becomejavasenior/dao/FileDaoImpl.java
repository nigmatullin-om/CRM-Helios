package com.becomejavasenior.dao;

import com.becomejavasenior.File;

import javax.sql.DataSource;
import java.util.List;

public class FileDaoImpl extends AbstractDao implements FileDao {
    public int create(File file) {
        return 0;
    }

    public File read(int id) {
        return null;
    }

    public boolean update(File file) {
        return false;
    }

    public boolean delete(File file) {
        return false;
    }

    public List<File> findAll() {
        return null;
    }

    public FileDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
