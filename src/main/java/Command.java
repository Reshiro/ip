/**
 * Represents a command given by the user.
 *
 * @author Michael Cheong (Reshiro)
 */
public abstract class Command {
    /**
     * Indicates if the Command is an Exit command.
     */
    protected boolean isExit;

    public Command() {
        this.isExit = false;
    }

    /**
     * Executes the command.
     * @param tasks The list of tasks that the Command may operate on.
     *
     * @throws YoChanException If any issue occurs during the execution of the Command.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws YoChanException;

    /**
     * Indicates if the Command is an Exit command.
     */
    public boolean isExit() {
        return isExit;
    }
}
