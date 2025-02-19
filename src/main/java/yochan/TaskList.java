package yochan;

import java.util.ArrayList;
import java.util.List;

import yochan.task.Task;

/**
 * Represents a list of tasks.
 *
 * @author Michael Cheong (Reshiro)
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Creates a list of tasks.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the list of tasks.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the task at the specified index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Removes the task at the specified index.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the number of tasks in the list of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Marks the indicated task as complete.
     *
     * @throws YoChanException If the index is invalid.
     */
    public void markTask(int index) throws YoChanException {
        if (isValidIndex(index)) {
            tasks.get(index).mark();
        } else {
            throw new YoChanException("Invalid task number! >:(");
        }
    }

    /**
     * Marks the indicated task as incomplete.
     *
     * @throws YoChanException If the index is invalid.
     */
    public void unmarkTask(int index) throws YoChanException {
        if (isValidIndex(index)) {
            tasks.get(index).unmark();
        } else {
            throw new YoChanException("Invalid task number! >:(");
        }
    }

    public void setTaskPriority(int index, int priority) throws YoChanException {
        if (isValidIndex(index)) {
            tasks.get(index).setPriority(priority);
        } else {
            throw new YoChanException("Invalid task number! >:(");
        }
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }
}
