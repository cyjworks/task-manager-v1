package uni.usic.application.service;

import uni.usic.domain.entity.maintasks.Task;
import uni.usic.infrastructure.repository.TaskFileRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
//    private static List<Task> taskList = new ArrayList<>();
    private static Map<String, Task> taskMap = new HashMap<>();

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

    public static boolean addTask(Task task) {
//        taskMap.put(task.getId(), task);
//        Task addedTask = taskMap.get(task.getId());
//        return addedTask != null;
        TaskFileRepository taskFileRepository = new TaskFileRepository();
        return taskFileRepository.save(task);
    }

    public static boolean removeTask(String id) {
        return taskMap.remove(id) != null;
    }
}
