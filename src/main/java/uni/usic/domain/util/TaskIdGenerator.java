package uni.usic.domain.util;

import uni.usic.domain.entity.maintasks.Task;
import uni.usic.infrastructure.repository.TaskFileRepository;

import java.util.List;

public class TaskIdGenerator {
    private static int currentId = 1;
    private static final String PREFIX = "TASK";

    private static final String TASKS_FILE_PATH = "src/main/java/uni/usic/infrastructure/storage/tasks.txt";
    private static final TaskFileRepository taskFileRepository = new TaskFileRepository(TASKS_FILE_PATH);

    public static void initialise(List<Task> tasks) {
        int max = tasks.stream()
                .map(Task::getId)
                .map(id -> id.replace(PREFIX + "-", ""))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
        currentId = max + 1;
    }

    public static String generateId() {
        List<Task> existingTasks = taskFileRepository.loadTaskListFromFile();
        TaskIdGenerator.initialise(existingTasks);
        return PREFIX + "-" + currentId++;
    }
}
