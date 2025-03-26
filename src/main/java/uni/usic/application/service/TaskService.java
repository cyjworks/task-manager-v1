package uni.usic.application.service;

import uni.usic.domain.entity.maintasks.Task;
import uni.usic.domain.enums.TaskPriority;
import uni.usic.domain.enums.TaskProgress;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskService implements TaskOperations {
    //    private static List<Task> taskList = new ArrayList<>();
    private static Map<String, Task> taskMap = new HashMap<>();

    @Override
    public List<Task> viewTaskList() {
        return null;
    }

    @Override
    public void viewTask(String id) {
        Task task = getTaskById(id);
        if(task == null) {
            System.out.println("No task has found like task ID: " + id);
            return;
        }
        System.out.println(task.toString());
    }

    public Task getTaskById(String taskId) {
        return taskMap.get(taskId);
    }

    public Task viewTaskById(String id, Map<String, Task> taskMap) {
        Task task = getTaskById(id, taskMap);
        if(task == null) {
            return null;
        }
        return task;
    }

    public Task getTaskById(String taskId, Map<String, Task> taskMap) {
        return taskMap.get(taskId.trim());
    }

    @Override
    public Task createTask(String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority) {
        return new Task(title, description, startDate, endDate, priority);
    }

    @Override
    public Task modifyTask(Task task, String id, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore) {
        task.setTitle(title);
        task.setDescription(description);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setPriority(priority);
        task.setProgress(progress);
        task.setReminderDaysBefore(reminderDaysBefore);
        return task;
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

    // TODO: getTaskList()?
    public List<Task> convertTaskMapToList(Map<String, Task> taskMap) {
        return new ArrayList<>(taskMap.values()); // Convert map values to list
    }



    public boolean removeTask(String id) {
        return taskMap.remove(id) != null;
    }
}
