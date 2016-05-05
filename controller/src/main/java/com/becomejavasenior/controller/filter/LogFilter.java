package com.becomejavasenior.controller.filter;

import com.becomejavasenior.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns ="/*",
            filterName = "logFilter"
    )
    public class LogFilter implements Filter{
        private String LOGIN_URI = "/login";
        private String REGISTRATION_URL  = "/registration";
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpSession session =  httpRequest.getSession();
            User user  = (User) session.getAttribute("User");
            if((user == null && !LOGIN_URI.equals( httpRequest.getRequestURI()))
                    && !REGISTRATION_URL.equals(httpRequest.getRequestURI())){
                RequestDispatcher rd = httpRequest.getRequestDispatcher("/login");
                rd.forward(request, response);

            }
            chain.doFilter(request,response);
        }

        @Override
        public void destroy() {

        }
}
