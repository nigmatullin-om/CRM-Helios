package com.becomejavasenior.controller;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.*;
import com.becomejavasenior.service.*;
import com.becomejavasenior.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/saveDeal")
@MultipartConfig
public class SaveDealController extends HttpServlet {

    private static final Logger log = LogManager.getLogger(SaveDealController.class);
    private DealService dealService;
    private ContactService contactService;
    private CompanyService companyService;
    private TaskService taskService;
    private UserService userService;
    private TaskTypeService taskTypeService;
    private NoteService noteService;
    private FileService fileService;


    @Override
    public void init() throws ServletException {
        dealService = new DealServiceImpl();
        contactService = new ContactServiceImpl();
        companyService = new CompanyServiceImpl();
        taskService = new TaskServiceImpl();
        userService = new UserServiceImpl();
        taskTypeService = new TaskTypeServiceImpl();
        noteService = new NoteServiceImpl();
        fileService = new FileServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Deal deal = getDeal(req);
        File file = getFile(req);
        Note note = getNote(req);
        Company company = getCompany(req);
        //-----read list of contacts-----------------------------------------

        Contact contact = getContact(req);
        Task task = getTask(req);

        try {
            if (company.getId() == 0){
                company.setId(companyService.createWithId(company));
                log.info("company was saved with id = " + company.getId());
            }
            deal.setCompany(company);
            int dealId = dealService.createWithId(deal);
            deal.setId(dealId);
            log.info("deal id = " + dealId);

            if (file != null){
                log.info("file was attached to deal");
                file.setId(fileService.createWithId(file));
                fileService.addFileToDeal(file, deal);
            }

            note.setId(noteService.createWithId(note));
            noteService.addNoteToDeal(note, deal);

            if (contact != null){
                contact.setCompany(company);
                contact.setId(contactService.createWithId(contact));
                log.info("new contact id = " + contact.getId());
                contactService.addContactToDeal(contact, deal);
            }

            if (task != null){
                log.info("trying to save task");
                task.setDeal(deal);
                task.setId(taskService.createWithId(task));
                log.info("new task id = " + task.getId());
            }
        } catch (DatabaseException e) {
            log.error("error while saving to DB" + e);
        }
        /*RequestDispatcher requestDispatcher =  getServletContext().getRequestDispatcher("/dashboard");
        log.info("forwarding to /dashboard");
        requestDispatcher.forward(req, resp);*/
    }

    private Deal getDeal(HttpServletRequest req){
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
        log.info("dealStage integer = " + dealStage);
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
        deal.setDeleted(false);
        deal.setCreationDate(new Date());
        try {
            deal.setResponsibleUser(userService.getUserById(dealResponsibleId));
            //deal.setCreatedByUser(userService.getUserById(currentUserId)); // TODO: 06.04.2016
            deal.setCreatedByUser(userService.getUserById(1));
        } catch (DatabaseException e) {
            log.error("error while getting user" + e);
        }
        //tags // TODO: 06.04.2016
        return deal;
    }

    private Contact getContact(HttpServletRequest req) {
        String contactName = req.getParameter("contactName");
        Contact contact = null;
        if (!contactName.equals("")) {
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
            try {
                contact.setResponsibleUser(userService.getUserById(1));  // TODO: 06.04.2016
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }
        return contact;
    }

    private Company getCompany(HttpServletRequest req){
        String companyName = req.getParameter("companyName");
        String companyIdString = req.getParameter("company");
        log.info("comapny id = " + companyIdString);
        Company company = null;
        if (companyName != null){
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
            company.setPhoneType(PhoneType.MOBILE);//// TODO: 08.04.2016
            company.setPhone(companyPhone);
            company.setWeb(companyWeb);
            company.setDeleted(false);
            company.setCreationDate(new Date());
            try {
                company.setCreatedByUser(userService.getUserById(1)); // TODO: 06.04.2016
                company.setResponsibleUser(userService.getUserById(1));// TODO: 06.04.2016
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }
        else{
            companyIdString = req.getParameter("company");
            log.info("company id from list = " + companyIdString);
            int companyId = Integer.parseInt(companyIdString);
            try {
                company =  companyService.getCompanyById(companyId);
            } catch (DatabaseException e) {
                log.error("can't read company from DB:" + e);
            }
        }
        return company;
    }

    private Task getTask(HttpServletRequest req){
        String taskPeriod = req.getParameter("taskPeriod");
        log.info("task period = " + taskPeriod);
        String taskDateStr = req.getParameter("taskDate");
        log.info("task Date = " + taskDateStr);
        String taskTimeStr = req.getParameter("taskTime");
        log.info("task Time = " + taskTimeStr);
        Task task = null;
        Date finishDate = null;
        Period taskPeriodEnum = null;
        User responsibleUser = null;
        User createdByUser = null;
        TaskType taskType = null;
        DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
        if (!taskPeriod.equals("-1") || !taskTimeStr.equals("") && !taskDateStr.equals("")){
            if (!taskPeriod.equals("-1")){
                taskPeriodEnum = Period.values()[Integer.parseInt(taskPeriod)];
                finishDate = new Date(); //// TODO: 11.04.2016  
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

            try {
                responsibleUser = userService.getUserById(Integer.parseInt(req.getParameter("taskResponsible")));
                createdByUser = userService.getUserById(1); // TODO: 06.04.2016
                taskType = taskTypeService.getTaskTypeById(Integer.parseInt(req.getParameter("taskType")));

            } catch (DatabaseException e) {
                log.error("error while getting users and task type from DB" + e);
            }
            String taskText = req.getParameter("taskText");
            log.info("task text = " + taskText);
            String taskName = req.getParameter("taskName");
            log.info("task name = " + taskName);

            task = new Task();
            task.setName(taskName);
            task.setFinishDate(finishDate);
            task.setPeriod(taskPeriodEnum);
            task.setTaskType(taskType);
            task.setCreatedByUser(createdByUser);
            task.setResponsibleUser(responsibleUser);
            task.setDescription(taskText);
            task.setDeleted(false);
            task.setDone(false);
        }
        return task;
    }

    private Note getNote (HttpServletRequest req){
        Note note = new Note();
        String dealNote = req.getParameter("dealNote");
        note.setCreationDate(new Date());
        note.setText(dealNote);
        try {
            note.setCreatedByUser(userService.getUserById(1)); //// TODO: 08.04.2016
        } catch (DatabaseException e) {
            log.error("can't get user from DB");
        }
        return note;
    }

    private File getFile (HttpServletRequest req){
        Part filePart = null;
        File file = null;
        try {
            filePart = req.getPart("fileName");
            if (filePart != null){
                log.info("file was attached, trying to upload");
                String fileName = extractFileName(filePart);
                log.info("file name: " + fileName);
                InputStream fileContent = filePart.getInputStream();
                log.info("input stream was received!");
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileContent);
                file = new File();
                file.setCreationDate(new Date());
                file.setCreatedByUser(userService.getUserById(1)); //// TODO: 11.04.2016
                byte [] data = new byte[bufferedInputStream.available()];
                int bytesRead = bufferedInputStream.read(data);
                log.info("bytes read from stream: " + bytesRead);
                file.setData(data);
                bufferedInputStream.close();
            }
            else {
                log.info("file was not attached, returning null");
                return null;
            }
        } catch (IOException e) {
            log.error("error while uploading file:" + e);
        } catch (ServletException e) {
            log.error("error while uploading file:" + e);
        } catch (DatabaseException e){
            log.error("error while getting user from DB:" + e);
        }
        return file;
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}
