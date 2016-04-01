package com.becomejavasenior.controller;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.DealStage;
import com.becomejavasenior.model.PhoneType;
import com.becomejavasenior.model.User;
import com.becomejavasenior.service.ContactService;
import com.becomejavasenior.service.UserService;
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

    @Override
    public void init() throws ServletException {
        this.userService = new UserServiceImpl();
        this.contactService = new ContactServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();

        List<EnumAdapter> stages = new ArrayList<EnumAdapter>();
        for (DealStage stage : DealStage.values()){
            EnumAdapter enumAdapter = new EnumAdapter(stage.ordinal(), stage.name());
            stages.add(enumAdapter);
        }
        log.info("deal stages: " + stages.toString());
        servletContext.setAttribute("dealStages", stages);

        List<EnumAdapter> phoneTypes = new ArrayList<EnumAdapter>();
        for (PhoneType phoneType : PhoneType.values()){
            EnumAdapter enumAdapter = new EnumAdapter(phoneType.ordinal(), phoneType.name());
            phoneTypes.add(enumAdapter);
        }
        log.info("phone types: " + phoneTypes.toString());
        servletContext.setAttribute("phoneTypes", phoneTypes);

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


        RequestDispatcher requestDispatcher =  getServletContext().getRequestDispatcher("/pages/addDeal.jsp");
        log.info("forwarding to /addDeal.jsp");
        requestDispatcher.forward(req, resp);

    }
}
