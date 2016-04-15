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
import java.time.*;
import java.time.format.TextStyle;
import java.util.*;

@WebServlet("/tasks")
public class TasksController extends HttpServlet {
    private static final String TASK_LIST_TODO_JSP = "/pages/task/tasksListTodo.jsp";
    private static final String TASK_LIST_DAY_JSP = "/pages/task/tasksCalendarDay.jsp";
    private static final String TASK_LIST_WEEK_JSP = "/pages/task/tasksCalendarWeek.jsp";
    private static final String TASK_LIST_MONTH_JSP = "/pages/task/tasksCalendarMonth.jsp";
    private static final String TASK_LIST = "/pages/task/tasksList.jsp";

    private static final String TODO_TASKS = "todoTasks";

    private static final String TASKS_FOR_DAY = "tasksForDay";
    private static final String DAY_NAME = "dayName";

    private static final String TASKS_FOR_WEEK = "tasksForWeek";
    private static final String DAY_NAMES = "dayNames";

    private static final String TASKS_FOR_MONTH = "tasksForMonth";

    private static final String TASKS_LIST = "tasks";

    private static final String TODO_VIEW = "todo";
    private static final String DAY_VIEW = "day";
    private static final String WEEK_VIEW = "week";
    private static final String MONTH_VIEW = "month";
    private static final String LIST_VIEW = "list";
    private static final String VIEW = "view";

    private static final String DATE = "date";
    private static final String NEXT_DAY = "nextDay";
    private static final String PREV_DAY = "prevDay";
    private static final String NEXT_WEEK = "nextWeek";
    private static final String PREV_WEEK = "prevWeek";
    private static final String NEXT_MONTH = "nextMonth";
    private static final String PREV_MONTH = "prevMonth";
    private static final String VIEW_DATE = "viewDate";
    private static final String CHANGE_DATE = "changeDate";
    private static final String ON_TODAY = "onToday";
    private static final String ON_TOMORROW = "onTomorrow";

    private TaskService taskService;

    @Override
    public void init() throws ServletException {
        this.taskService = new TaskServiceImpl();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewType = getViewType(request);

        RequestDispatcher rd;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int taskId = Integer.parseInt(request.getParameter("taskId"));
        String changeDate = request.getParameter(CHANGE_DATE);
        try {
            if (changeDate.equals(ON_TODAY)) {
                taskService.moveOnToday(taskId);
            } else if (changeDate.equals(ON_TOMORROW)) {
                taskService.moveOnTomorrow(taskId);
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/tasks?view=todo");
    }

    private RequestDispatcher processMonthTasks(HttpServletRequest request) {
        Map<LocalDate, List<Task>> taskByDayForMonth = new LinkedHashMap<>();
        LocalDate currentTime = getDateFromRequest(request);
        try {
            taskByDayForMonth = taskService.getTaskForMonthByDay(currentTime);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        request.setAttribute(TASKS_FOR_MONTH, taskByDayForMonth);
        request.setAttribute(NEXT_MONTH, currentTime.plusMonths(1));
        request.setAttribute(PREV_MONTH, currentTime.minusMonths(1));
        request.setAttribute(VIEW_DATE, currentTime);
        return getServletContext().getRequestDispatcher(TASK_LIST_MONTH_JSP);
    }

    private String getViewType(HttpServletRequest request) {
        String viewType = request.getParameter(VIEW);
        if (viewType == null)
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
        LocalDate currentTime = getDateFromRequest(request);
        try {

            tasksForWeek = taskService.getTaskByHalfHourForWeek(currentTime);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        List<String> dayNames = new ArrayList<>();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            dayNames.add(dayOfWeek.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()));
        }

        request.setAttribute(DAY_NAMES, dayNames);
        request.setAttribute(TASKS_FOR_WEEK, tasksForWeek);
        request.setAttribute(NEXT_WEEK, currentTime.plusWeeks(1));
        request.setAttribute(PREV_WEEK, currentTime.minusWeeks(1));
        request.setAttribute(VIEW_DATE, currentTime);
        return getServletContext().getRequestDispatcher(TASK_LIST_WEEK_JSP);
    }

    private RequestDispatcher processDayTasks(HttpServletRequest request) {
        Map<LocalTime, List<Task>> taskForDay = new LinkedHashMap<>();
        LocalDate currentTime = getDateFromRequest(request);
        DayOfWeek currentDayOfWeek = currentTime.getDayOfWeek();
        try {
            taskForDay = taskService.getTaskForDayByHalfHour(currentTime);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        request.setAttribute(DAY_NAME, currentDayOfWeek.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()));
        request.setAttribute(TASKS_FOR_DAY, taskForDay);
        request.setAttribute(NEXT_DAY, currentTime.plusDays(1));
        request.setAttribute(PREV_DAY, currentTime.minusDays(1));
        request.setAttribute(VIEW_DATE, currentTime);
        return getServletContext().getRequestDispatcher(TASK_LIST_DAY_JSP);
    }

    private RequestDispatcher processTodoLine(HttpServletRequest request) {
        Map<String, List<Task>> taskByDay = new LinkedHashMap<>();
        LocalDate currentTime = getDateFromRequest(request);
        try {
            taskByDay = taskService.getTodoLineTasks(currentTime);

        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        request.setAttribute(TODO_TASKS, taskByDay);
        request.setAttribute(VIEW_DATE, currentTime);
        return getServletContext().getRequestDispatcher(TASK_LIST_TODO_JSP);
    }

    private LocalDate getDateFromRequest(HttpServletRequest request) {
        String date = request.getParameter(DATE);
        if (date == null) {
            return LocalDate.now();
        }
        return LocalDate.parse(date);
    }

}


