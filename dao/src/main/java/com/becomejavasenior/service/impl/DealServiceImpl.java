package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.DaoFactory;
import com.becomejavasenior.dao.DealDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.dao.impl.DatabaseException;
import com.becomejavasenior.dao.impl.DealDaoImpl;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.DealStage;
import com.becomejavasenior.service.DealService;

import javax.sql.DataSource;
import java.util.*;

/**
 * Created by aivlev on 3/23/16.
 */
public class DealServiceImpl implements DealService {

    private DealDao dealDao;

    public DealServiceImpl() {
        this.dealDao = new DaoFactoryImpl().getDealDao();
    }

    @Override
    public List<Deal> findAll() throws DatabaseException {
        return dealDao.findAll();
    }

    public Map<String, List<Deal>> filterSuccessAndFailedDeals(List<Deal> deals) {
        Map<String, List<Deal>> mapDeals = new HashMap<>();
        List<Deal> successDeals = new LinkedList<>();
        List<Deal> failedDeals = new LinkedList<>();
        ListIterator<Deal> listIterator = deals.listIterator();
        while(listIterator.hasNext()){
            Deal tempDeal = listIterator.next();
            if(tempDeal.getDealStage() == DealStage.SUCCESS){
                successDeals.add(tempDeal);
            }
            if(tempDeal.getDealStage() == DealStage.FAILED_AND_CLOSED){
                failedDeals.add(tempDeal);
            }
        }
        mapDeals.put("successDeals", successDeals);
        mapDeals.put("failedDeals", failedDeals);
        return mapDeals;
    }
}
