package com.becomejavasenior.service;

import com.becomejavasenior.dao.impl.DatabaseException;

/**
 * Created by aivlev on 3/23/16.
 */
public interface CompanyService {

    int getCount() throws DatabaseException;

}
