package yochan.task;

/**
 * Represents a YoChan.Task with no deadline.
 *
 * @author Michael Cheong (Reshiro)
 */
public class Todo extends Task {
    /**
     * Creates a YoChan.Todo YoChan.Task with the specified description.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
