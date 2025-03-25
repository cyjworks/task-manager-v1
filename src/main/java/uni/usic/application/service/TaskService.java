package uni.usic.application.service;

import uni.usic.domain.entity.maintasks.Task;
import uni.usic.domain.enums.TaskPriority;
import uni.usic.domain.enums.TaskProgress;
import uni.usic.infrastructure.repository.TaskFileRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskService implements TaskOperations {
    //    private static List<Task> taskList = new ArrayList<>();
    private static Map<String, Task> taskMap = new HashMap<>();

    @Override
    public void viewTask(String id) {
        Task task = getTaskById(id);
        if(task == null) {
            System.out.println("No task has found like task ID: " + id);
            return;
        }
        System.out.println(task.toString());
    }

    @Override
    public Task createTask(String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority) {
        Task task = new Task(title, description, startDate, endDate, priority);
        boolean result = addTask(task);

        if(result) {
            return task;
        } else {
            return null;
        }
    }

    public boolean addTask(Task task) {
        TaskFileRepository taskFileRepository = new TaskFileRepository();
        return taskFileRepository.save(task);
    }

    @Override
    public boolean modifyTask(String id, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore) {
        Task task = getTaskById(id);
        if(task == null) {
            return false;
        }
        task.setTitle(title);
        task.setDescription(description);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setPriority(priority);
        task.setProgress(progress);
        task.setReminderDaysBefore(reminderDaysBefore);
        return true;
    }

    @Override
    public boolean updateProgress(String id, TaskProgress progress) {
        Task task = getTaskById(id);
        if(task == null) {
            return false;
        }
        task.setProgress(progress);
        return true;
    }

    @Override
    public boolean deleteTask(String id) {
        return removeTask(id);
    }

    public static List<Task> viewTaskList() {
        return new ArrayList<>(taskMap.values());
    }

    // TODO: getTaskList()?
    public static List<Task> convertTaskMapToList(Map<String, Task> taskMap) {
        return new ArrayList<>(taskMap.values()); // Convert map values to list
    }

    public static Task getTaskById(String taskId) {
        return taskMap.get(taskId);
    }



    public static boolean removeTask(String id) {
        return taskMap.remove(id) != null;
    }
}
