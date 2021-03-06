package com.becomejavasenior.controller;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Role;
import com.becomejavasenior.model.User;
import com.becomejavasenior.service.*;
import com.becomejavasenior.service.impl.MailServiceImpl;
import com.becomejavasenior.service.impl.RoleServiceImpl;
import com.becomejavasenior.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebServlet("/registration")
public class RegistrationController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(RegistrationController.class);
    private static final String REGISTRATION = "/pages/registration.jsp";
    private static final String EMAIL_EXIST = "Email already exist";
    private static final String PASSWORD_PROMPT = "Passwords do not match";
    private UserService userService;
    private RoleService roleService;
    @Override
    public void init()throws ServletException {
        ApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
        this.userService = ctx.getBean(UserService.class);
        this.roleService = ctx.getBean(RoleService.class);

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = getServletContext().getRequestDispatcher(REGISTRATION);
        rd.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        User user = new User();
        user.setName(request.getParameter("user_name"));
        user.setMobilePhone(request.getParameter("phone"));
        user.setNote(request.getParameter("note"));
        //Passwords inspection
        if(!request.getParameter("password").equals(request.getParameter("password_confirmation"))){
            request.setAttribute("prompt", PASSWORD_PROMPT);
            doGet(request, response);
            return;
        }
        //Email inspection
        try {
            if(!userService.isEmailExist(request.getParameter("email"))){
                user.setEmail(request.getParameter("email"));
            }
            else {
                request.setAttribute("prompt", EMAIL_EXIST);
                doGet(request, response);
                return;
            }
        } catch (DatabaseException e) {
            LOGGER.error("Couldn't get  data!", e.getMessage());
        }
        //create role
        Role role = new Role();
        try {
            role.setId((roleService.getMaxId()));
        } catch (DatabaseException e) {
            LOGGER.error("Couldn't get role data!", e.getMessage());
        }
        role.setName(request.getParameter("role"));
        //add role
        user.setRole(role);
        user.setPassword(request.getParameter("password"));
        try {
            userService.create(user);
        } catch (DatabaseException e) {
            LOGGER.error("Couldn't create users data!", e.getMessage());
        }
        HttpSession session = request.getSession();
        session.setAttribute("User",user);
        LocaleService localeService = new LocaleService(Locale.getDefault());
        String topic = localeService.getString("registrationTopic");
        LOGGER.info("message topic: " + topic);
        String body = localeService.getString("registrationBody");
        LOGGER.info("message body: " + body);
        try {
            new MailServiceImpl().sendRegistrationMail(topic, body, user);
        } catch (MessagingException e) {
            LOGGER.error("error while sending registration message: " + e);
        }
        doGet(request, response);
    }
}
