package com.becomejavasenior.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/context.xml"})
public class HsqlConnectionTest {


    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    private RowMapper<Long> mapper = new CntRowMapper();
    private JdbcTemplate template;

    @Before
    public void setUp() throws Exception {
        template = new JdbcTemplate(dataSource);
    }

    @Test
    public void testRoleAccess() throws Exception {
        List<Long> result = template.query("select count(*) from role", mapper);
        assertEquals(2, (long)result.get(0));
    }

    @Test
    public void testCompanyAccess() throws Exception {
        List<Long> result = template.query("select count(*) from company", mapper);
        assertEquals(0, (long)result.get(0));
    }

    @Test
    public void testContactAccess() throws Exception {
        List<Long> result = template.query("select count(*) from contact", mapper);
        assertEquals(0, (long)result.get(0));
    }

    @Test
    public void testDealAccess() throws Exception {
        List<Long> result = template.query("select count(*) from deal", mapper);
        assertEquals(0, (long)result.get(0));
    }

    @Test
    public void testFileAccess() throws Exception {
        List<Long> result = template.query("select count(*) from file", mapper);
        assertEquals(0, (long)result.get(0));
    }

    @Test
    public void testNoteAccess() throws Exception {
        List<Long> result = template.query("select count(*) from note", mapper);
        assertEquals(0, (long)result.get(0));
    }

    @Test
    public void testPersonAccess() throws Exception {
        List<Long> result = template.query("select count(*) from person", mapper);
        assertEquals(0, (long)result.get(0));
    }

    @Test
    public void testTagAccess() throws Exception {
        List<Long> result = template.query("select count(*) from tag", mapper);
        assertEquals(0, (long)result.get(0));
    }

    @Test
    public void testTaskAccess() throws Exception {
        List<Long> result = template.query("select count(*) from task", mapper);
        assertEquals(0, (long)result.get(0));
    }

    @Test
    public void testLangAccess() throws Exception {
        List<Long> result = template.query("select count(*) from lang", mapper);
        assertEquals(3, (long)result.get(0));
    }

    @Test
    public void testCurrenciesAccess() throws Exception {
        List<Long> result = template.query("select count(*) from currencies", mapper);
        assertEquals(10, (long)result.get(0));
    }

    @Test
    public void testPhoneTypeAccess() throws Exception {
        List<Long> result = template.query("select count(*) from phone_type", mapper);
        assertEquals(6, (long)result.get(0));
    }

    @Test
    public void testStageAccess() throws Exception {
        List<Long> result = template.query("select count(*) from stage", mapper);
        assertEquals(6, (long)result.get(0));
    }

    @Test
    public void testTaskTypeAccess() throws Exception {
        List<Long> result = template.query("select count(*) from task_type", mapper);
        assertEquals(2, (long)result.get(0));
    }

    @After
    public void tearDown() throws Exception {
        dataSource.getConnection().close();
    }

    private class CntRowMapper implements RowMapper<Long> {
        @Override
        public Long mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getLong(1);
        }
    }
}
