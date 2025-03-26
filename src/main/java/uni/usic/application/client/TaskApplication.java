package uni.usic.application.client;

import uni.usic.application.service.TaskManager;
import uni.usic.application.service.TaskService;
import uni.usic.domain.entity.maintasks.Task;
import uni.usic.domain.enums.TaskPriority;
import uni.usic.domain.enums.TaskProgress;
import uni.usic.infrastructure.repository.TaskFileRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class TaskApplication {
    private static Scanner keyboard = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
    private static final String TASKS_FILE_PATH = "src/main/java/uni/usic/infrastructure/storage/tasks.txt";

    // Dependency Injection
    private static final TaskFileRepository taskFileRepository = new TaskFileRepository(TASKS_FILE_PATH);
    private static final TaskService taskService = new TaskService();
    private static final TaskManager taskManager = new TaskManager(taskService, taskFileRepository);

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
                System.out.println("Invalid menu");
        }

        // Java 14+
//        switch (num) {
//            case 1 -> viewTaskList();
//            case 2 -> viewTaskDetails();
//            case 3 -> createTask();
//            case 4 -> modifyTask();
//            case 5 -> deleteTask();
//            default -> System.out.println("Invalid menu");
//        }
    }

    public static void viewTaskList() {
        System.out.println("\n=== View Task List ===");
        List<Task> taskList = taskManager.viewTaskList();
        for(Task task : taskList) {
            System.out.println(task.toString() + "\n");
        }
    }

    public static void viewTaskDetails() {
        System.out.println("\n=== View Task Details ===");
        System.out.print("Please enter task ID: ");
        String taskId = keyboard.next();

        Task searchedTask = taskManager.viewTaskById(taskId);

        if(searchedTask!=null) {
            System.out.println("\n"+searchedTask.toString());
        } else {
            System.out.println("No task has found like task ID: " + taskId);
        }
    }

    public static void createTask() {
        System.out.println("\n=== Create Task ===");
        System.out.print("Please enter title: ");
        String title = keyboard.nextLine();
        System.out.print("Please enter description: ");
        String description = keyboard.nextLine();
        System.out.print("Please enter start date (ddMMyyyy): ");
        String startDateStr = keyboard.next();
        System.out.print("Please enter end date (ddMMyyyy): ");
        String endDateStr = keyboard.next();
        System.out.print("Please enter priority(high, medium, low): ");
        String priorityStr = keyboard.next();

        LocalDate startDate = convertToLocalDate(startDateStr);
        LocalDate endDate = convertToLocalDate(endDateStr);
        TaskPriority priority = convertToTaskPriority(priorityStr);

        Task createdTask = taskManager.createTask(title, description, startDate, endDate, priority);

        if(createdTask!=null) {
            System.out.println("Task successfully created.");
        } else {
            System.out.println("Task creation failed.");
        }
    }

    public static void modifyTask() {
        System.out.println("\n=== Modify Task ===");
        System.out.print("Please enter task ID to modify: ");
        String taskId = keyboard.next();

        boolean taskExists = taskManager.checkIfTaskExists(taskId);
        if(!taskExists) {
            System.out.println("No task has found like task ID: " + taskId);
            return;
        }

        System.out.print("Please enter task title: ");
        String newTitle = keyboard.nextLine();
        System.out.print("Please enter task description: ");
        String newDescription = keyboard.nextLine();
        System.out.print("Please enter start date (ddMMyyyy): ");
        String newStartDateStr = keyboard.next();
        System.out.print("Please enter end date (ddMMyyyy): ");
        String newEndDateStr = keyboard.next();
        System.out.print("Please enter priority: ");
        String newPriorityStr = keyboard.next();
        System.out.print("Please enter progress: ");
        String newProgressStr = keyboard.next();
        System.out.print("Please enter reminder days before: ");
        int newReminderDaysBefore = keyboard.nextInt();

        LocalDate newStartDate = convertToLocalDate(newStartDateStr);
        LocalDate newEndDate = convertToLocalDate(newEndDateStr);
        TaskPriority newPriority = convertToTaskPriority(newPriorityStr);
        TaskProgress newProgress = convertToTaskProgress(newProgressStr);

        Task modifiedTask = taskManager.modifyTask(taskId, newTitle, newDescription, newStartDate, newEndDate, newPriority, newProgress, newReminderDaysBefore);

        if(modifiedTask!=null) {
            System.out.println("Task successfully modified.");
        } else {
            System.out.println("Task modification failed.");
        }
    }

    public static void deleteTask() {
        System.out.println("\n=== Delete Task ===");
        System.out.print("Please enter task ID to delete: ");
        String taskId = keyboard.next();

//        System.out.println("Are you sure you want to delete " + taskId + "?");

        boolean result = taskManager.deleteTask(taskId);

        if(result) {
            System.out.println("Task successfully deleted.");
        } else {
            System.out.println("Task deletion failed.");
        }
    }

    public static void checkDateFormat(String dateStr) {

    }

    public static LocalDate convertToLocalDate(String dateStr) {
//        try {
            return LocalDate.parse(dateStr, formatter);
//        } catch (DateTimeParseException e) {
//            System.out.println("Invalid date format! Please use ddMMyyyy.");
//            return null;
//        }
    }

    public static TaskPriority convertToTaskPriority(String priorityStr) {
        return TaskPriority.valueOf(priorityStr.toUpperCase());
    }

    public static TaskProgress convertToTaskProgress(String progressStr) {
        return TaskProgress.valueOf(progressStr.toUpperCase());
    }
}
