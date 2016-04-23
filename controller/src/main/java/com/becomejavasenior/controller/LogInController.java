package com.becomejavasenior.controller;


import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.User;
import com.becomejavasenior.service.UserService;
import com.becomejavasenior.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebServlet("/login")
public class LogInController extends HttpServlet{
    private static final Logger LOGGER = LogManager.getLogger(LogInController.class);
    private static final String AUTHORIZATION = "/pages/login.jsp";
    private static final String WRONG_PASS_EMAIL = "Wrong pass/email";
    private UserService userService;
    @Override
    public void init()throws ServletException{
        userService = new UserServiceImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = getServletContext().getRequestDispatcher(AUTHORIZATION);
        rd.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        User user = null;
        String nameUser = request.getParameter("userName");
        String passwordUser = request.getParameter("userPassword");
        try {
            user = userService.getUserByName(nameUser);
        } catch (DatabaseException e) {
            LOGGER.error("Couldn't get users data!", e.getMessage());
        }
        if(user!= null && user.getName().equals(nameUser)){
            if(user.getPassword().equals(passwordUser.trim())){
                HttpSession session = request.getSession();
                session.setAttribute("User",user);
                response.sendRedirect("/pages/index.jsp");
                request.setAttribute("wrong_pass_or_email","");
                return;
            }
        }else{
            request.setAttribute("wrong_pass_or_email",WRONG_PASS_EMAIL);
        }
        doGet(request,response);
    }
}
