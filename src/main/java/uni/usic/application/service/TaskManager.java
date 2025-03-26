package uni.usic.application.service;

import uni.usic.domain.entity.maintasks.Task;
import uni.usic.domain.enums.TaskPriority;
import uni.usic.domain.enums.TaskProgress;
import uni.usic.infrastructure.repository.TaskFileRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TaskManager {
    TaskService taskService = new TaskService();
    TaskFileRepository taskFileRepository = new TaskFileRepository();

    public List<Task> viewTaskList() {
        return taskFileRepository.loadTaskListFromFile();
    }

    public void viewTask(String id) {
        taskFileRepository.findById(id);
    }

    public Task viewTaskById(String id) {
        Map<String, Task> taskMap = taskFileRepository.loadTaskMapFromFile();
        return taskService.viewTaskById(id, taskMap);
    }

    public Task createTask(String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority) {
        Task task = taskService.createTask(title, description, startDate, endDate, priority);
        boolean result = taskFileRepository.save(task);

        if(result) {
            return task;
        } else {
            return null;
        }
    }

    public Task modifyTask(String id, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore) {
        Map<String, Task> taskMap = taskFileRepository.loadTaskMapFromFile();
        Task taskToModify = taskService.getTaskById(id, taskMap);
        if(taskToModify==null) {
            System.out.println("No task has found like task ID: " + id);
            return null;
        }

        Task modifiedTask = taskService.modifyTask(taskToModify, id, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        boolean result = taskFileRepository.update(modifiedTask);

        if(result) {
            return modifiedTask;
        } else {
            return null;
        }
    }

    public boolean checkIfTaskExists(String id) {
        Map<String, Task> taskMap = taskFileRepository.loadTaskMapFromFile();
        TaskService taskService = new TaskService();
        return taskService.getTaskById(id, taskMap) != null;
    }

    public boolean updateProgress(String id, TaskProgress progress) {
        return false;
    }

    public boolean deleteTask(String id) {
        boolean taskExists = checkIfTaskExists(id);
        if(!taskExists) {
            System.out.println("No task has found like task ID: " + id);
            return false;
        }

        return true;
    }
}
