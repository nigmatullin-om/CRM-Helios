package com.becomejavasenior.service.impl;


import com.becomejavasenior.dao.*;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.DealStage;
import com.becomejavasenior.service.DealService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class DealServiceImpl implements DealService {

    private static final String SUCCESS_DEALS = "successDeals";
    private static final String FAILED_DEALS = "failedDeals";

    @Resource
    private DealDao dealDao;

    @Resource
    private UserDao userDao;

    @Resource
    private CompanyDao companyDao;

    @Resource
    private NoteDao noteDao;

    @Resource
    private FileDao fileDao;

    @Resource
    private TagDao tagDao;

    @Resource
    private ContactDao contactDao;

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

        for (Deal deal : allDeals) {
            dealsWithContactAndCompany.add(getDealWithContactsAndCompany(deal.getId()));
        }

        return dealsWithContactAndCompany;
    }

    @Override
    public Map<String, List<Deal>> getDealsByStage() throws DatabaseException {
        List<Deal> dealListWithCompanyAndContacts = getDealListWithCompanyAndContacts();

        Map<String, List<Deal>> dealsByStage = new LinkedHashMap<>();
        for (Deal deal : dealListWithCompanyAndContacts) {
            String dealStage = deal.getDealStage().name();
            if (dealsByStage.containsKey(dealStage)) {
                dealsByStage.get(dealStage).add(deal);
            } else {
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
        for (Deal deal : allDeal) {
            fillDealFields(deal);
        }
        return allDeal;
    }

    public Map<String, List<Deal>> filterSuccessAndFailedDeals(List<Deal> deals) {
        Map<String, List<Deal>> mapDeals = new HashMap<>();

        List<Deal> successDeals = deals.stream().filter(deal -> deal.getDealStage().equals(DealStage.SUCCESS)).
                collect(Collectors.toCollection(LinkedList::new));
        List<Deal> failedDeals = deals.stream().filter(deal -> deal.getDealStage().equals(DealStage.FAILED_AND_CLOSED)).
                collect(Collectors.toCollection(LinkedList::new));

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
