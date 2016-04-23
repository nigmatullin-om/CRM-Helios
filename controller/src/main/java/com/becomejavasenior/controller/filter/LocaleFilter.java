package com.becomejavasenior.controller.filter;

import com.becomejavasenior.controller.InputDataManager;
import com.becomejavasenior.controller.LocaleService;
import com.becomejavasenior.controller.constant.CommonField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebFilter("/tbd/*")
public class LocaleFilter implements Filter{

    private static final Logger LOGGER = LogManager.getLogger(LocaleFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        Locale userLocale = InputDataManager.getLocale(req);
        LOGGER.debug("User locale: " + userLocale);
        LocaleService localeService = new LocaleService(userLocale);
      //  resp.addCookie(InputDataManager.getLocaleCookie(userLocale));

        req.setAttribute(CommonField.LOCALE_TEXT, localeService.getResources());

        chain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
}
