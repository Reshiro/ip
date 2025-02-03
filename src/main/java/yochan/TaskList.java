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
     * Adds a YoChan to the list of tasks.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the YoChan at the specified index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Removes the YoChan at the specified index.
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
     * Marks the indicated YoChan as complete.
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
     * Marks the indicated YoChan as incomplete.
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

    private boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }
}
