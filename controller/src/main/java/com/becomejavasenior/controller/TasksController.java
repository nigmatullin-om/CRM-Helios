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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;

@WebServlet("/tasks")
public class TasksController extends HttpServlet {
    private static final String TASK_LIST_TODO_JSP = "/pages/task/tasksListTodo.jsp";
    private static final String TASK_LIST_DAY_JSP = "/pages/task/tasksCalendarDay.jsp";
    private static final String TASK_LIST_WEEK_JSP = "/pages/task/tasksCalendarWeek.jsp";
    private static final String TASK_LIST_MONTH_JSP = "/pages/task/tasksCalendarMonth.jsp";
    private static final String TASK_LIST = "/pages/task/tasksList.jsp";

    private static final String OVERDUE_TASKS = "overdueTasks";
    private static final String TODAY_TASKS = "todayTasks";
    private static final String TOMORROW_TASKS = "tomorrowTasks";

    private static final String TASKS_FOR_DAY = "tasksForDay";
    private static final String DAY_NAME = "dayName";

    private static final String TASKS_FOR_WEEK = "tasksForWeek";
    private static final String DAY_NAMES = "dayNames";

    private static final String TASKS_FOR_MONTH = "tasksForMonth";

    private static final String TASKS_LIST = "tasks";

    public static final String TODO_VIEW = "todo";
    public static final String DAY_VIEW = "day";
    public static final String WEEK_VIEW = "week";
    public static final String MONTH_VIEW = "month";
    public static final String LIST_VIEW = "list";
    public static final String VIEW = "view";

    private TaskService taskService;
    @Override
    public void init() throws ServletException {
        this.taskService = new TaskServiceImpl();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewType = getViewType(request);

        RequestDispatcher rd = null;
        switch (viewType) {
            case TODO_VIEW: {
                rd = processTodoLine(request);
                break;
            }
            case DAY_VIEW: {
                rd = processDayTasks(request);
                break;
            }
            case WEEK_VIEW: {
                rd = processWeekTasks(request);
                break;
            }
            case MONTH_VIEW: {
                rd = processMonthTasks(request);
                break;
            }
            default: {
                rd = processListTasks(request);
                break;
            }
        }
        rd.forward(request, response);

    }

    private RequestDispatcher processMonthTasks(HttpServletRequest request) {
        Map<LocalDate, List<Task>> taskByDayForMonth = new LinkedHashMap<>();
        try {
            taskByDayForMonth = taskService.getTaskForMonthByDay(LocalDate.now());

        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        request.setAttribute(TASKS_FOR_MONTH, taskByDayForMonth);
        return getServletContext().getRequestDispatcher(TASK_LIST_MONTH_JSP);
    }

    private String getViewType(HttpServletRequest request) {
        String viewType = request.getParameter(VIEW);
        if(viewType == null)
            viewType = LIST_VIEW;
        return viewType;
    }

    private RequestDispatcher processListTasks(HttpServletRequest request) {
        List<Task> tasks = null;
        try {
            tasks = taskService.findAll();

        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        request.setAttribute(TASKS_LIST, tasks);
        return getServletContext().getRequestDispatcher(TASK_LIST);
    }

    private RequestDispatcher processWeekTasks(HttpServletRequest request) {
        Map<LocalTime, Map<DayOfWeek, List<Task>>> tasksForWeek = new LinkedHashMap<>();
        try {
            tasksForWeek = taskService.getTaskByHalfHourForWeek(LocalDate.now());
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        List<String> dayNames = new ArrayList<>();
        for(DayOfWeek dayOfWeek : DayOfWeek.values())
        {
            dayNames.add(dayOfWeek.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()));
        }

        request.setAttribute(DAY_NAMES, dayNames);
        request.setAttribute(TASKS_FOR_WEEK, tasksForWeek);
        return getServletContext().getRequestDispatcher(TASK_LIST_WEEK_JSP);
    }

    private RequestDispatcher processDayTasks(HttpServletRequest request) {
        Map<LocalTime, List<Task>> taskForDay = new LinkedHashMap<>();
        LocalDate currentTime = LocalDate.now();
        DayOfWeek currentDayOfWeek = currentTime.getDayOfWeek();
        try {

            taskForDay = taskService.getTaskForDayByHalfHour(currentTime);

        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        request.setAttribute(DAY_NAME, currentDayOfWeek.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()));
        request.setAttribute(TASKS_FOR_DAY, taskForDay);
        return getServletContext().getRequestDispatcher(TASK_LIST_DAY_JSP);
    }

    private RequestDispatcher processTodoLine(HttpServletRequest request) {
        Map<String, List<Task>> taskByDay = new LinkedHashMap<>();
        try {
            taskByDay = taskService.getTodoLineTasks(LocalDate.now());

        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        request.setAttribute(OVERDUE_TASKS, taskByDay.get(TaskServiceImpl.OVERDUE_TASKS));
        request.setAttribute(TODAY_TASKS, taskByDay.get(TaskServiceImpl.TODAY_TASKS));
        request.setAttribute(TOMORROW_TASKS, taskByDay.get(TaskServiceImpl.TOMORROW_TASKS));
        return getServletContext().getRequestDispatcher(TASK_LIST_TODO_JSP);
    }

}


