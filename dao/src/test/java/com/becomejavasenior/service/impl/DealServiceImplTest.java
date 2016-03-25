package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.dao.impl.DealDaoImpl;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.DealStage;
import com.becomejavasenior.service.DealService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.sql.DataSource;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by aivlev on 3/25/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(DealServiceImpl.class)
public class DealServiceImplTest {

    private static final String SUCCESS_DEALS = "successDeals";
    private static final String FAILED_DEALS = "failedDeals";
    private static final int DEALS_WITH_TASKS_COUNT = 2;
    private static final int DEALS_WITHOUT_TASKS_COUNT = 1;

    private DealService dealService;

    @Mock
    private DealDaoImpl dealDao;

    @Mock
    private Deal deal;

    @Mock
    DaoFactoryImpl daoFactory;

    @Before
    public void init() throws Exception {
        whenNew(DaoFactoryImpl.class).withNoArguments().thenReturn(daoFactory);
        when(daoFactory.getDealDao()).thenReturn(dealDao);
        dealService = new DealServiceImpl();
    }

    @Test
    public void testCreate() throws Exception {
        doNothing().when(dealDao, "create", any(Deal.class));
        dealService.create(deal);

        verify(dealDao).create(deal);
    }

    @Test
    public void testGetDealById() throws Exception {
        when(dealDao.getDealById(1)).thenReturn(deal);

        Deal resultDeal = dealService.getDealById(1);

        verify(dealDao).getDealById(1);
        assertEquals(deal, resultDeal);
    }

    @Test
    public void testUpdate() throws Exception {
        doNothing().when(dealDao, "update", any(Deal.class));
        dealService.update(deal);

        verify(dealDao).update(deal);
    }

    @Test
    public void testDelete() throws Exception {
        doNothing().when(dealDao, "delete", any(Deal.class));
        dealService.delete(deal);

        verify(dealDao).delete(deal);
    }

    @Test
    public void testFindAll() throws DatabaseException {
        List<Deal> deals = new LinkedList<>();

        Deal firstDeal = new Deal();
        firstDeal.setId(1);
        firstDeal.setName("First Deal");

        deals.add(firstDeal);

        Deal secondDeal = new Deal();
        secondDeal.setId(2);
        secondDeal.setName("Second Deal");

        deals.add(secondDeal);

        when(dealDao.findAll()).thenReturn(deals);
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

}