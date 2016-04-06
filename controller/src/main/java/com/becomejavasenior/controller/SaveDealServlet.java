package com.becomejavasenior.controller;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.*;
import com.becomejavasenior.service.*;
import com.becomejavasenior.service.impl.*;
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

@WebServlet("/saveDeal")
public class SaveDealServlet extends HttpServlet {
    static final Logger log = LogManager.getLogger(SaveDealServlet.class);
    private DealService dealService;
    private ContactService contactService;
    private CompanyService companyService;
    private TaskService taskService;
    private UserService userService;
    private TaskTypeService taskTypeService;

    @Override
    public void init() throws ServletException {
        dealService = new DealServiceImpl();
        contactService = new ContactServiceImpl();
        companyService = new CompanyServiceImpl();
        taskService = new TaskServiceImpl();
        userService = new UserServiceImpl();
        taskTypeService = new TaskTypeServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //------read deal--------------------------------------------
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
            Date dealDate =  dateFormat.parse(req.getParameter("dealDate".trim()));
            log.info("deal date = " + dealDate.toString());
        } catch (ParseException e) {
            log.error("can't parse date" + e);
        }
        Deal deal = new Deal();
        deal.setName(dealName);
        deal.setBudget(dealBudget);
        deal.setDealStage(dealStageEnum);
        //deal.setNote(); // TODO: 06.04.2016  
        deal.setDeleted(false);
        deal.setCreationDate(new Date());
        try {
            deal.setResponsibleUser(userService.getUserById(dealResponsibleId));
            //deal.setCreatedByUser(userService.getUserById(currentUserId)); // TODO: 06.04.2016  
        } catch (DatabaseException e) {
            log.error("error while getting user" + e);
        }
        //tags // TODO: 06.04.2016  
        //deal.setCompany // TODO: 06.04.2016  
        //-----read list of contacts-----------------------------------------

        //----read new contact----------------------------------------------
        String contactName = req.getParameter("contactName");
        Contact contact;
        if (!contactName.equals("")){
            log.info("reading new contact");
            log.info("contact name = " + contactName);
            String contactPosition = req.getParameter("contactPosition");
            log.info("contact position = " + contactPosition);
            int contactPhoneTypeInt = Integer.parseInt(req.getParameter("contactPhoneType"));
            log.info("contact phone type = " + contactPhoneTypeInt);
            PhoneType contactPhoneType = PhoneType.values()[contactPhoneTypeInt];
            String contactPhone = req.getParameter("contactPhone");
            log.info("contact phone = " + contactPhone);
            String contactEmail = req.getParameter("contactEmail");
            log.info("contact email = " + contactEmail);
            String contactSkype = req.getParameter("contactSkype");
            log.info("contact skype = " + contactSkype);
            contact = new Contact();
            contact.setName(contactName);
            contact.setPosition(contactPosition);
            contact.setPhoneType(contactPhoneType);
            contact.setPhone(contactPhone);
            contact.setEmail(contactEmail);
            contact.setSkype(contactSkype);
            contact.setDeleted(false);
            contact.setCreationDate(new Date());
            //contact.setCompany();// TODO: 06.04.2016  
            //contact.setResponsibleUser // TODO: 06.04.2016
        }
        //-----read company from list of companies-----------------------------------------

        //-----read new company-----------------------------------------
        String companyName = req.getParameter("companyName");
        Company company;
        if (!companyName.equals("")){
            log.info("company name = " + companyName);
            String companyPhone = req.getParameter("companyPhone");
            log.info("company phone = " + companyPhone);
            String companyEmail = req.getParameter("companyEmail");
            log.info("company email = " + companyEmail);
            String companyWeb = req.getParameter("companyWeb");
            log.info("company web = " + companyWeb);
            String companyAdress = req.getParameter("companyAdress");
            log.info("company adress = " + companyAdress);
            company = new Company();
            company.setName(companyName);
            company.setAddress(companyAdress);
            company.setEmail(companyEmail);
            company.setPhone(companyPhone);
            company.setWeb(companyWeb);
            company.setCreationDate(new Date());
            //company.setCreatedByUser(); // TODO: 06.04.2016
            //company.setResponsibleUser();// TODO: 06.04.2016
        }

        //-----read new task-----------------------------------------
        String taskPeriod = req.getParameter("taskPeriod");
        log.info("task period" + taskPeriod);
        String taskDateStr = req.getParameter("taskDate");
        log.info("task Date" + taskDateStr);
        String taskTimeStr = req.getParameter("taskTime");
        log.info("task Time" + taskTimeStr);
        Task task;
        Date finishDate;
        Period taskPeriodEnum = null;
        if (taskPeriod !="-1" || taskTimeStr !="" && taskDateStr != ""){
            if (taskPeriod !="-1"){
                taskPeriodEnum = Period.values()[Integer.parseInt(taskPeriod)];
            }
            else {
                try {
                    Date taskDate =  dateFormat.parse(req.getParameter("dealDate".trim()));
                    log.info("deal date = " + taskDate.toString());
                    Date taskTime =  dateFormat.parse(req.getParameter("dealTime".trim()));
                    log.info("deal time = " + taskDate.toString());
                    finishDate = new Date(taskDate.getTime() + taskTime.getTime());
                } catch (ParseException e) {
                    log.error("can't parse task date" + e);
                }
            }
            User responsibleUser = null;
            User createdByUser = null;
            TaskType taskType = null;
            try {
                responsibleUser = userService.getUserById(Integer.parseInt(req.getParameter("taskResponsible")));
                //createdByUser = userService.getUserById())); // TODO: 06.04.2016
                taskType = taskTypeService.getTaskTypeById(Integer.parseInt(req.getParameter("taskType")));

            } catch (DatabaseException e) {
                log.error("error while getting data from DB" + e);
            }
            String taskText = req.getParameter("taskText");
            log.info("task text = " + taskText);

            task = new Task();
            task.setPeriod(taskPeriodEnum);
            task.setTaskType(taskType);
            task.setCreatedByUser(createdByUser);
            task.setResponsibleUser(responsibleUser);
            task.setDescription(taskText);
            task.setDeleted(false);
            task.setDone(false);
        }

        //---saving to DB----------
    }
}
