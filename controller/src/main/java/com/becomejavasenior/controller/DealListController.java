package com.becomejavasenior.controller;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Deal;
import com.becomejavasenior.service.DealService;
import com.becomejavasenior.service.impl.DealServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@WebServlet("/view/deals")
public class DealListController extends HttpServlet {
    private static final String DEAL_LIST_JSP = "/pages/deal/dealList.jsp";
    private static final String DEAL_FUNNEL_JSP = "/pages/deal/dealsFunnel.jsp";

    private static final String DEALS = "deals";
    private static final String  DEALS_BY_STAGE = "dealsByStage";

    public static final String LIST_VIEW = "list";
    public static final String FUNNEL_VIEW = "funnel";
    public static final String VIEW = "view";

    private DealService dealService;


    private static final Logger LOGGER = LogManager.getLogger(DealListController.class);

    @Override
    public void init() throws ServletException {
        this.dealService = new DealServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewType = getViewType(request);

        RequestDispatcher rd;
        switch (viewType) {
            case FUNNEL_VIEW: {
                Map<String, List<Deal>> dealsByStage = new HashMap<>();
                try {
                  dealsByStage = dealService.getDealsByStage();
                } catch (DatabaseException e) {
                    LOGGER.error(e.getMessage());
                }
                request.setAttribute(DEALS_BY_STAGE,  dealsByStage);
                rd = getServletContext().getRequestDispatcher(DEAL_FUNNEL_JSP);
                rd.forward(request, response);
                break;
            }
            default: {
                List<Deal> deals = new ArrayList<>();
                try {
                    deals = dealService.getDealListWithCompanyAndContacts();
                } catch (DatabaseException e) {
                    LOGGER.error(e.getMessage());
                }
                request.setAttribute(DEALS, deals);
                rd = getServletContext().getRequestDispatcher(DEAL_LIST_JSP);
                rd.forward(request, response);
            }
        }

    }

    private String getViewType(HttpServletRequest request) {
        String viewType = request.getParameter(VIEW);
        if (viewType == null)
            viewType = LIST_VIEW;
        return viewType;
    }
}