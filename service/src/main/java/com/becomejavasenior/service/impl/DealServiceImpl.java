package com.becomejavasenior.service.impl;


import com.becomejavasenior.dao.*;
import com.becomejavasenior.dao.impl.CompanyDaoImpl;
import com.becomejavasenior.dao.impl.ContactDaoImpl;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.DealStage;
import com.becomejavasenior.service.DealService;

import java.util.*;

public class DealServiceImpl implements DealService {

    private static final String SUCCESS_DEALS = "successDeals";
    private static final String FAILED_DEALS = "failedDeals";

    private DealDao dealDao;
    private UserDao userDao;
    private CompanyDao companyDao;
    private NoteDao noteDao;
    private FileDao fileDao;
    private TagDao tagDao;
    private ContactDao contactDao;

    public DealDao getDealDao() {
        return dealDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public CompanyDao getCompanyDao() {
        return companyDao;
    }

    public NoteDao getNoteDao() {
        return noteDao;
    }

    public void setNoteDao(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    public FileDao getFileDao() {
        return fileDao;
    }

    public void setFileDao(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    public TagDao getTagDao() {
        return tagDao;
    }

    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public ContactDao getContactDao() {
        return contactDao;
    }

    public void setDealDao(DealDao dealDao) {
        this.dealDao = dealDao;
    }

    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    public void setContactDao(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @Override
    public int create(Deal deal) throws DatabaseException {
        return dealDao.create(deal);
    }

    @Override
    public Deal getDealById(int id) throws DatabaseException {
        return dealDao.getDealById(id);
    }

    @Override
    public Deal getDealWithContactsAndCompany(int id) throws DatabaseException {
        Deal dealFromDB = dealDao.getDealById(id);

        Company companyForDeal = companyDao.getCompanyForDeal(dealFromDB);
        dealFromDB.setCompany(companyForDeal);

        List<Contact> allContactsByCompanyId = contactDao.findAllByCompanyId(dealFromDB.getId());
        dealFromDB.setContacts(allContactsByCompanyId);

        return dealFromDB;
    }

    @Override
    public List<Deal> getDealListWithCompanyAndContacts() throws DatabaseException {
        List<Deal> allDeals = findAll();
        List<Deal> dealsWithContactAndCompany = new ArrayList<>();

        for(Deal deal : allDeals)
        {
            dealsWithContactAndCompany.add(getDealWithContactsAndCompany(deal.getId()));
        }

        return dealsWithContactAndCompany;
    }

    @Override
    public Map<String, List<Deal>> getDealsByStage() throws DatabaseException {
        List<Deal> dealListWithCompanyAndContacts = getDealListWithCompanyAndContacts();

        Map<String, List<Deal>> dealsByStage = new LinkedHashMap<>();
        for(Deal deal : dealListWithCompanyAndContacts)
        {
            String dealStage = deal.getDealStage().name();
            if(dealsByStage.containsKey(dealStage))
            {
                dealsByStage.get(dealStage).add(deal);
            }
            else
            {
                List<Deal> deals = new ArrayList<>();
                deals.add(deal);
                dealsByStage.put(dealStage, deals);
            }

        }
        return dealsByStage;
    }

    @Override
    public int update(Deal deal) throws DatabaseException {
        return dealDao.update(deal);
    }

    @Override
    public int delete(Deal deal) throws DatabaseException {
        return dealDao.delete(deal);
    }

    @Override
    public List<Deal> findAll() throws DatabaseException {
        List<Deal> allDeal = dealDao.findAll();
        for (Deal deal: allDeal)
        {
            fillDealFields(deal);
        }
        return allDeal;
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
        mapDeals.put(SUCCESS_DEALS, successDeals);
        mapDeals.put(FAILED_DEALS, failedDeals);
        return mapDeals;
    }

    @Override
    public int countDealsWithTasks() throws DatabaseException {
        return dealDao.countDealsWithTasks();
    }

    @Override
    public int countDealsWithoutTasks() throws DatabaseException {
        return dealDao.countDealsWithoutTasks();
    }
    public int createWithId(Deal deal) throws DatabaseException {
        return dealDao.createWithId(deal);
    }

    @Override

    public int createDealForContact(int contactId, Deal deal) throws DatabaseException {
        return dealDao.createDealForContact(contactId, deal);
    }

    public int updateDealContact(Deal deal) throws DatabaseException {
        return dealDao.updateDealContact(deal);
    }

    @Override
    public int getMaxId() throws DatabaseException {
        return dealDao.getMaxId();
    }

    private void fillDealFields(Deal deal) throws DatabaseException {
        deal.setTags(tagDao.findAllByDealId(deal.getId()));
        deal.setContacts(contactDao.findAllByDealId(deal.getId()));
        deal.setFiles(fileDao.findAllByDealId(deal.getId()));
        deal.setNotes(noteDao.findAllByDealId(deal.getId()));
        deal.setCreatedByUser(userDao.getCreatedByUserForDeal(deal));
        deal.setCompany(companyDao.getCompanyForDeal(deal));
        deal.setResponsibleUser(userDao.getResponsibleUserForDeal(deal));
    }

}
