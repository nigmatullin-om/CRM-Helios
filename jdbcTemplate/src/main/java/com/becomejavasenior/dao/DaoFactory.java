package com.becomejavasenior.dao;

public interface DaoFactory {
    CompanyDao getCompanyDao();
    ContactDao getContactDao();
    DealDao getDealDao();
    TaskDao getTaskDao();
}
