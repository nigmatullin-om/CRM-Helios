package com.becomejavasenior.controller.filter;

import com.becomejavasenior.controller.InputDataManager;
import com.becomejavasenior.controller.LocaleService;
import com.becomejavasenior.controller.constant.CommonField;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.PhoneType;
import com.becomejavasenior.model.User;
import com.becomejavasenior.service.UserService;
import com.becomejavasenior.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@WebFilter(urlPatterns = {"/add/*", "/view/*"},
        dispatcherTypes = DispatcherType.REQUEST)
public class FormsFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(FormsFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (((HttpServletRequest) request).getMethod().equalsIgnoreCase("POST")) {
            List<User> users = null;
            try {
                users = new UserServiceImpl().findAll();
            } catch (DatabaseException e) {
                LOGGER.error("Couldn't get users data!", e);
            }
            request.setAttribute(CommonField.USERS_LIST, users);
            request.setAttribute(CommonField.PHONE_TYPES, PhoneType.values());
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
