package com.becomejavasenior.filter;


import com.becomejavasenior.dao.CompanyDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Company;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class CompanyFilter {
    private CompanyDao companyDao;
    private static final Logger LOGGER = LogManager.getLogger(CompanyFilter.class);

    public List<Company> filterCompanies(String byTaskType, String byPeriod, String chosenDate, String createdOrModified,
                                          String[] byStages, String byUser, String byTask, String byTag) throws DatabaseException {
        companyDao = new DaoFactoryImpl().getCompanyDao();

        List<Integer> companiesId = new ArrayList<>();
        int companyAmount = companyDao.getCount();
        for (int i = 1; i < companyAmount + 1; i++) companiesId.add(i);

        switch (byTaskType) {
            case "without": companiesId = companyDao.withoutTasks(); break;
            case "outdated": companiesId = companyDao.withOutdatedTasks(); break;
            case "deleted": companiesId = companyDao.markedDelete(); break;
        }

        List<Integer> byPeriodListId;
        switch (byPeriod) {
            case "today" : byPeriodListId = companyDao.byPeriod(convertToTimestampforPeriod(byPeriod, chosenDate), createdOrModified); break;
            case "3 days" : byPeriodListId = companyDao.byPeriod(convertToTimestampforPeriod(byPeriod, chosenDate), createdOrModified); break;
            case "week" : byPeriodListId = companyDao.byPeriod(convertToTimestampforPeriod(byPeriod, chosenDate), createdOrModified); break;
            case "month" : byPeriodListId = companyDao.byPeriod(convertToTimestampforPeriod(byPeriod, chosenDate), createdOrModified); break;
            case "3 months" : byPeriodListId = companyDao.byPeriod(convertToTimestampforPeriod(byPeriod, chosenDate), createdOrModified); break;
            case "calendar" : byPeriodListId = companyDao.byPeriod(convertToTimestampforPeriod(byPeriod, chosenDate), createdOrModified); break;
            default: byPeriodListId = companiesId;
        }

        companiesId = findOverlaps(companiesId, byPeriodListId);

        List<Integer> byUserListId = companyDao.byUser(byUser);

        if (byUser.length() > 0) companiesId = findOverlaps(companiesId, byUserListId);

        List<Integer> byTaskListId;
        switch (byTask) {
            case "today" : byTaskListId = companyDao.byTask(convertToTimestampforTask(byTask)); break;
            case "tomorrow" : byTaskListId = companyDao.byTask(convertToTimestampforTask(byTask)); break;
            case "week" : byTaskListId = companyDao.byTask(convertToTimestampforTask(byTask)); break;
            case "month" : byTaskListId = companyDao.byTask(convertToTimestampforTask(byTask)); break;
            case "3 months" : byTaskListId = companyDao.byTask(convertToTimestampforTask(byTask)); break;
            case "notasks" : byTaskListId = companyDao.withoutTasks(); break;
            case "outdated" : byTaskListId = companyDao.withOutdatedTasks(); break;
            default: byTaskListId = companiesId;
        }

        if (byTask.length() > 0) companiesId = findOverlaps(companiesId, byTaskListId);

        List<Integer> byTagListId = companyDao.byTag(byTag);

        if (byTag.length() > 0) companiesId = findOverlaps(companiesId, byTagListId);


        if (byStages != null) {
            List<Integer> byStageListId = companyDao.byStage(byStages);
            if (byStages.length > 0) companiesId = findOverlaps(companiesId, byStageListId);
        }

        List<Company> companiesList = new ArrayList<>();
        for (int i : companiesId) companiesList.add(companyDao.getCompanyById(i));

        return companiesList;
    }

    private List<Integer> findOverlaps(List<Integer> companiesId, List<Integer> filteredList) {
        Iterator<Integer> i = companiesId.iterator();
        while (i.hasNext())  {
            Integer val = i.next();
            if (!filteredList.contains(val)) i.remove();
        }
        return companiesId;
    }

    private Timestamp convertToTimestampforPeriod(String byPeriod, String chosenDate) {
        Timestamp periodFrom = null;

        switch (byPeriod) {
            case "today" :
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                periodFrom  = new Timestamp(calendar.getTimeInMillis());
                break;

            case "3 days" : periodFrom = new Timestamp(System.currentTimeMillis() - 72 * 60 * 60 * 1000); break;
            case "week" : periodFrom = new Timestamp(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000); break;
            case "month" : periodFrom = new Timestamp(System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000); break;
            case "3 months" :
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                int month = cal.get(cal.MONTH);
                if (month < 3) {
                    cal.set(Calendar.MONTH, Calendar.JANUARY);
                    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
                    periodFrom = new Timestamp(cal.getTimeInMillis()); break;
                }
                else if (month > 2 && month < 6) {
                    cal.set(Calendar.MONTH, Calendar.APRIL);
                    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
                    periodFrom = new Timestamp(cal.getTimeInMillis()); break;
                }
                else if (month > 5 && month < 9) {
                    cal.set(Calendar.MONTH, Calendar.JULY);
                    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
                    periodFrom = new Timestamp(cal.getTimeInMillis()); break;
                }
                else if (month > 8) {
                    cal.set(Calendar.MONTH, Calendar.OCTOBER);
                    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
                    periodFrom = new Timestamp(cal.getTimeInMillis()); break;
                }
            case "calendar" : SimpleDateFormat formatter = new SimpleDateFormat();
                formatter.applyPattern("dd.MM.yyyy");
                try {
                    periodFrom = new Timestamp(formatter.parse(chosenDate).getTime());
                } catch (ParseException e) {
                    LOGGER.error("Couldn't parse the chosen date. Error - {}", new Object[]{e.getMessage()});
                }
        }

        try {
            if (periodFrom == null) throw new DatabaseException();
        }
        catch (DatabaseException e) {
            LOGGER.error("Invalid byPeriod field. Error - {}", new Object[]{e.getMessage()});
        }
        return periodFrom;
    }

    private Timestamp convertToTimestampforTask(String byTask) {
        Timestamp periodFrom = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        switch (byTask) {
            case "today" :
                periodFrom  = new Timestamp(today(calendar).getTimeInMillis());
                break;

            case "tomorrow" :
                periodFrom = new Timestamp(today(calendar).getTimeInMillis() + 24 * 60 * 60 * 1000); break;

            case "week" :
                calendar = today(calendar);
                calendar.set(Calendar.DAY_OF_WEEK, 7);
                periodFrom = new Timestamp(calendar.getTimeInMillis()); break;

            case "month" :
                calendar = today(calendar);
                calendar.set(Calendar.WEEK_OF_MONTH, 4);
                calendar.set(Calendar.DAY_OF_WEEK, 7);
                periodFrom = new Timestamp(calendar.getTimeInMillis()); break;

            case "3 months" :
                int month = calendar.get(calendar.MONTH);
                if (month < 3) {
                    calendar = today(calendar);
                    calendar.set(Calendar.MONTH, Calendar.MARCH);
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                    periodFrom = new Timestamp(calendar.getTimeInMillis()); break;
                }
                else if (month > 2 && month < 6) {
                    calendar = today(calendar);
                    calendar.set(Calendar.MONTH, Calendar.JUNE);
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                    periodFrom = new Timestamp(calendar.getTimeInMillis()); break;
                }
                else if (month > 5 && month < 9) {
                    calendar = today(calendar);
                    calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                    periodFrom = new Timestamp(calendar.getTimeInMillis()); break;
                }
                else if (month > 8) {
                    calendar = today(calendar);
                    calendar.set(Calendar.MONTH, Calendar.DECEMBER);
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                    periodFrom = new Timestamp(calendar.getTimeInMillis()); break;
                }
        }

        try {
            if (periodFrom == null) throw new DatabaseException();
        }
        catch (DatabaseException e) {
            LOGGER.error("Invalid byPeriod field. Error - {}", new Object[]{e.getMessage()});
        }
        return periodFrom;
    }

    private Calendar today(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
}
