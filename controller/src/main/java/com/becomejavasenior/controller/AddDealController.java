package com.becomejavasenior.controller;

import com.becomejavasenior.controller.constant.Jsp;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.*;
import com.becomejavasenior.service.*;
import com.becomejavasenior.service.impl.*;
import com.becomejavasenior.model.Company;
import com.becomejavasenior.model.Contact;
import com.becomejavasenior.model.TaskType;
import com.becomejavasenior.model.User;
import com.becomejavasenior.service.CompanyService;
import com.becomejavasenior.service.ContactService;
import com.becomejavasenior.service.TaskTypeService;
import com.becomejavasenior.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet("/add/deal")
public class AddDealController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(AddDealController.class);
    private final String ADD_CONTACTS_KEY = "addContact";
    private final String CURRENT_USER = "User";
    private DealService dealService;
    private ContactService contactService;
    private CompanyService companyService;
    private TaskService taskService;
    private UserService userService;
    private TaskTypeService taskTypeService;
    private NoteService noteService;
    private FileService fileService;
    private TagService tagService;

    @Override
    public void init() throws ServletException {
        ApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
        this.userService = ctx.getBean(UserService.class);
        this.contactService =ctx.getBean(ContactService.class);
        this.companyService = ctx.getBean(CompanyService.class);
        this.taskTypeService = ctx.getBean(TaskTypeService.class);
        this.dealService = ctx.getBean(DealService.class);
        this.taskService = ctx.getBean(TaskService.class);
        this.noteService = ctx.getBean(NoteService.class);
        this.fileService = ctx.getBean(FileService.class);
        this.tagService = ctx.getBean(TagService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();

        List<EnumAdapter> stages = new ArrayList<EnumAdapter>();
        stages.add(new EnumAdapter(1, "Первичный контакт"));
        stages.add(new EnumAdapter(2, "Переговоры"));
        stages.add(new EnumAdapter(3, "Принятие решения"));
        stages.add(new EnumAdapter(4, "Подписание сделки"));
        stages.add(new EnumAdapter(5, "Успешно завершена"));
        stages.add(new EnumAdapter(6, "Не завершено и закрыто"));
        log.info("deal stages: " + stages.toString());
        servletContext.setAttribute("dealStages", stages);

        List<EnumAdapter> phoneTypes = new ArrayList<EnumAdapter>();
        phoneTypes.add(new EnumAdapter(1, "Рабочий"));
        phoneTypes.add(new EnumAdapter(2, "Прямой рабочий номер"));
        phoneTypes.add(new EnumAdapter(3, "Домашний"));
        phoneTypes.add(new EnumAdapter(4, "Мобильный"));
        phoneTypes.add(new EnumAdapter(5, "Домашний"));
        phoneTypes.add(new EnumAdapter(6, "Другое"));
        log.info("phone types: " + phoneTypes.toString());
        servletContext.setAttribute("phoneTypes", phoneTypes);

        List<EnumAdapter> periods = new ArrayList<>();
        periods.add(new EnumAdapter(0, "Сегодня"));
        periods.add(new EnumAdapter(1, "Весь день"));
        periods.add(new EnumAdapter(2, "Завтра"));
        periods.add(new EnumAdapter(3, "Следующая неделя"));
        periods.add(new EnumAdapter(4, "Следующий месяц"));
        periods.add(new EnumAdapter(5, "Следующий год"));
        log.info("periods: " + periods.toString());
        servletContext.setAttribute("periods", periods);

        List<User> users = new ArrayList<>();
        try {
            log.info("trying to get users");
            users = userService.findAll();
        } catch (DatabaseException e) {
            log.error("error while trying get users" + e);
        }
        log.info("users: " + users.toString());
        servletContext.setAttribute("users", users);

        List<Contact> contacts = new ArrayList<>();
        try {
            log.info("trying to get contacts");
            contacts = contactService.findAll();
        } catch (DatabaseException e) {
            log.error("error while trying get contacts" + e);
        }
        log.info("contacts: " + contacts.toString());
        servletContext.setAttribute("contacts", contacts);

        List<Company> companies = new ArrayList<>();
        try {
            companies = companyService.findAll();
        } catch (DatabaseException e) {
            log.error("error while trying get companies" + e);
        }
        log.info("companies: " + companies);
        servletContext.setAttribute("companies", companies);

        List<TaskType> taskTypes = new ArrayList<>();
        try {
            taskTypes = taskTypeService.findAll();
        } catch (DatabaseException e) {
            log.error("error while trying get task types" + e);
        }
        log.info("task types: " + taskTypes);
        servletContext.setAttribute("taskTypes", taskTypes);

        RequestDispatcher requestDispatcher =  getServletContext().getRequestDispatcher(Jsp.JSP_DEAL_ADD);
        log.info("forwarding to " + Jsp.JSP_DEAL_ADD);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("do post method started!");
        Deal deal = getDeal(req);
        File file = getFile(req);
        Note note = getNote(req);
        Company company = getCompany(req);
        List<Contact> contacts = getContactsList(req);
        Contact contact = getContact(req);
        Task task = getTask(req);
        List<Tag> tags = getTags(req);

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

            if (tags.size() > 0){
                for (Tag tag : tags){
                    Tag checkTag = tagService.findTagByName(tag.getName());
                    if (checkTag != null){
                        log.info("tag: " + tag.toString() + " allready exist in DB");
                        tag = checkTag;
                    }
                    else{
                        log.info("saving tag " + tag.toString() + " to DB");
                        int id = tagService.createWithId(tag);
                        tag.setId(id);
                        log.info("new tag id: " + id);
                    }
                    int result = tagService.addTagToDeal(tag, deal);
                    if (result > 0){
                        log.info("tag was added to deal");
                    }
                    else {
                        log.info("tag was not added to deal");
                    }
                }
            }

            if (contact != null){
                contact.setCompany(company);
                contact.setId(contactService.createWithId(contact));
                log.info("new contact id = " + contact.getId());
                contactService.addContactToDeal(contact, deal);
            }

            if (contacts.size() > 0){
                for (Contact contactFromList : contacts){
                    contactService.addContactToDeal(contactFromList, deal);
                }
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
        resp.sendRedirect("/dashboard");
    }

    private Deal getDeal(HttpServletRequest req){
        User currentUser = (User)req.getSession().getAttribute(CURRENT_USER);
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
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
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
        deal.setCreatedByUser(currentUser);
        try {
            deal.setResponsibleUser(userService.getUserById(dealResponsibleId));
        } catch (DatabaseException e) {
            log.error("error while getting user" + e);
        }
        return deal;
    }

    private Contact getContact(HttpServletRequest req) {
        User currentUser = (User)req.getSession().getAttribute(CURRENT_USER);
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
            contact.setResponsibleUser(currentUser);
        }
        return contact;
    }

    private Company getCompany(HttpServletRequest req){
        User currentUser = (User)req.getSession().getAttribute(CURRENT_USER);
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
            company.setPhoneType(PhoneType.MOBILE);
            company.setPhone(companyPhone);
            company.setWeb(companyWeb);
            company.setDeleted(false);
            company.setCreationDate(new Date());
            company.setCreatedByUser(currentUser);
            company.setResponsibleUser(currentUser);
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

    private Note getNote (HttpServletRequest req){
        User currentUser = (User)req.getSession().getAttribute(CURRENT_USER);
        Note note = new Note();
        String dealNote = req.getParameter("dealNote");
        note.setCreationDate(new Date());
        note.setText(dealNote);
        note.setCreatedByUser(currentUser);
        return note;
    }

    private Task getTask(HttpServletRequest req){
        User currentUser = (User)req.getSession().getAttribute(CURRENT_USER);
        String taskPeriod = req.getParameter("taskPeriod");
        if (taskPeriod == null){
            taskPeriod = "-1";
        }
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
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        if (!taskPeriod.equals("-1") || !taskTimeStr.equals("") && !taskDateStr.equals("")){
            if (!taskPeriod.equals("-1")){
                taskPeriodEnum = Period.values()[Integer.parseInt(taskPeriod)];
                finishDate = new Date();
            }
            else {
                try {
                    Date taskDate =  dateFormat.parse(taskDateStr);
                    log.info("converted task date = " + taskDate.toString());
                    Date taskTime =  convertTime(taskTimeStr);
                    log.info("converted task time = " + taskDate.toString());
                    finishDate = new Date(taskDate.getTime() + taskTime.getTime());
                    log.info("task finish date: " + finishDate.toString());
                } catch (ParseException e) {
                    log.error("can't parse task date" + e);
                }
            }
            try {
                responsibleUser = userService.getUserById(Integer.parseInt(req.getParameter("taskResponsible")));
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
            task.setCreatedByUser(currentUser);
            task.setResponsibleUser(responsibleUser);
            task.setDescription(taskText);
            task.setDeleted(false);
            task.setDone(false);
        }
        return task;
    }

    private File getFile (HttpServletRequest req){
        User currentUser = (User)req.getSession().getAttribute(CURRENT_USER);
        Part filePart = null;
        File file = null;
        try {
            filePart = req.getPart("fileName");
            String fileName = extractFileName(filePart);
            log.info("file name: ?" + fileName + "?");
            if (!fileName.equals("")){
                log.info("file was attached, trying to upload");

                InputStream fileContent = filePart.getInputStream();
                log.info("input stream was received!");
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileContent);
                file = new File();
                file.setCreationDate(new Date());
                file.setCreatedByUser(currentUser);
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
        } catch (IOException | ServletException e) {
            log.error("error while uploading file:" + e);
        }
        return file;
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String result;
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                result = s.substring(s.indexOf("=") + 2, s.length()-1);
                log.info("file name from request: " + result);
                return result;
            }
        }
        return "";
    }

    private List<Contact> getContactsList(HttpServletRequest req){
        List<Contact> contacts = new ArrayList<>();
        Map<String, String[]> parametersMap = (Map<String, String[]>) req.getParameterMap();
        for (Map.Entry<String, String[]> entry : parametersMap.entrySet() ) {
            if (entry.getKey().contains(ADD_CONTACTS_KEY)){
                log.info("add contact parameter name:" + entry.getKey());
                log.info("value array length:" + entry.getValue().length);
                for (int i = 0; i< entry.getValue().length; i++){
                    log.info("add contact parameter value:" + entry.getValue()[i]);
                }
                int id = Integer.parseInt(entry.getValue()[0]);
                try {
                    Contact contact = contactService.getContactById(id);
                    contacts.add(contact);
                } catch (DatabaseException e) {
                    log.error("can't read contact from DB + " + e);
                }
            }
        }
        return contacts;
    }

    private List<Tag> getTags(HttpServletRequest req){
        User currentUser = (User)req.getSession().getAttribute(CURRENT_USER);
        List<Tag> tags = new ArrayList<>();
        String tagToSplit = req.getParameter("dealTags");
        log.info("tags string: " + tagToSplit);
        tags = TagSplitter.getTagListFromString(tagToSplit);
        for (Tag tag : tags){
            tag.setCreationDate(new Timestamp(new Date().getTime()));
            tag.setCreatedByUser(currentUser);
        }
        return tags;
    }

    private Date convertTime(String time){
        Date date;
        long timeInMilSec = 0;
        String[] splitTime = time.split(":");
        int hours = Integer.parseInt(splitTime[0]);
        int minutes = Integer.parseInt(splitTime[1]);
        timeInMilSec += hours * 3600 * 1000;
        timeInMilSec += minutes * 60 * 1000;
        date = new Date(timeInMilSec);
        return date;
    }
}
