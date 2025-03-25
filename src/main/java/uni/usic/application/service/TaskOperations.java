package uni.usic.application.service;

import uni.usic.domain.entity.maintasks.Task;
import uni.usic.domain.enums.TaskPriority;
import uni.usic.domain.enums.TaskProgress;

import java.time.LocalDate;

public interface TaskOperations {
    void viewTask(String id);
    Task createTask(String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority);
    boolean modifyTask(String id, String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority, TaskProgress progress, Integer reminderDaysBefore);
    boolean updateProgress(String id, TaskProgress progress);
    boolean deleteTask(String id);
}
