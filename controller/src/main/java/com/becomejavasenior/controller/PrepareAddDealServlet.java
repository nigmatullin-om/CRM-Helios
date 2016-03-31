package com.becomejavasenior.controller;

import com.becomejavasenior.model.DealStage;
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
public class PrepareAddDealServlet extends HttpServlet {
    static final Logger log = LogManager.getLogger(PrepareAddDealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();

        List<String> stages = new ArrayList<String>();
        for (DealStage stage : DealStage.values()){
            stages.add(stage.name());
        }
        log.info("deal stages: " + stages.toString());
        servletContext.setAttribute("dealStages", stages);







        RequestDispatcher requestDispatcher =  getServletContext().getRequestDispatcher("/addDeal.jsp");
        log.info("forwarding to /addDeal.jsp");
        requestDispatcher.forward(req, resp);

    }
}
