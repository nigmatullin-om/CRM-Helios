package com.becomejavasenior.dao.integration;


import com.becomejavasenior.dao.*;
import org.dbunit.DefaultDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-test.xml")
public abstract class AbstractTestDao {
    private static final String DEFAULT_DATA_XML_PATH = "defaultData.xml";
    private IDatabaseTester databaseTester;

    private IDatabaseConnection connection;

    @Autowired
    TaskDao taskDao;

    @Autowired
    UserDao userDao;

    @Autowired
    TaskTypeDao taskTypeDao;

    @Autowired
    DataSource dataSource;

    @Autowired
    DealDao dealDao;

    @Autowired
    CompanyDao companyDao;

    @Autowired
    ContactDao contactDao;

    @Autowired
    TagDao tagDao;

    @Autowired
    NoteDao noteDao;

    @Value("${currentSchema}")
    public String currentSchema;



    @Before
    public void init() throws Exception {
        IDatabaseConnection connection = new DatabaseConnection(getDataSource().getConnection(), currentSchema);
        DatabaseConfig config = connection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
        databaseTester = new DefaultDatabaseTester(connection);

        IDataSet dataSet = getDataSet();
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
        fixIndexes();
        databaseTester.onTearDown();
        databaseTester.getConnection().close();
        connection.close();
    }

    @After
    public void clean() throws Exception {
        dataSource.getConnection().close();
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    protected IDataSet getDataSet() throws Exception {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(DEFAULT_DATA_XML_PATH);
        ReplacementDataSet defaultDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(resourceAsStream));
        defaultDataSet.addReplacementObject("[null]", null);

        IDataSet[] dataSets = {defaultDataSet, getSpecificDataSet()};
        return new CompositeDataSet(dataSets);
    }

    private void fixIndexes() throws SQLException {
        Connection connection = getDataSource().getConnection();
        String query = "SELECT setval('company_id_seq', (SELECT MAX(id) FROM company));\n" +
                "SELECT setval('contact_id_seq', (SELECT MAX(id) FROM contact));\n" +
                "SELECT setval('deal_id_seq', (SELECT MAX(id) FROM deal));\n" +
                "SELECT setval('file_id_seq', (SELECT MAX(id) FROM file));\n" +
                "SELECT setval('note_id_seq', (SELECT MAX(id) FROM note));\n" +
                "SELECT setval('person_id_seq', (SELECT MAX(id) FROM person));\n" +
                "SELECT setval('tag_id_seq', (SELECT MAX(id) FROM tag));\n" +
                "SELECT setval('task_type_id_seq', (SELECT MAX(id) FROM task_type));\n" +
                "SELECT setval('task_id_seq', (SELECT MAX(id) FROM task));\n";
        connection.prepareStatement(query).execute();
        connection.close();
    }

    abstract protected IDataSet getSpecificDataSet() throws DataSetException;
}