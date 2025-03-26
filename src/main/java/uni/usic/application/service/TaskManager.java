package uni.usic.application.service;

import uni.usic.domain.entity.maintasks.Task;
import uni.usic.domain.enums.TaskPriority;
import uni.usic.domain.enums.TaskProgress;
import uni.usic.infrastructure.repository.TaskFileRepository;

import java.time.LocalDate;

public class TaskManager implements TaskOperations {
    TaskService taskService = new TaskService();
    TaskFileRepository taskFileRepository = new TaskFileRepository();

    @Override
    public void viewTask(String id) {

    }

    @Override
    public Task createTask(String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority) {
        Task task = taskService.createTask(title, description, startDate, endDate, priority);
        boolean result = taskFileRepository.save(task);

        if(result) {
            return task;
        } else {
            return null;
        }
    }

    @Override
    public Task modifyTask(String id, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore) {
        Task task = taskService.modifyTask(id, title, description, startDate, endDate, priority, progress, reminderDaysBefore);
        boolean result = taskFileRepository.update(task);

        if(result) {
            return task;
        } else {
            return null;
        }
    }

    @Override
    public boolean updateProgress(String id, TaskProgress progress) {
        return false;
    }

    @Override
    public boolean deleteTask(String id) {
        return false;
    }
}
