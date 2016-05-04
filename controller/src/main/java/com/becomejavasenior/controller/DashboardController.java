package com.becomejavasenior.controller;

import com.becomejavasenior.controller.constant.*;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.Task;
import com.becomejavasenior.service.CompanyService;
import com.becomejavasenior.service.ContactService;
import com.becomejavasenior.service.DealService;
import com.becomejavasenior.service.TaskService;
import com.becomejavasenior.service.impl.CompanyServiceImpl;
import com.becomejavasenior.service.impl.ContactServiceImpl;
import com.becomejavasenior.service.impl.DealServiceImpl;
import com.becomejavasenior.service.impl.TaskServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by aivlev on 3/20/16.
 */
@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {

    private static final String SUCCESS_DEALS = "successDeals";
    private static final String FAILED_DEALS = "failedDeals";
    private static final String DONE_TASKS = "doneTasks";
    private static final String NOT_DONE_TASKS = "notDoneTasks";
    private static final String DEALS = "deals";
    private static final String SUCCESS_DEALS_COUNT = "successDealsCount";
    private static final String FAILED_DEALS_COUNT = "failedDealsCount";
    private static final String CONTACTS_COUNT = "contactsCount";
    private static final String COMPANIES_COUNT = "companiesCount";
    private static final String DEALS_WITH_TASKS_COUNT = "dealsWithTasksCount";
    private static final String DEALS_WITHOUT_TASKS_COUNT = "dealsWithoutTasksCount";
    private static final String RUNNING_TASKS_COUNT = "runningTasksCount";
    private static final String DONE_TASKS_COUNT = "doneTasksCount";
    private static final String NOT_DONE_TASKS_COUNT = "notDoneTasksCount";


    private DealService dealService;
    private ContactService contactService;
    private CompanyService companyService;
    private TaskService taskService;

    @Override
    public void init() throws ServletException {
        this.dealService = new DealServiceImpl();
        this.contactService = new ContactServiceImpl();
        this.companyService = new CompanyServiceImpl();
        this.taskService = new TaskServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Deal> deals = null;
        List<Task> tasks = null;
        Map<String, List<Task>> mapTasks;
        List<Task> notDoneTasks = null;
        List<Task> runningTasks;
        int contactsCount = 0;
        int companiesCount = 0;
        int successDealsCount = 0;
        int failedDealsCount = 0;
        int dealsWithTasksCount = 0;
        int dealsWithoutTasksCount = 0;
        int doneTasksCount = 0;
        int notDoneTasksCount = 0;
        try {
            deals = dealService.findAll();
            tasks = taskService.findAll();
            contactsCount = contactService.getCount();
            companiesCount = companyService.getCount();
            dealsWithTasksCount = dealService.countDealsWithTasks();
            dealsWithoutTasksCount = dealService.countDealsWithoutTasks();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        if(null != deals && deals.size() > 0){
            Map<String, List<Deal>> mapDeals = dealService.filterSuccessAndFailedDeals(deals);
            successDealsCount = mapDeals.get(SUCCESS_DEALS).size();
            failedDealsCount = mapDeals.get(FAILED_DEALS).size();
        }
        if(null != tasks && tasks.size() > 0){
            mapTasks = taskService.filterTasksByDone(tasks);
            notDoneTasks = mapTasks.get(NOT_DONE_TASKS);
            doneTasksCount = mapTasks.get(DONE_TASKS).size();
            notDoneTasksCount = notDoneTasks.size();
        }
        runningTasks = taskService.getRunningTasks(notDoneTasks);

        request.setAttribute(DEALS, deals);
        request.setAttribute(SUCCESS_DEALS_COUNT, successDealsCount);
        request.setAttribute(FAILED_DEALS_COUNT, failedDealsCount);
        request.setAttribute(CONTACTS_COUNT, contactsCount);
        request.setAttribute(COMPANIES_COUNT, companiesCount);
        request.setAttribute(DEALS_WITH_TASKS_COUNT, dealsWithTasksCount);
        request.setAttribute(DEALS_WITHOUT_TASKS_COUNT, dealsWithoutTasksCount);
        request.setAttribute(RUNNING_TASKS_COUNT, runningTasks.size());
        request.setAttribute(DONE_TASKS_COUNT, doneTasksCount);
        request.setAttribute(NOT_DONE_TASKS_COUNT, notDoneTasksCount);
        RequestDispatcher rd = getServletContext().getRequestDispatcher(Jsp.JSP_DASHBOARD);
        rd.forward(request, response);
    }

}
