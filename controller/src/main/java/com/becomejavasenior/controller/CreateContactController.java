package com.becomejavasenior.controller;

import com.becomejavasenior.controller.constant.Jsp;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.*;
import com.becomejavasenior.service.*;
import com.becomejavasenior.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;



@WebServlet("/add/contact")
@MultipartConfig
public class CreateContactController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(CreateContactController.class);


    private static final String USERS = "users";
    private static final String COMPANIES = "companies";
    private static final String TASK_TYPES = "taskTypes";

    private UserService userService;
    private CompanyService companyService;
    private TaskTypeService taskTypeService;
    private DealService dealService;
    private NoteService noteService;
    private TagService tagService;
    private TaskService taskService;
    private ContactService contactService;

    @Override

    public void init() throws ServletException {
        ApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
        this.userService = ctx.getBean(UserService.class);
        this.companyService = ctx.getBean(CompanyService.class);
        this.taskTypeService = ctx.getBean(TaskTypeService.class);
        this.dealService = ctx.getBean(DealService.class);
        this.noteService = ctx.getBean(NoteService.class);
        this.tagService = ctx.getBean(TagService.class);
        this.taskService = ctx.getBean(TaskService.class);
        this.contactService = ctx.getBean(ContactService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = null;
        List<Company> companies = null;
        List<TaskType> taskTypes = null;

        try {
            taskTypes = taskTypeService.findAll();
            users = userService.findAll();
            companies = companyService.findAll();

        } catch (DatabaseException e) {
            LOGGER.error(new Object[]{e.getMessage()});
        }
        request.setAttribute(USERS, users);
        request.setAttribute(COMPANIES, companies);
        request.setAttribute(TASK_TYPES, taskTypes);
        RequestDispatcher rd = getServletContext().getRequestDispatcher(Jsp.JSP_CONTACT_ADD);
        rd.forward(request, response);
    }

    @Override
    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Contact contact = new Contact();
        Deal deal = new Deal();
        Note noteContact = new Note();
        List<Note> notesContact  = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        Task task = new Task();
        Note noteCompany = new Note();
        Company  company = new Company();
        List<Note> notesCompany = new ArrayList<>();

        contact.setName(request.getParameter("contact-name"));
        contact.setPhoneType(PhoneType.values()[Integer.parseInt(request.getParameter("phone-type"))]);
        contact.setPhone(request.getParameter("phone_number"));
        contact.setEmail(request.getParameter("email"));
        contact.setSkype(request.getParameter("skype"));
        contact.setPosition(request.getParameter("position"));
        contact.setCreationDate(new Date(System.currentTimeMillis()));
        contact.setDeleted(false);
        try {
            contact.setResponsibleUser(userService.getUserById(Integer.parseInt(request.getParameter("responsible"))));
            contact.setId(contactService.findAll().toArray().length+1);
            deal = getDealByRequest(request);
            noteContact = getNoteByRequest(request, "note");
            if (noteContact != null) {
                noteContact.setContact(contact);
            }
            tags = getTagsByRequest(request);
            task = getTaskByRequest(request);
            noteCompany = getNoteByRequest(request,"note-company");
            company = getCompanyByRequest(request);
            if (noteCompany != null) {
                noteCompany.setCompany(company);
            }
        } catch (DatabaseException e) {
            LOGGER.error(new Object[]{e.getMessage()});
        }
        notesContact.add(noteContact);
        contact.setNotes(notesContact);
        contact.setTags(tags);

        List<Task> tasks = contact.getTasks();
        if(task!=null){
            task.setDeal(deal);
            task.setContact(contact);
        }
        tasks.add(task);

        contact.setTasks(tasks);
        contact.setCompany(company);
        notesCompany.add(noteCompany);
        company.setNotes(notesCompany);
        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(contact);
        if (deal != null) {
            deal.setContacts(contacts);
        }
        try {
            if(deal!=null){
                dealService.create(deal);
            }
            if(request.getParameter("select_company")==null) {
                companyService.create(company);
            }
            contactService.create(contact);
            if (task!=null){
                taskService.create(task);
            }
            if(deal!=null) {
                dealService.updateDealContact(deal);
            }
            if(noteContact!=null){
                noteService.create(noteContact);
            }
            if (noteCompany!=null){
                noteService.create(noteCompany);
            }
            for(Tag tag: tags){
                if(!tagService.isExist(tag)) {
                    tagService.create(tag);
                    tagService.updateTagContact(tag, contact ,company);
                }
                else{
                    tagService.updateTagContact(tagService.getTagByName(tag.getName()), contact, company);
                }
            }
        } catch (DatabaseException e) {
            LOGGER.error(new Object[]{e.getMessage()});
        }

        doGet(request,response);
    }

    private Deal getDealByRequest(HttpServletRequest request) throws DatabaseException {
        Deal deal = new Deal();
        if(request.getParameter("deal-name").equals("")){
            return null;
        }
        deal.setName(request.getParameter("deal-name"));
        deal.setDealStage(DealStage.values()[Integer.parseInt(request.getParameter("deal-stage"))]);
        deal.setBudget(new BigDecimal(Integer.parseInt(request.getParameter("budget"))));
        deal.setCreationDate(new Date(System.currentTimeMillis()));
        deal.setId(dealService.getMaxId()+1);
        return deal;
    }
    private Task getTaskByRequest(HttpServletRequest request)throws DatabaseException{
        Task task = new Task();
        if(request.getParameter("task-description").equals("")){
            return null;
        }
        task.setName(request.getParameter("task-description"));
        task.setId(taskService.getMaxId()+1);
        task.setDescription(request.getParameter("task-description"));// task description same with task name
        task.setPeriod(Period.values()[Integer.parseInt(request.getParameter("task-period"))]);
        String[] date = request.getParameter("task-date").split("-");
        task.setFinishDate(new Date(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2])));
        task.setCreationDate(new Date(System.currentTimeMillis()));
        task.setTaskType(taskTypeService.getTaskTypeById(Integer.parseInt(request.getParameter("task-type"))));
        task.setResponsibleUser(userService.getUserById(Integer.parseInt(request.getParameter("task-responsible"))));
        return task;
    }
    private Note getNoteByRequest(HttpServletRequest request, String inputName)throws DatabaseException{
        Note note = new Note();
        String noteText =  request.getParameter(inputName);
        if(noteText == ""){
            return null;
        }else {
            note.setId(noteService.getMaxId()+1);
            note.setText(noteText);
            //test date
            //TODO add created by User();
            note.setCreatedByUser(userService.getUserById(1));
            note.setCreationDate(new Date(System.currentTimeMillis()));
            return  note;
        }
    }
    private Company getCompanyByRequest(HttpServletRequest request)throws DatabaseException{
        if(request.getParameter("select_company")!=null && request.getParameter("select_company")!="0"){
            return companyService.getCompanyById(Integer.parseInt(request.getParameter("select_company")));
        }
        Company company = new Company();
        company.setId(companyService.findAll().toArray().length+1);
        company.setName(request.getParameter("name-company"));
        company.setPhone(request.getParameter("phone-company"));
        company.setEmail(request.getParameter("email-company"));
        company.setWeb(request.getParameter("web-company"));
        company.setCreationDate(new Date(System.currentTimeMillis()));
        company.setDeleted(false);
        company.setPhoneType(PhoneType.DIRECT_WORK_PHONE);
        return company;
    }
    private ArrayList<Tag> getTagsByRequest(HttpServletRequest request)throws DatabaseException{
        if(request.getParameter("tags").equals("")){
            return null;
        }else {
            int tagId;
            ArrayList <Tag> tags = new ArrayList<>();
            Pattern pattern = Pattern.compile("#| #");
            String[] tagsName =  pattern.split(request.getParameter("tags"));
            tagId = tagService.getMaxId();//because we haw one excessive param
            for(String tagName:Arrays.asList(tagsName)){
                Tag tag = new Tag();
                tagName = "#"+tagName;
                tag.setName(tagName);
                tag.setId(tagId);
                // TODO
                tag.setCreatedByUser(userService.getUserById(1));
                tag.setCreationDate(new Date(System.currentTimeMillis()));
                tags.add(tag);
                tagId++;
            }
            tags.remove(0);
            return tags;
        }
    }
}