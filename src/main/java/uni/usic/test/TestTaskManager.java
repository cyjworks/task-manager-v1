//package uni.usic.test;
//
//import uni.usic.application.service.TaskManager;
//import uni.usic.domain.entity.maintasks.Task;
//import uni.usic.domain.enums.TaskPriority;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public class TestTaskManager {
//    public static void main(String[] args) {
//
//
//        System.out.println("=== Test for TaskManger class ===\n");
//
//        int totalTests = 0, passedTests = 0;
//
//        // Run all tests
//        totalTests++;
//        if(testAddTask()) {
//            passedTests++;
//        }
//
//        totalTests++;
//        if(testViewTaskList()) {
//            passedTests++;
//        }
//
//        totalTests++;
//        if(testGetTaskById()) {
//            passedTests++;
//        }
//
//        totalTests++;
//        if(testRemoveTask()) {
//            passedTests++;
//        }
//
//        System.out.println("\nTest Summary: " + passedTests + "/" + totalTests + " tests passed.");
//    }
//
//    public static boolean testAddTask() {
//        System.out.println("Running testAddTask()...");
//
//        Task task = new Task("Study Java", "Complete tutorial", LocalDate.now(), LocalDate.now().plusDays(3), TaskPriority.HIGH);
//        boolean added = TaskManager.addTask(task);
//
//        if (!added) return fail("Task was not added successfully.");
//        return pass();
//    }
//
//    public static boolean testViewTaskList() {
//        System.out.println("Running testViewTaskList()...");
//
//        List<Task> tasks = TaskManager.viewTaskList();
//        if (tasks == null) return fail("Task list should not be null.");
//
//        return pass();
//    }
//
//    public static boolean testGetTaskById() {
//        System.out.println("Running testGetTaskById()...");
//
//        Task task = TaskManager.getTaskById("TASK-1");
//        if (task == null) return fail("Task with ID TASK-1 should exist.");
//        return pass();
//    }
//
//    public static boolean testRemoveTask() {
//        System.out.println("Running testRemoveTask()...");
//
//        boolean removed = TaskManager.removeTask("TASK-1");
//        if (!removed) return fail("Task was not removed successfully.");
//
//        Task task = TaskManager.getTaskById("TASK-1");
//        if (task != null) return fail("Task should not exist after removal.");
//        return pass();
//    }
//
//    /**
//     * Helper method to print success message.
//     * @return
//     */
//    public static boolean pass() {
//        System.out.println("Test Passed!");
//        return true;
//    }
//
//    /**
//     * Helper method to print failure message.
//     * @param message
//     * @return
//     */
//    public static boolean fail(String message) {
//        System.out.println("Test Failed! Reason: " + message);
//        return false;
//    }
//}
