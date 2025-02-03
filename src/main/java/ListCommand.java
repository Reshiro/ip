/**
 * Represents the command for listing tasks in the TaskList.
 *
 * @author Michael Cheong (Reshiro)
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks);
    }
}
