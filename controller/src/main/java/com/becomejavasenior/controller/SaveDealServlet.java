package com.becomejavasenior.controller;

import com.becomejavasenior.model.Deal;
import com.becomejavasenior.model.DealStage;
import com.becomejavasenior.model.PhoneType;
import com.becomejavasenior.service.CompanyService;
import com.becomejavasenior.service.ContactService;
import com.becomejavasenior.service.DealService;
import com.becomejavasenior.service.TaskService;
import com.becomejavasenior.service.impl.CompanyServiceImpl;
import com.becomejavasenior.service.impl.ContactServiceImpl;
import com.becomejavasenior.service.impl.DealServiceImpl;
import com.becomejavasenior.service.impl.TaskServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@WebServlet("/saveDeal")
public class SaveDealServlet extends HttpServlet {
    static final Logger log = LogManager.getLogger(SaveDealServlet.class);
    private DealService dealService;
    private ContactService contactService;
    private CompanyService companyService;
    private TaskService taskService;

    @Override
    public void init() throws ServletException {
        dealService = new DealServiceImpl();
        contactService = new ContactServiceImpl();
        companyService = new CompanyServiceImpl();
        taskService = new TaskServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dealName = req.getParameter("dealName");
        log.info("deal name = " + dealName);
        String dealTags = req.getParameter("dealTags");
        log.info("deal tags = " + dealTags);
        int dealResponsibleId = Integer.parseInt(req.getParameter("dealResponsible"));
        log.info("deal responsible id = " + dealResponsibleId);
        BigDecimal dealBudget =  new BigDecimal(Float.parseFloat(req.getParameter("dealBudget")));
        log.info("deal budget = " + dealBudget);
        int dealStage = Integer.parseInt(req.getParameter("dealStage"));
        DealStage dealStageEnum = DealStage.values()[dealStage];
        log.info("deal stage = " + dealStageEnum.name());
        String dealNote = req.getParameter("dealNote");
        log.info("deal note = " + dealNote);
        //---------------------------------------------------------
        DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
        try {
            Date dealDate =  dateFormat.parse(req.getParameter("dealDate"));
            log.info("deal date = " + dealDate.toLocaleString());
        } catch (ParseException e) {
            log.error("can't parse date" + e);
        }
        //-----read list of contacts-----------------------------------------

        //----read new contact----------------------------------------------
        String contactName = req.getParameter("contactName");
        log.info("contact name = " + contactName);
        String contactPosition = req.getParameter("contactPosition");
        log.info("contact position = " + contactPosition);
        int contactPhoneTypeInt = Integer.parseInt(req.getParameter("contactPhoneType"));
        log.info("contact phone type = " + contactPhoneTypeInt);
        PhoneType contactPhoneType;
        if (contactPhoneTypeInt != -1){
            contactPhoneType = PhoneType.values()[contactPhoneTypeInt];
        }
        String contactPhone = req.getParameter("contactPhone");
        log.info("contact phone = " + contactPhone);
        String contactSkype = req.getParameter("contactSkype");
        log.info("contact skype = " + contactSkype);

    }
}
