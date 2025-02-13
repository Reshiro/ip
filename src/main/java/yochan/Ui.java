package yochan;

import java.util.Scanner;

import yochan.task.Task;

/**
 * Deals with user interaction.
 *
 * @author Michael Cheong (Reshiro)
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Creates an Ui object with the system input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Prints a welcome message.
     */
    public void showWelcome() {
        System.out.println("Ough... What do you want?");
    }

    /**
     * Reads and returns the next system input.
     *
     * @return Returns the input that was read.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints an exit message.
     */
    public void showGoodbye() {
        showBorder();
        System.out.println("Bye bye!");
        showBorder();
    }

    /**
     * Prints an error with the specified message.
     *
     * @param message The error message.
     */
    public void showError(String message) {
        showBorder();
        System.out.println(message);
        showBorder();
    }

    /**
     * Prints the String representation of the task that was marked.
     *
     * @param task The task that was marked.
     */
    public void showTaskMarked(Task task) {
        showBorder();
        System.out.println("Ough! I've marked this task as done:");
        System.out.println(task);
        showBorder();
    }

    /**
     * Prints the String representation of the task that was unmarked.
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        showBorder();
        System.out.println("Ough... I've marked this task as not done yet:");
        System.out.println(task);
        showBorder();
    }

    /**
     * Prints the String representation of the added task.
     *
     * @param task The added task.
     * @param totalTasks The number of tasks in the TaskList.
     */
    public void showTaskAdded(Task task, int totalTasks) {
        showBorder();
        System.out.println("Oughkay, I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        showBorder();
    }

    /**
     * Prints the String representation of the deleted task.
     *
     * @param task The deleted task.
     * @param totalTasks The number of tasks in the TaskList.
     */
    public void showTaskDeleted(Task task, int totalTasks) {
        showBorder();
        System.out.println("Ough! I've removed this task:");
        System.out.println(task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        showBorder();
    }

    /**
     * Prints all tasks in the TaskList.
     */
    public void showTaskList(TaskList tasks) {
        showBorder();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        showBorder();
    }

    /**
     * Displays tasks matching the search keyword.
     *
     * @param tasks The matching tasks.
     */
    public void showMatchingTasks(TaskList tasks) {
        showBorder();
        if (tasks.size() == 0) {
            System.out.println("Ough... No matching tasks found!");
        } else {
            System.out.println("Ough! Here are the matching tasks in your list:");
            // Prints matching tasks as consecutive indices; might switch to task list indices later.
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
        showBorder();
    }

    private void showBorder() {
        System.out.println("-*-*-*-*-*-*-*-*-*-*-");
    }

    private String getBorder() {
        return "-*-*-*-*-*-*-*-*-*-*-";
    }

    /**
     * Closes the scanner after usage.
     */
    public void close() {
        scanner.close();
    }
}
