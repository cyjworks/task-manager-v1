package uni.usic.application.service;

import uni.usic.domain.entity.maintasks.Task;
import uni.usic.domain.enums.TaskPriority;
import uni.usic.domain.enums.TaskProgress;

import java.time.LocalDate;

public class TaskService implements TaskOperations {
    @Override
    public void viewTask(String id) {
        Task task = TaskManager.getTaskById(id);
        if(task == null) {
            System.out.println("No task has found like task ID: " + id);
            return;
        }
        System.out.println(task.toString());
    }

    @Override
    public Task createTask(String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority) {
        Task task = new Task(title, description, startDate, endDate, priority);
        return task;
    }

    @Override
    public boolean modifyTask(String id, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore) {
        Task task = TaskManager.getTaskById(id);
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
        Task task = TaskManager.getTaskById(id);
        if(task == null) {
            return false;
        }
        task.setProgress(progress);
        return true;
    }

    @Override
    public boolean deleteTask(String id) {
        return TaskManager.removeTask(id);
    }
}
