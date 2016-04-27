package com.becomejavasenior.service;

import com.becomejavasenior.dao.CompanyDao;
import com.becomejavasenior.dao.ContactDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.DealDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.DealStage;
import com.becomejavasenior.service.impl.DealServiceImpl;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DealServiceImpl.class)
@PowerMockIgnore( {"javax.management.*"})
public class DealServiceTest {

    private static final String SUCCESS_DEALS = "successDeals";
    private static final String FAILED_DEALS = "failedDeals";
    private static final int DEALS_WITH_TASKS_COUNT = 2;
    private static final int DEALS_WITHOUT_TASKS_COUNT = 1;

    private DealServiceImpl dealService;

    @Mock
    private DealDao dealDao;

    @Mock
    private CompanyDao companyDao;

    @Mock
    private ContactDao contactDao;

    @Mock
    private Deal deal;

    @Mock
    private Company company;

    @Mock
    private Contact contact;

    @Mock
    DaoFactoryImpl daoFactory;

    @Before
    public void setUp() throws Exception {
        dealService = new DealServiceImpl();
        dealService.setCompanyDao(companyDao);
        dealService.setContactDao(contactDao);
        dealService.setDealDao(dealDao);
        when(dealDao.create(any(Deal.class))).thenReturn(1);
        when(dealDao.update(any(Deal.class))).thenReturn(1);
        when(dealDao.delete(any(Deal.class))).thenReturn(1);
        when(companyDao.getCompanyForDeal(any(Deal.class))).thenReturn(company);

        List<Contact> newList = new ArrayList<>();
        newList.add(contact);
        newList.add(contact);
        when(contactDao.findAllByCompanyId(anyInt())).thenReturn(newList);

        Deal firstDeal = new Deal();
        firstDeal.setId(1);
        firstDeal.setName("First Deal");

        List<Deal> deals = new LinkedList<>();
        deals.add(firstDeal);

        Deal secondDeal = new Deal();
        secondDeal.setId(2);
        secondDeal.setName("Second Deal");

        deals.add(secondDeal);

        when(dealDao.findAll()).thenReturn(deals);
        when(dealDao.getDealById(1)).thenReturn(firstDeal);
        when(dealDao.getDealById(2)).thenReturn(secondDeal);


    }

    @Test
    public void testCreate() throws Exception {
        dealService.create(deal);
        verify(dealDao).create(deal);
    }

    @Test
    public void testGetDealById() throws Exception {
        Deal resultDeal = dealService.getDealById(1);

        Assert.assertEquals(dealDao.getDealById(1), resultDeal);
    }

    @Test
    public void testUpdate() throws Exception {
        when(dealDao.update(any(Deal.class))).thenReturn(1);
        dealService.update(deal);
        verify(dealDao).update(deal);
    }

    @Test
    public void testDelete() throws Exception {
        when(dealDao.delete(any(Deal.class))).thenReturn(1);
        dealService.delete(deal);
        verify(dealDao).delete(deal);
    }

    @Test
    public void testFindAll() throws DatabaseException {

        List<Deal> result = dealService.findAll();

        verify(dealDao).findAll();
        assertEquals(2, result.size());


    }

    @Test
    public void testFilterSuccessAndFailedDeals(){
        List<Deal> deals = new LinkedList<>();

        Deal firstDeal = new Deal();
        firstDeal.setId(1);
        firstDeal.setName("First Deal");
        firstDeal.setDealStage(DealStage.SUCCESS);

        deals.add(firstDeal);

        Deal secondDeal = new Deal();
        secondDeal.setId(2);
        secondDeal.setName("Second Deal");
        secondDeal.setDealStage(DealStage.FAILED_AND_CLOSED);


        deals.add(secondDeal);


        Map<String, List<Deal>> mapDeals= dealService.filterSuccessAndFailedDeals(deals);


        assertEquals(1, mapDeals.get(SUCCESS_DEALS).size());
        assertEquals(1, mapDeals.get(FAILED_DEALS).size());

    }

    @Test
    public void testCountDealsWithTasks() throws DatabaseException {
        when(dealDao.countDealsWithTasks()).thenReturn(DEALS_WITH_TASKS_COUNT);
        int result = dealService.countDealsWithTasks();

        verify(dealDao).countDealsWithTasks();
        assertEquals(DEALS_WITH_TASKS_COUNT, result);

    }

    @Test
    public void testCountDealsWithoutTasks() throws DatabaseException {
        when(dealDao.countDealsWithoutTasks()).thenReturn(DEALS_WITHOUT_TASKS_COUNT);
        int result = dealService.countDealsWithoutTasks();

        verify(dealDao).countDealsWithoutTasks();
        assertEquals(DEALS_WITHOUT_TASKS_COUNT, result);

    }

    @Test
    public void testGetDealWithContactsAndCompany() throws DatabaseException {

        List<Deal> dealListWithCompanyAndContacts = dealService.getDealListWithCompanyAndContacts();
        Deal deal = dealListWithCompanyAndContacts.get(0);

        verify(dealDao).findAll();
        assertThat(deal, is(notNullValue()));
        assertThat(deal.getCompany(), is(notNullValue()));
        assertThat(deal.getContacts(), hasSize(2));
    }

    @Test
    @Ignore
    public void testGetDealsByStage() throws DatabaseException {
        Deal dealAgreement = dealService.findAll().get(0);
        Deal dealDeciding = dealService.findAll().get(1);

        DealStage agreement = DealStage.AGREEMENT;
        DealStage deciding = DealStage.DECIDING;

        dealAgreement.setDealStage(agreement);
        dealDeciding.setDealStage(deciding);

        Map<String, List<Deal>> dealsByStage = dealService.getDealsByStage();

        assertEquals(dealsByStage.get(agreement).get(0), dealAgreement);
        assertEquals(dealsByStage.get(deciding).get(0), dealDeciding);
    }

}