package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.CurrenciesDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Currencies;
import com.becomejavasenior.service.CurrenciesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CurrenciesServiceImpl implements CurrenciesService {

    @Resource
    private CurrenciesDao currenciesDao;

    @Override
    public int create(Currencies currencies) throws DatabaseException {
        return currenciesDao.create(currencies);
    }

    @Override
    public Currencies read(int id) throws DatabaseException {
        return currenciesDao.read(id);
    }

    @Override
    public int update(Currencies currencies) throws DatabaseException {
        return currenciesDao.update(currencies);
    }

    @Override
    public int delete(Currencies currencies) throws DatabaseException {
        return currenciesDao.delete(currencies);
    }

    @Override
    public List<Currencies> findAll() throws DatabaseException {
        return currenciesDao.findAll();
    }

    @Override
    public Currencies getActiveCurrencies() throws DatabaseException {
        return currenciesDao.getActiveCurrencies();
    }
}
