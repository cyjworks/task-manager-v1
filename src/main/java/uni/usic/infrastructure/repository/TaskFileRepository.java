package uni.usic.infrastructure.repository;

import uni.usic.domain.entity.maintasks.Task;
import uni.usic.domain.enums.TaskPriority;
import uni.usic.domain.enums.TaskProgress;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskFileRepository implements TaskRepository {
    private static final String TASKS_FILE = "src/main/java/uni/usic/infrastructure/storage/tasks.txt";

    @Override
    public boolean save(Task task) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(TASKS_FILE, true))) {
            writer.write(taskToString(task));
            writer.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public List<Task> findAll() {
        return loadTasksFromFile();
    }

    @Override
    public Optional<Task> findById(String taskId) {
        return loadTasksFromFile().stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst();
//        return Optional.empty();
    }

    @Override
    public List<Task> findByPriority(TaskPriority priority) {
        return loadTasksFromFile().stream()
                .filter(task -> task.getPriority().equals(priority))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findByProgress(TaskProgress progress) {
        return loadTasksFromFile().stream()
                .filter(task -> task.getProgress().equals(progress))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findByDateRange(LocalDate start, LocalDate end) {
        return null;
    }

    @Override
    public boolean update(Task task) {
        List<Task> tasks = loadTasksFromFile();
        boolean updated = false;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(TASKS_FILE))) {
            for(Task t : tasks) {
                if(t.getId().equals(task.getId())) {
                    writer.write(taskToString(task));
                    updated = true;
                } else {
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            return false;
        }
        return updated;
    }

    @Override
    public boolean deleteById(String taskId) {
        List<Task> tasks = loadTasksFromFile();
        boolean deleted = false;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(TASKS_FILE))) {
            for(Task task : tasks) {
                if(!task.getId().equals(taskId)) {
                    writer.write(taskToString(task));
                    writer.newLine();
                } else {
                    deleted = true;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return deleted;
    }

    @Override
    public void deleteAll() {
        try{
            Files.deleteIfExists(Paths.get(TASKS_FILE));
        } catch (IOException e) {
            System.out.println("Error deleting all tasks: " + e.getMessage());
        }
    }

    private static String taskToString(Task task) {
        return task.getId() + "|" +
                task.getTitle() + "|" +
                task.getDescription() + "|" +
                task.getStartDate() + "|" +
                task.getEndDate() + "|" +
                task.getPriority() + "|" +
                task.getProgress() + "|" +
                (task.getReminderDaysBefore() == null ? "" : task.getReminderDaysBefore());
    }

    private static Task stringToTask(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 7) return null; // Ensure all fields exist

        String id = parts[0];
        String title = parts[1];
        String description = parts[2];
        LocalDate startDate = LocalDate.parse(parts[3]);
        LocalDate endDate = LocalDate.parse(parts[4]);
        TaskPriority priority = TaskPriority.valueOf(parts[5]);
        TaskProgress progress = TaskProgress.valueOf(parts[6]);
        Integer reminderDaysBefore = parts[7].isEmpty() ? null : Integer.parseInt(parts[7]);

        Task task = new Task(title, description, startDate, endDate, priority);
        task.setId(id); // Assuming Task has a setter for ID
        task.setProgress(progress);
        task.setReminderDaysBefore(reminderDaysBefore);
        return task;
    }

    public static List<Task> loadTasksFromFile() {
        List<Task> tasks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(TASKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = stringToTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            System.out.println("✅ Loaded " + tasks.size() + " tasks from file.");
        } catch (FileNotFoundException e) {
            System.out.println("No tasks file found. Creating a new one...");
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

}
