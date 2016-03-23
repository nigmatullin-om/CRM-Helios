package com.becomejavasenior.service;

import com.becomejavasenior.dao.impl.DatabaseException;
import com.becomejavasenior.model.Deal;

import java.util.List;
import java.util.Map;

/**
 * Created by aivlev on 3/23/16.
 */
public interface DealService {

    List<Deal> findAll() throws DatabaseException;

    Map<String, List<Deal>> filterSuccessAndFailedDeals(List<Deal> deals);

}
