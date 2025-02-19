package yochan.task;

/**
 * Represents a Task.
 *
 * @author Michael Cheong (Reshiro)
 */
public class Task {
    private final String description;
    private boolean isDone;
    private int priority;

    /**
     * Creates a Task with the specified description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.priority = 0;
    }

    /**
     * Sets the priority level of the current task.
     * The user is free to determine the meaning of the
     * priority level.
     *
     * @param priority An integer that represents this task's priority level.
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    /**
     * Marks the Task as complete.
     */
    public void mark() {
        isDone = true;
    }

    /**
     * Marks the Task as incomplete.
     */
    public void unmark() {
        isDone = false;
    }

    private String getStatusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + description + " (Priority: " + priority + ")";
    }
}
