package com.becomejavasenior.controller;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Task;
import com.becomejavasenior.service.TaskService;
import com.becomejavasenior.service.impl.TaskServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/tasks")
public class TasksController extends HttpServlet {
    private static final String TASK_JSP = "/pages/tasks.jsp";
    private TaskService taskService;

    private static final String TASKS = "tasks";
    @Override
    public void init() throws ServletException {
        this.taskService = new TaskServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Task> tasks = null;
        try {
            tasks = taskService.findAll();

        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        request.setAttribute(TASKS, tasks);
        RequestDispatcher rd = getServletContext().getRequestDispatcher(TASK_JSP);
        rd.forward(request, response);
    }

}
