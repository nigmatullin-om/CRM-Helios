package com.becomejavasenior.service;

import com.becomejavasenior.dao.CompanyDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.service.impl.CompanyServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * Created by aivlev on 3/26/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(CompanyServiceImpl.class)
public class CompanyServiceTest {

    private static final int COMPANIES_COUNT = 2;

    private CompanyService companyService;

    @Mock
    private CompanyDao companyDao;

    @Mock
    private Company company;

    @Mock
    DaoFactoryImpl daoFactory;

    @Before
    public void setUp() throws Exception {
        whenNew(DaoFactoryImpl.class).withNoArguments().thenReturn(daoFactory);
        when(daoFactory.getCompanyDao()).thenReturn(companyDao);
        companyService = new CompanyServiceImpl();
    }

    @Test
    public void testCreate() throws Exception {
        doNothing().when(companyDao, "create", any(Company.class));
        companyService.create(company);

        verify(companyDao).create(company);
    }

    @Test
    public void testGetCompanyById() throws Exception {
        when(companyDao.getCompanyById(1)).thenReturn(company);

        Company resultCompany = companyService.getCompanyById(1);

        verify(companyDao).getCompanyById(1);
        assertEquals(company, resultCompany);

    }

    @Test
    public void testUpdate() throws Exception {
        doNothing().when(companyDao, "update", any(Company.class));
        companyService.update(company);

        verify(companyDao).update(company);
    }

    @Test
    public void testDelete() throws Exception {
        doNothing().when(companyDao, "delete", any(Company.class));
        companyService.delete(company);

        verify(companyDao).delete(company);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Company> companies = new LinkedList<>();

        Company firstCompany = new Company();
        firstCompany.setId(1);
        firstCompany.setName("First Company");

        companies.add(firstCompany);

        Company secondCompany = new Company();
        secondCompany.setId(2);
        secondCompany.setName("Second Company");

        companies.add(secondCompany);

        when(companyDao.findAll()).thenReturn(companies);
        List<Company> result = companyService.findAll();

        verify(companyDao).findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetCount() throws DatabaseException {
        when(companyDao.getCount()).thenReturn(COMPANIES_COUNT);
        int result = companyService.getCount();

        verify(companyDao).getCount();
        assertEquals(COMPANIES_COUNT, result);

    }
}