package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;


public class DaoFactoryImpl implements DaoFactory {

    private static final Logger LOGGER = LogManager.getLogger(DaoFactoryImpl.class);

    private DataSource dataSource;

    public CompanyDao getCompanyDao() {
        return new CompanyDaoImpl(getDataSource());
    }

    public ContactDao getContactDao() {
        return new ContactDaoImpl(getDataSource());
    }

    public DealDao getDealDao() {
        return new DealDaoImpl(getDataSource());
    }

    public FileDao getFileDao() {
        return new FileDaoImpl(getDataSource());
    }

    public LanguageDao getLanguageDao() {
        return new LanguageDaoImpl(getDataSource());
    }

    public NoteDao getNoteDao() {
        return new NoteDaoImpl(getDataSource());
    }

    public RoleDao getRoleDao() {
        return new RoleDaoImpl(getDataSource());
    }

    public TagDao getTagDao() {
        return new TagDaoImpl(getDataSource());
    }

    public TaskDao getTaskDao() {
        return new TaskDaoImpl(getDataSource());
    }

    public UserDao getUserDao() {
        return new UserDaoImpl(getDataSource());
    }

    public TaskTypeDao getTaskTypeDao() {
        return new TaskTypeDaoImpl(getDataSource());
    }

    public CurrenciesDao getCurrenciesDao(){return  new CurrenciesDaoImpl(getDataSource());}

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
