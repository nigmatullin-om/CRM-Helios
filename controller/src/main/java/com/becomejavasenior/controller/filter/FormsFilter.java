package com.becomejavasenior.controller.filter;

import com.becomejavasenior.controller.constant.CommonField;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.PhoneType;
import com.becomejavasenior.model.User;
import com.becomejavasenior.service.UserService;
import com.becomejavasenior.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@WebFilter(urlPatterns = {"/add/*", "/view/*"},
        dispatcherTypes = DispatcherType.REQUEST)
public class FormsFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(FormsFilter.class);
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(filterConfig.getServletContext());
        this.userService = ctx.getBean(UserService.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (((HttpServletRequest) request).getMethod().equalsIgnoreCase("POST")) {
            List<User> users = null;
            try {
                users = userService.findAll();
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
