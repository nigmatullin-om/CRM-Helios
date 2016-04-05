package com.becomejavasenior.controller;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.*;
import com.becomejavasenior.service.CompanyService;
import com.becomejavasenior.service.ContactService;
import com.becomejavasenior.service.UserService;
import com.becomejavasenior.service.impl.CompanyServiceImpl;
import com.becomejavasenior.service.impl.ContactServiceImpl;
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

@WebServlet("/addDeal")
public class AddDealServlet extends HttpServlet {
    static final Logger log = LogManager.getLogger(AddDealServlet.class);
    private UserService userService;
    private ContactService contactService;
    private CompanyService companyService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserServiceImpl();
        this.contactService = new ContactServiceImpl();
        this.companyService = new CompanyServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();

        List<EnumAdapter> stages = new ArrayList<EnumAdapter>();
        stages.add(new EnumAdapter(0, "Первичный контакт"));
        stages.add(new EnumAdapter(1, "Переговоры"));
        stages.add(new EnumAdapter(2, "Принятие решения"));
        stages.add(new EnumAdapter(3, "Подписание сделки"));
        stages.add(new EnumAdapter(4, "Успешно завершена"));
        stages.add(new EnumAdapter(5, "Не завершено и закрыто"));
        log.info("deal stages: " + stages.toString());
        servletContext.setAttribute("dealStages", stages);

        List<EnumAdapter> phoneTypes = new ArrayList<EnumAdapter>();
        phoneTypes.add(new EnumAdapter(0, "Рабочий"));
        phoneTypes.add(new EnumAdapter(1, "Прямой рабочий номер"));
        phoneTypes.add(new EnumAdapter(2, "Домашний"));
        phoneTypes.add(new EnumAdapter(3, "Мобильный"));
        phoneTypes.add(new EnumAdapter(4, "Домашний"));
        phoneTypes.add(new EnumAdapter(5, "Другое"));
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

        RequestDispatcher requestDispatcher =  getServletContext().getRequestDispatcher("/pages/addDeal.jsp");
        log.info("forwarding to /addDeal.jsp");
        requestDispatcher.forward(req, resp);
    }
}
