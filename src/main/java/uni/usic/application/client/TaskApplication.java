package uni.usic.application.client;

import uni.usic.application.service.TaskService;
import uni.usic.domain.entity.maintasks.Task;
import uni.usic.domain.enums.TaskPriority;
import uni.usic.domain.enums.TaskProgress;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TaskApplication {
    private static Scanner keyboard = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

    public static void main(String[] args) {
        System.out.println("Welcome to Task Manager.");

        System.out.println("1. View task list");
        System.out.println("2. View task details");
        System.out.println("3. Create task");
        System.out.println("4. Modify task");
        System.out.println("5. Delete task");

        System.out.print("Please select menu: ");
        int num = keyboard.nextInt();

        switch (num) {
            case 1:
                viewTaskList();
                break;
            case 2:
                viewTaskDetails();
                break;
            case 3:
                createTask();
                break;
            case 4:
                modifyTask();
                break;
            case 5:
                deleteTask();
                break;
            default:
                System.out.println("");
        }
    }

    public static void viewTaskList() {
        TaskService taskService = new TaskService();
        List<Task> taskList = taskService.viewTaskList();
        for(Task task : taskList) {
            System.out.println(task.toString());
        }
    }

    public static void viewTaskDetails() {
        System.out.print("Please enter task ID: ");
        String taskId = keyboard.next();

        TaskService taskService = new TaskService();
        taskService.viewTask(taskId);
    }

    public static void createTask() {
        System.out.print("Please enter title: ");
        String title = keyboard.next();
        System.out.print("Please enter description: ");
        String description = keyboard.next();
        System.out.print("Please enter start date (ddMMyyyy): ");
        String startDateStr = keyboard.next();
        System.out.print("Please enter end date (ddMMyyyy): ");
        String endDateStr = keyboard.next();
        System.out.print("Please enter priority(high, medium, low): ");
        String priorityStr = keyboard.next();

        LocalDate startDate = convertToLocalDate(startDateStr);
        LocalDate endDate = convertToLocalDate(endDateStr);
        TaskPriority priority = convertToTaskPriority(priorityStr);

        TaskService taskService = new TaskService();
        Task createdTask = taskService.createTask(title, description, startDate, endDate, priority);

        if(createdTask!=null) {
            System.out.println("Task successfully created.");
        } else {
            System.out.println("Task creation failed.");
        }
    }

    public static void modifyTask() {
        System.out.print("Please enter task ID to modify: ");
        String taskId = keyboard.next();

        boolean taskExists = checkIfTaskExists(taskId);
        if(!taskExists) {
            System.out.println("No task has found like task ID: " + taskId);
            return;
        }

        TaskService taskService = new TaskService();
        taskService.viewTask(taskId);

        System.out.print("Please enter task title: ");
        String newTitle = keyboard.next();
        System.out.print("Please enter task description: ");
        String newDescription = keyboard.next();
        System.out.print("Please enter start date (ddMMyyyy): ");
        String newStartDateStr = keyboard.next();
        System.out.print("Please enter end date (ddMMyyyy): ");
        String newEndDateStr = keyboard.next();
        System.out.print("Please enter priority: ");
        String newPriorityStr = keyboard.next();
        System.out.print("Please enter priority: ");
        String newProgressStr = keyboard.next();
        System.out.print("Please enter priority: ");
        int newReminderDaysBefore = keyboard.nextInt();

        LocalDate newStartDate = convertToLocalDate(newStartDateStr);
        LocalDate newEndDate = convertToLocalDate(newEndDateStr);
        TaskPriority newPriority = convertToTaskPriority(newPriorityStr);
        TaskProgress newProgress = convertToTaskProgress(newProgressStr);

        boolean result = taskService.modifyTask(taskId, newTitle, newDescription, newStartDate, newEndDate, newPriority, newProgress, newReminderDaysBefore);

        if(result) {
            System.out.println("Task successfully modified.");
        } else {
            System.out.println("Task modification failed.");
        }
    }

    public static void deleteTask() {
        System.out.print("Please enter task ID to delete: ");
        String taskId = keyboard.next();

        boolean taskExists = checkIfTaskExists(taskId);
        if(!taskExists) {
            System.out.println("No task has found like task ID: " + taskId);
            return;
        }
    }

    public static boolean checkIfTaskExists(String id) {
        TaskService taskService = new TaskService();
        return taskService.getTaskById(id) != null;
    }

    public static void checkDateFormat(String dateStr) {

    }

    public static LocalDate convertToLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, formatter);
    }

    public static TaskPriority convertToTaskPriority(String priorityStr) {
        return TaskPriority.valueOf(priorityStr.toUpperCase());
    }

    public static TaskProgress convertToTaskProgress(String progressStr) {
        return TaskProgress.valueOf(progressStr.toUpperCase());
    }
}
