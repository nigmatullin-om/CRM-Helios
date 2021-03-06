package com.becomejavasenior.service.impl;

import com.becomejavasenior.dao.*;
import com.becomejavasenior.model.*;
import com.becomejavasenior.service.TaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.*;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private static final String DONE_TASKS = "doneTasks";
    private static final String NOT_DONE_TASKS = "notDoneTasks";
    private static final String OVERDUE_TASKS = "overdue";
    private static final String TODAY_TASKS = "today";
    private static final String TOMORROW_TASKS = "tomorrow";
    private static final int MINUTES_IN_HALF_HOUR = 30;
    private static final int HALF_HOURS_IN_DAY = 48;
    private static final int DAYS_IN_WEEK = 7;

    @Resource
    private TaskDao taskDao;

    @Resource
    private ContactDao contactDao;

    @Resource
    private CompanyDao companyDao;

    @Resource
    private DealDao dealDao;

    @Resource
    private UserDao userDao;

    @Resource
    private TaskTypeDao taskTypeDao;

    @Override
    public int create(Task task) throws DatabaseException {
        return taskDao.create(task);
    }

    @Override
    public Task getTaskById(int id) throws DatabaseException {
        Task taskById = taskDao.getTaskById(id);
        return fillTaskFields(taskById);
    }

    @Override
    public int update(Task task) throws DatabaseException {
        return taskDao.update(task);
    }

    @Override
    public int delete(Task task) throws DatabaseException {
        return taskDao.delete(task);
    }

    @Override
    public List<Task> findAll() throws DatabaseException {
        List<Task> rawTasks = taskDao.findAll();
        return fillTaskFields(rawTasks);
    }


    @Override
    public Map<String, List<Task>> getTodoLineTasks(LocalDate localDate) throws DatabaseException {
        Map<String, List<Task>> todoLineTasks = new LinkedHashMap<>();
        todoLineTasks.put(OVERDUE_TASKS, new ArrayList<>());
        todoLineTasks.put(TODAY_TASKS, new ArrayList<>());
        todoLineTasks.put(TOMORROW_TASKS, new ArrayList<>());

        LocalDate nextDay = localDate.plusDays(2);

        for (Task task : findAll()) {
            LocalDate taskFinishDate = getLocalDateTimeFromDate(task.getFinishDate()).toLocalDate();
            if (taskFinishDate.isBefore(localDate)) {
                todoLineTasks.get(OVERDUE_TASKS).add(task);
            } else if (taskFinishDate.isEqual(localDate)) {
                todoLineTasks.get(TODAY_TASKS).add(task);
            } else if (taskFinishDate.isAfter(localDate) && taskFinishDate.isBefore(nextDay)) {
                todoLineTasks.get(TOMORROW_TASKS).add(task);
            }
        }
        return todoLineTasks;
    }

    @Override
    public Map<LocalDate, List<Task>> getTaskByDay(LocalDate startDate, int dayCount) throws DatabaseException {
        Map<LocalDate, List<Task>> tasksByDay = new LinkedHashMap<>();

        LocalDate day = startDate;
        for (int i = 0; i < dayCount; i++) {
            LocalDate nextDay = day.plusDays(1);
            List<Task> tasksForToday = fillTaskFields(taskDao.getTasksBetweenDays(day, nextDay));
            tasksByDay.put(day, tasksForToday);
            day = nextDay;
        }
        return tasksByDay;
    }

    @Override
    public Map<LocalTime, List<Task>> getTaskForDayByHalfHour(LocalDate day) throws DatabaseException {
        Map<LocalDate, List<Task>> taskForDay = getTaskByDay(day, 1);

        List<Task> taskList = taskForDay.values().iterator().next();
        Map<LocalTime, List<Task>> taskMapHalfHour = new LinkedHashMap<>();

        List<LocalTime> timeByHalfHour = timeTimeByHalfHour();
        for (LocalTime halfHour : timeByHalfHour) {
            LocalTime nextHalfHour = halfHour.plusMinutes(MINUTES_IN_HALF_HOUR);
            List<Task> tasksForHalfHour = getTaskForTimePeriod(halfHour, nextHalfHour, taskList);
            taskMapHalfHour.put(halfHour, tasksForHalfHour);
        }
        return taskMapHalfHour;

    }

    @Override
    public Map<LocalTime, Map<DayOfWeek, List<Task>>> getTaskByHalfHourForWeek(LocalDate day) throws DatabaseException {
        LocalDate firstDayOfThisWeek = day.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);

        Map<LocalDate, List<Task>> tasksForWeekByDay = getTaskByDay(firstDayOfThisWeek, DAYS_IN_WEEK);
        Map<LocalTime, Map<DayOfWeek, List<Task>>> tasksByHalfHourForWeek = new LinkedHashMap<>();

        for (LocalTime halfHour : timeTimeByHalfHour()) {
            tasksByHalfHourForWeek.put(halfHour, new LinkedHashMap<>());
        }

        for (LocalTime halfHour : timeTimeByHalfHour()) {
            for (LocalDate dayOfWeek : tasksForWeekByDay.keySet()) {
                LocalTime nextHalfHour = halfHour.plusMinutes(MINUTES_IN_HALF_HOUR);
                List<Task> tasksForHalfHour = getTaskForTimePeriod(halfHour, nextHalfHour, tasksForWeekByDay.get(dayOfWeek));
                tasksByHalfHourForWeek.get(halfHour).put(dayOfWeek.getDayOfWeek(), tasksForHalfHour);
            }

        }
        return tasksByHalfHourForWeek;

    }

    @Override
    public Map<LocalDate, List<Task>> getTaskForMonthByDay(LocalDate date) throws DatabaseException {
        LocalDate firstDayOfMonth = date.withDayOfMonth(1);
        return getTaskByDay(firstDayOfMonth, firstDayOfMonth.lengthOfMonth());
    }

    @Override
    public Map<String, List<Task>> filterTasksByDone(List<Task> tasks) {
        Map<String, List<Task>> mapTasks = new HashMap<>();

        List<Task> doneTasks = tasks.stream().filter(Task::isDone).collect(Collectors.toCollection(LinkedList::new));
        List<Task> notDoneTasks = tasks.stream().filter(task -> !task.isDone()).collect(Collectors.toCollection(LinkedList::new));

        mapTasks.put(DONE_TASKS, doneTasks);
        mapTasks.put(NOT_DONE_TASKS, notDoneTasks);
        return mapTasks;
    }

    @Override
    public List<Task> getRunningTasks(List<Task> notDoneTasks) {
        long currentTime = System.currentTimeMillis();
        return notDoneTasks.stream().filter(tempTask -> tempTask.getFinishDate().getTime() > currentTime).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public void moveOnToday(int taskId) throws DatabaseException {
        Task task = getTaskById(taskId);
        LocalDateTime finishDateTime = LocalDateTime.ofInstant(task.getFinishDate().toInstant(), ZoneId.systemDefault());
        LocalTime finishTime = finishDateTime.toLocalTime();
        LocalDateTime newFinishDate = LocalDateTime.of(LocalDate.now(), finishTime);
        updateTaskTime(task, newFinishDate);
    }

    @Override
    public void moveOnTomorrow(int taskId) throws DatabaseException {
        Task task = getTaskById(taskId);
        LocalDateTime finishDateTime = LocalDateTime.ofInstant(task.getFinishDate().toInstant(), ZoneId.systemDefault());
        LocalTime finishTime = finishDateTime.toLocalTime();
        LocalDateTime newFinishDate = LocalDateTime.of(LocalDate.now().plusDays(1), finishTime);
        updateTaskTime(task, newFinishDate);
    }

    @Override
    public void updateTaskTime(Task task, LocalDateTime newDate) throws DatabaseException {
        task.setFinishDate(Date.from(newDate.atZone(ZoneId.systemDefault()).toInstant()));
        update(task);
    }

    @Override
    public int createWithId(Task task) throws DatabaseException {
        return taskDao.createWithId(task);
    }

    private List<LocalTime> timeTimeByHalfHour() {
        LocalTime localTime = LocalTime.MIN;
        List<LocalTime> timeByHalfHour = new ArrayList<>();

        for (int i = 0; i < HALF_HOURS_IN_DAY; i++) {
            timeByHalfHour.add(localTime);
            localTime = localTime.plusMinutes(MINUTES_IN_HALF_HOUR);
        }
        return timeByHalfHour;
    }

    private List<Task> getTaskForTimePeriod(LocalTime from, LocalTime to, List<Task> tasks) {
        List<Task> tasksByTime = new ArrayList<>();
        for (Task task : tasks) {
            LocalDateTime taskFinishDateTime = getLocalDateTimeFromDate(task.getFinishDate());
            LocalTime taskFinishTime = taskFinishDateTime.toLocalTime();

            if ((taskFinishTime.isAfter(from) && taskFinishTime.isBefore(to)) || taskFinishTime.equals(from)) {
                tasksByTime.add(task);
            }
        }
        return tasksByTime;
    }

    private LocalDateTime getLocalDateTimeFromDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }


    private List<Task> fillTaskFields(List<Task> rawTasks) throws DatabaseException {
        List<Task> tasksWithAllFields = new ArrayList<>();

        for (Task task : rawTasks) {
            tasksWithAllFields.add(fillTaskFields(task));
        }
        return tasksWithAllFields;
    }

    private Task fillTaskFields(Task task) throws DatabaseException {
        Contact contactForTask = contactDao.getContactForTask(task);
        task.setContact(contactForTask);

        Company companyForTask = companyDao.getCompanyForTask(task);
        task.setCompany(companyForTask);

        Deal dealForTask = dealDao.getDealForTask(task);
        task.setDeal(dealForTask);

        User responsibleUserForTask = userDao.getResponsibleUserForTask(task);
        task.setResponsibleUser(responsibleUserForTask);

        User createdByUserForTask = userDao.createdByUserForTask(task);
        task.setCreatedByUser(createdByUserForTask);

        TaskType taskType = taskTypeDao.getTaskTypeForTask(task);
        task.setTaskType(taskType);

        return task;
    }


    public int getMaxId() throws DatabaseException {
        return taskDao.getMaxId();
    }

}
