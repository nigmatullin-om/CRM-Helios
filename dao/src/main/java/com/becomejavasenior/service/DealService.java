package com.becomejavasenior.service;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Deal;

import java.util.List;
import java.util.Map;

/**
 * Created by aivlev on 3/23/16.
 */
public interface DealService {

    void create(Deal deal) throws DatabaseException;

    Deal getDealById(int id) throws DatabaseException;

    void update(Deal deal) throws DatabaseException;

    void delete(Deal deal) throws DatabaseException;

    List<Deal> findAll() throws DatabaseException;

    Map<String, List<Deal>> filterSuccessAndFailedDeals(List<Deal> deals);

    int countDealsWithTasks() throws DatabaseException;

    int countDealsWithoutTasks() throws DatabaseException;

}
