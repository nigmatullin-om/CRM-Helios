package com.becomejavasenior.controller;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.TagDao;
import com.becomejavasenior.dao.UserDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.filter.CompanyFilter;
import com.becomejavasenior.model.*;
import com.becomejavasenior.service.*;
import com.becomejavasenior.service.impl.CompanyServiceImpl;
import com.becomejavasenior.service.impl.ContactServiceImpl;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.becomejavasenior.service.impl.TaskServiceImpl;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


@WebServlet("/view/CompaniesList")
public class CompaniesListController extends HttpServlet {
    private static final String COMPANIES_LIST = "/pages/view/CompaniesList.jsp";

    private static final String CONTACTS = "contacts";
    private static final String COMPANIES = "companies";
    private static final String TASKS = "tasks";
    private static final String TAGS = "tags";
    private static final String USERS = "users";
    private static final String STAGES = "stages";

    private ContactService contactService;
    private CompanyService companyService;
    private TaskService taskService;
    private TagService tagService;
    private UserService userService;

    private RequestDispatcher rd;

    private static final Logger LOGGER = LogManager.getLogger(CompaniesListController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Task> tasks = null;
        List<Tag> tags = null;
        List<User> users = null;
        List<String> stages = null;
        List<Contact> contacts = null;
        List<Company> companies = null;

        try {
            contacts = contactService.findAll();
            companies = companyService.findAll();
            users = userService.findAll();
            tasks = taskService.findAll();
            tags = tagService.findAllByAllContacts();
            stages = DealStage.getAllDealNames();

        } catch (DatabaseException e) {
            LOGGER.error("Couldn't get the list of entity. Error - {}", new Object[]{e.getMessage()});
        }

        request.setAttribute(COMPANIES, companies);
        request.setAttribute(CONTACTS, contacts);
        request.setAttribute(TASKS, tasks);
        request.setAttribute(TAGS, tags);
        request.setAttribute(USERS, users);
        request.setAttribute(STAGES, stages);

        rd.forward(request, response);
    }

    @Override
    public void init() throws ServletException {
        ApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
        this.userService = ctx.getBean(UserService.class);
        contactService = ctx.getBean(ContactService.class);
        companyService =ctx.getBean(CompanyService.class);
        taskService = ctx.getBean(TaskService.class);
        tagService = ctx.getBean(TagService.class);
        rd = getServletContext().getRequestDispatcher(COMPANIES_LIST);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String byTaskType = request.getParameter("byTaskType");
        String byPeriod = request.getParameter("byPeriod");
        String chosenDate = request.getParameter("chosenDate");
        String createdOrModified = request.getParameter("createdOrModified");
        String[] byStages = request.getParameterValues("byStages[]");
        String byUser = request.getParameter("byUser");
        String byTask = request.getParameter("byTask");
        String byTag = request.getParameter("byTag");

        List<Company> companies = null;

        CompanyFilter companyFilter = new CompanyFilter();

        try {
            companies = companyFilter.filterCompanies(byTaskType, byPeriod, chosenDate, createdOrModified,
                    byStages, byUser, byTask, byTag);
        } catch (DatabaseException e) {
            LOGGER.error("Couldn't get the list of companies. Error - {}", new Object[]{e.getMessage()});
        }
        String json = new Gson().toJson(companies);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }


}
