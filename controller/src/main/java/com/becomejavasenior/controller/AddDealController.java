package com.becomejavasenior.controller;

import com.becomejavasenior.controller.constant.Jsp;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.*;
import com.becomejavasenior.service.CompanyService;
import com.becomejavasenior.service.ContactService;
import com.becomejavasenior.service.TaskTypeService;
import com.becomejavasenior.service.UserService;
import com.becomejavasenior.service.impl.CompanyServiceImpl;
import com.becomejavasenior.service.impl.ContactServiceImpl;
import com.becomejavasenior.service.impl.TaskTypeServiceImpl;
import com.becomejavasenior.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/add/deal")
public class AddDealController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(AddDealController.class);
    private UserService userService;
    private ContactService contactService;
    private CompanyService companyService;
    private TaskTypeService taskTypeService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserServiceImpl();
        this.contactService = new ContactServiceImpl();
        this.companyService = new CompanyServiceImpl();
        this.taskTypeService = new TaskTypeServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();

        List<EnumAdapter> stages = new ArrayList<EnumAdapter>();
        stages.add(new EnumAdapter(1, "Первичный контакт"));
        stages.add(new EnumAdapter(2, "Переговоры"));
        stages.add(new EnumAdapter(3, "Принятие решения"));
        stages.add(new EnumAdapter(4, "Подписание сделки"));
        stages.add(new EnumAdapter(5, "Успешно завершена"));
        stages.add(new EnumAdapter(6, "Не завершено и закрыто"));
        log.info("deal stages: " + stages.toString());
        servletContext.setAttribute("dealStages", stages);

        List<EnumAdapter> phoneTypes = new ArrayList<EnumAdapter>();
        phoneTypes.add(new EnumAdapter(1, "Рабочий"));
        phoneTypes.add(new EnumAdapter(2, "Прямой рабочий номер"));
        phoneTypes.add(new EnumAdapter(3, "Домашний"));
        phoneTypes.add(new EnumAdapter(4, "Мобильный"));
        phoneTypes.add(new EnumAdapter(5, "Домашний"));
        phoneTypes.add(new EnumAdapter(6, "Другое"));
        log.info("phone types: " + phoneTypes.toString());
        servletContext.setAttribute("phoneTypes", phoneTypes);

        List<EnumAdapter> periods = new ArrayList<>();
        periods.add(new EnumAdapter(0, "Сегодня"));
        periods.add(new EnumAdapter(1, "Весь день"));
        periods.add(new EnumAdapter(2, "Завтра"));
        periods.add(new EnumAdapter(3, "Следующая неделя"));
        periods.add(new EnumAdapter(4, "Следующий месяц"));
        periods.add(new EnumAdapter(5, "Следующий год"));
        log.info("periods: " + periods.toString());
        servletContext.setAttribute("periods", periods);

        List<User> users = null;
        try {
            log.info("trying to get users");
            users = userService.findAll();
        } catch (DatabaseException e) {
            log.error("error while trying get users" + e);
        }
        log.info("users: " + users.toString());
        servletContext.setAttribute("users", users);

        List<Contact> contacts = null;
        try {
            log.info("trying to get contacts");
            contacts = contactService.findAll();
        } catch (DatabaseException e) {
            log.error("error while trying get contacts" + e);
        }
        log.info("contacts: " + contacts.toString());
        servletContext.setAttribute("contacts", contacts);

        List<Company> companies = new ArrayList<>();
        try {
            companies = companyService.findAll();
        } catch (DatabaseException e) {
            log.error("error while trying get companies" + e);
        }
        log.info("companies: " + companies);
        servletContext.setAttribute("companies", companies);

        List<TaskType> taskTypes = new ArrayList<>();
        try {
            taskTypes = taskTypeService.findAll();
        } catch (DatabaseException e) {
            log.error("error while trying get task types" + e);
        }
        log.info("task types: " + taskTypes);
        servletContext.setAttribute("taskTypes", taskTypes);

        RequestDispatcher requestDispatcher =  getServletContext().getRequestDispatcher(Jsp.DEAL_JSP);
        log.info("forwarding to " + Jsp.DEAL_JSP);
        requestDispatcher.forward(req, resp);
    }
}
