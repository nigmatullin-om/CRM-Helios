package com.becomejavasenior.dao;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

import javax.sql.DataSource;

public class DaoFactoryImpl implements DaoFactory {
    private static String URL = "localhost";
    private static String PORT = "8082";
    private static String DB_NAME = "crm_helios";
    private static String USER_NAME = "postgres";
    private static String PASSWORD = "root";
    private static String POOL_NAME = "pool";
    private static DataSource dataSource = null;

    private DataSource initDataSource(){
        /*http://svn.apache.org/viewvc/commons/proper/dbcp/trunk/doc/PoolingDataSourceExample.java?revision=1659726&view=markup*/
        String connectURI= "jdbc:postgresql://" + URL + ":" + PORT + "/" + DB_NAME;
        ConnectionFactory connectionFactory =  new DriverManagerConnectionFactory(connectURI,DB_NAME, PASSWORD);
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
        ObjectPool<PoolableConnection> connectionPool =  new GenericObjectPool<PoolableConnection>(poolableConnectionFactory);
        poolableConnectionFactory.setPool(connectionPool);
        PoolingDataSource<PoolableConnection> dataSource = new PoolingDataSource<PoolableConnection>(connectionPool);
        return dataSource;
    }

    /*public void initConnectionPool() {
        String connectURI= "jdbc:postgresql://" + URL +":" + PORT + "/" + DB_NAME ;
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectURI,USER_NAME, PASSWORD);
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
        ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<PoolableConnection>(poolableConnectionFactory);
        poolableConnectionFactory.setPool(connectionPool);

        try {
            Class.forName("org.apache.commons.dbcp2.PoolingDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        PoolingDriver driver = null;
        try {
            driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        driver.registerPool(POOL_NAME,connectionPool);
    }*/

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
            return dataSource ;
        }
        else {
            return dataSource;
        }
    }
}
