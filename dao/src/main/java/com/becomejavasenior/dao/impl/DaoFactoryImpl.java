package com.becomejavasenior.dao.impl;

import com.becomejavasenior.dao.*;
import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoFactoryImpl implements DaoFactory {

    private static final String URL = "url";
    private static final String PORT = "port";
    private static final String DB_NAME = "db_name";
    private static final String USER_NAME = "user_name";
    private static final String PASSWORD = "password";
    private static final String DRIVER_CLASS_NAME = "driverClassName";
    private static String URL_VALUE;
    private static String PORT_VALUE;
    private static String DB_NAME_VALUE;
    private static String USER_NAME_VALUE;
    private static String PASSWORD_VALUE;
    private static DataSource dataSource;

    static final Logger log = LogManager.getLogger(DaoFactoryImpl.class);

    private DataSource initDataSource(){
        loadProperties();
        String connectURI= "jdbc:postgresql://" + URL_VALUE + ":" + PORT_VALUE + "/" + DB_NAME_VALUE;
        ConnectionFactory connectionFactory =  new DriverManagerConnectionFactory(connectURI, USER_NAME_VALUE, PASSWORD_VALUE);
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
        ObjectPool<PoolableConnection> connectionPool =  new GenericObjectPool<>(poolableConnectionFactory);
        poolableConnectionFactory.setPool(connectionPool);
        PoolingDataSource<PoolableConnection> dataSource = new PoolingDataSource<>(connectionPool);
        return dataSource;
    }

    private void loadProperties(){
        Properties prop = new Properties();
        InputStream input;
        try {
            input = Thread.currentThread().getContextClassLoader().getResourceAsStream("db_config.properties");
            prop.load(input);
            URL_VALUE = prop.getProperty(URL);
            PORT_VALUE = prop.getProperty(PORT);
            DB_NAME_VALUE = prop.getProperty(DB_NAME);
            USER_NAME_VALUE = prop.getProperty(USER_NAME);
            PASSWORD_VALUE = prop.getProperty(PASSWORD);
            Class.forName(prop.getProperty(DRIVER_CLASS_NAME));
        } catch (ClassNotFoundException e) {
                e.printStackTrace();
        } catch (FileNotFoundException e) {
            log.error("Couldn't find the properties file!");
        } catch (IOException e) {
            log.error("Couldn't get properties!");
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
            dataSource = initDataSource();
        }
        return dataSource;
    }
}
