package yochan.task;

/**
 * Represents a YoChan.Task.
 *
 * @author Michael Cheong (Reshiro)
 */
public class Task {
    private String description;
    private boolean isDone;

    /**
     * Creates a YoChan.Task with the specified description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the YoChan.Task as complete.
     */
    public void mark() {
        isDone = true;
    }

    /**
     * Marks the YoChan.Task as incomplete.
     */
    public void unmark() {
        isDone = false;
    }

    private String getStatusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }
}
