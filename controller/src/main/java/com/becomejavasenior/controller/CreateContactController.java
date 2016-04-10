package com.becomejavasenior.controller;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.*;
import com.becomejavasenior.service.impl.CompanyServiceImpl;
import com.becomejavasenior.service.impl.TaskTypeServiceImpl;
import com.becomejavasenior.service.impl.UserServiceImpl;

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
import java.util.zip.Inflater;


@WebServlet("/contact")
@MultipartConfig
public class CreateContactController extends HttpServlet {
    private static final String CONTACT_JSP = "/pages/createContact.jsp";
    private static final String USERS = "users";
    private static final String COMPANIES = "companies";
    private static final String TASK_TYPES = "taskTypes";

    private UserServiceImpl userService;
    private CompanyServiceImpl companyService;
    private TaskTypeServiceImpl taskTypeService;

    private List<User> users = null;
    private List<Company> companies = null;
    private List<TaskType> taskTypes = null;


    @Override
    public void init() throws ServletException {
        this.userService = new UserServiceImpl();
        this.companyService = new CompanyServiceImpl();
        this.taskTypeService = new TaskTypeServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            companies = companyService.findAll();
            users = userService.findAll();
            taskTypes = taskTypeService.findAll();

        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        request.setAttribute(USERS, users);
        request.setAttribute(COMPANIES, companies);
        request.setAttribute(TASK_TYPES, taskTypes);
        RequestDispatcher rd = getServletContext().getRequestDispatcher(CONTACT_JSP);
        rd.forward(request, response);
    }
    @Override
    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Contact contact = new Contact();
        List<Tag> tags = new ArrayList<Tag>();
        Task task = new Task();
        Note note = new Note();
        Deal deal = new Deal();

        contact.setName(request.getParameter("contact-name"));
        contact.setPhoneType(PhoneType.values()[Integer.parseInt(request.getParameter("phone-type"))]);
        contact.setPhone(request.getParameter("phone_number"));
        contact.setEmail(request.getParameter("email"));
        contact.setSkype(request.getParameter("skype"));
        contact.setPosition(request.getParameter("position"));
        note.setText(request.getParameter("note"));
        List<Note> notes = new ArrayList<Note>();
        notes.add(note);
        contact.setNotes(notes);
        //tags
        Pattern pattern = Pattern.compile("#| #");
        String[] tagsName =  pattern.split(request.getParameter("tags"));
        Arrays.asList(tagsName).forEach(tagName -> {
            Tag tag = new Tag();
            tag.setName(tagName);
            //TODO add created by User();
            tag.setCreatedByUser(new User());
            tag.setCreationDate(new Date(System.currentTimeMillis()));
            tags.add(tag);
        });
        tags.remove(0);
        contact.setTags(tags);
        //task
        task.setPeriod(Period.values()[Integer.parseInt(request.getParameter("task-period"))]);
        task.setFinishDate(new Date(request.getParameter("task-date")));
        //TODO add created by User();
        task.setName(request.getParameter("task-description"));
        task.setCreationDate(new Date(System.currentTimeMillis()));
        //deal
        deal.setName(request.getParameter("deal-name"));
        deal.setDealStage(DealStage.values()[Integer.parseInt(request.getParameter("deal-stage"))]);
        deal.setBudget(new BigDecimal(Integer.parseInt(request.getParameter("budget"))));
        try {
            contact.setResponsibleUser(userService.getUserById(Integer.parseInt(request.getParameter("responsible"))));
            task.setTaskType(taskTypeService.getTaskTypeById(Integer.parseInt(request.getParameter("task-type"))));
            task.setResponsibleUser(userService.getUserById(Integer.parseInt(request.getParameter("task-responsible"))));
        } catch (DatabaseException e) {
            //TODO add log4j instead  printStackTrace
            e.printStackTrace();
        }

        doGet(request,response);
    }
}
