package com.becomejavasenior.dao;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoFactoryImpl implements DaoFactory {
    private static String URL;
    private static String PORT;
    private static String DB_NAME;
    private static String USER_NAME;
    private static String PASSWORD;
    private static DataSource dataSource = null;

    private DataSource initDataSource(){
        String connectURI= "jdbc:postgresql://" + URL + ":" + PORT + "/" + DB_NAME;
        ConnectionFactory connectionFactory =  new DriverManagerConnectionFactory(connectURI,USER_NAME, PASSWORD);
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
        ObjectPool<PoolableConnection> connectionPool =  new GenericObjectPool<PoolableConnection>(poolableConnectionFactory);
        poolableConnectionFactory.setPool(connectionPool);
        PoolingDataSource<PoolableConnection> dataSource = new PoolingDataSource<PoolableConnection>(connectionPool);
        return dataSource;
    }

    private void loadProperties(){
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("db_config.properties");
            prop.load(input);
            URL = prop.getProperty("url");
            PORT = prop.getProperty("port");
            DB_NAME = prop.getProperty("db_name");
            USER_NAME = prop.getProperty("user_name");
            PASSWORD = prop.getProperty("password");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("can't find propereties file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public DaoFactoryImpl() {
    }

    private DataSource getDataSource(){
        if(dataSource == null){
            loadProperties();
            dataSource = initDataSource();
            return dataSource ;
        }
        else {
            return dataSource;
        }
    }
}
