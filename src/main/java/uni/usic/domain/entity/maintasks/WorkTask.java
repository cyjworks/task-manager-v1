package uni.usic.domain.entity.maintasks;

import uni.usic.domain.enums.TaskPriority;

import java.time.LocalDate;

public class WorkTask extends Task {
    private String careerTask;

    public WorkTask(String title, String description, LocalDate startDate, LocalDate endDate, TaskPriority priority) {
        super(title, description, startDate, endDate, priority);
    }
}
