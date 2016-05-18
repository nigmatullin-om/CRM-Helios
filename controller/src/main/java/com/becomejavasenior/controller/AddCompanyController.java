package com.becomejavasenior.controller;

import com.becomejavasenior.controller.constant.*;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.*;
import com.becomejavasenior.service.CompanyService;
import com.becomejavasenior.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;

@WebServlet("/add/company")
public class AddCompanyController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(AddCompanyController.class);

    private CompanyService companyService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        ApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
        companyService = ctx.getBean(CompanyService.class);
        userService = ctx.getBean(UserService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        req.setAttribute(DealField.STAGES, DealStage.values());
        req.setAttribute(TaskField.PERIODS, Period.values());

        RequestDispatcher rd = getServletContext().getRequestDispatcher(Jsp.JSP_COMPANY_ADD);
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Company company = new Company();
        List<User> users = null;
        try {
            users = userService.findAll();
        } catch (DatabaseException e) {
            LOGGER.error("Couldn't get users data!", e);
        }

        company.setName(req.getParameter(CompanyField.NAME));
        if (req.getParameter(CompanyField.TAGS) != null) {
            company.setTags(InputDataManager.stringToTags(req.getParameter(CompanyField.TAGS)));
        }

        company.setResponsibleUser(
                users.stream()
                        .filter((u) -> u.getId() == Integer.parseInt(req.getParameter(CompanyField.RESPONSIBLE)))
                        .findFirst()
                        .get()
        );


        company.setCreatedByUser(company.getResponsibleUser());
        company.setEmail(req.getParameter(CompanyField.EMAIL));
        company.setWeb(req.getParameter(CompanyField.WEB));
        company.setAddress(req.getParameter(CompanyField.ADDRESS));
        company.setPhoneType(PhoneType.valueOf(req.getParameter(CompanyField.PHONE_TYPE)));
        company.setPhone(req.getParameter(CompanyField.PHONE));

        String noteString = req.getParameter(CompanyField.NOTE);

        if (noteString != null && !"".equals(noteString)) {
            final Note note = new Note();
            note.setText(noteString);
            company.setNotes(Collections.singletonList(note));
        }

        String contactName = req.getParameter(ContactField.NAME);
        if( !"".equals(contactName) && contactName != null) {
            final Contact contact = new Contact();
            contact.setName(contactName);
            contact.setPosition(req.getParameter(ContactField.POSITION));
            contact.setPhoneType(PhoneType.valueOf(req.getParameter(ContactField.PHONE_TYPE)));
            contact.setPhone(req.getParameter(ContactField.PHONE));
            contact.setEmail(req.getParameter(ContactField.EMAIL));
            contact.setSkype(req.getParameter(ContactField.SKYPE));

            company.setContacts(Collections.singletonList(contact));
        }

        String dealName = req.getParameter(DealField.NAME);
        if( !"".equals(dealName) && dealName != null) {
            Deal deal = new Deal();
            deal.setName(dealName);
            deal.setDealStage(DealStage.valueOf(req.getParameter(DealField.STAGE)));
            DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.setParseBigDecimal(true);

            if( !"".equals(req.getParameter(DealField.BUDGET))) {
                BigDecimal budget = null;
                try {
                    budget = (BigDecimal) decimalFormat.parse(req.getParameter(DealField.BUDGET));
                } catch (ParseException e) {
                    LOGGER.error("Parsing error!", e.getMessage());
                }
                deal.setBudget(budget);
            }
        }

        String taskName = req.getParameter(TaskField.NAME);
        if( !"".equals(taskName) && taskName != null) {
            Task task = new Task();
            task.setName(taskName);
           // task.setPeriod(Period.valueOf(req.getParameter(TaskField.PERIOD)));
/*
            try {
                task.setFinishDate(new SimpleDateFormat().parse(req.getParameter(TaskField.DATE)) );
            } catch (ParseException e) {
                e.printStackTrace();
            }
*/
            task.setResponsibleUser(
                    users.stream()
                            .filter((u) -> u.getId() == Integer.parseInt(req.getParameter(TaskField.RESPONSIBLE)))
                            .findFirst()
                            .get()
            );
            task.setDescription(req.getParameter(TaskField.DESCRIPTION));
        }

        try {
            companyService.create(company);
        } catch (DatabaseException e) {
            LOGGER.error("Couldn't save company!", e.getMessage());
        }
        resp.sendRedirect("/dashboard");
    }

}