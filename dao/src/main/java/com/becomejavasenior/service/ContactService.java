package com.becomejavasenior.service;

import com.becomejavasenior.dao.DatabaseException;

/**
 * Created by aivlev on 3/23/16.
 */
public interface ContactService {

    int getCount() throws DatabaseException;
}
