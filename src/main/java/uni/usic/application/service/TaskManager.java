package uni.usic.application.service;

import uni.usic.domain.entity.maintasks.Task;
import uni.usic.domain.enums.TaskPriority;
import uni.usic.domain.enums.TaskProgress;
import uni.usic.infrastructure.repository.TaskFileRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TaskManager implements TaskOperations {
    TaskService taskService = new TaskService();
    TaskFileRepository taskFileRepository = new TaskFileRepository();

    @Override
    public List<Task> viewTaskList() {
        return taskFileRepository.loadTaskListFromFile();
    }

    @Override
    public void viewTask(String id) {
        taskFileRepository.findById(id);
    }

    public Task viewTaskById(String id) {
        Map<String, Task> taskMap = taskFileRepository.loadTaskMapFromFile();
        return taskService.viewTaskById(id, taskMap);
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
