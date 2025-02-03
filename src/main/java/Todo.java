/**
 * Represents a Task with no deadline.
 *
 * @author Michael Cheong (Reshiro)
 */
public class Todo extends Task {
    /**
     * Creates a Todo Task with the specified description.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
