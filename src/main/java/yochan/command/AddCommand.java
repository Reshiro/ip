package yochan.command;

import yochan.Storage;
import yochan.TaskList;
import yochan.Ui;
import yochan.YoChanException;
import yochan.task.Task;

/**
 * Represents the command for adding a Task to the list of tasks.
 *
 * @author Michael Cheong (Reshiro)
 */
public class AddCommand extends Command {
    private final Task task;

    /**
     * Creates an AddCommand object with the specified Task to be added.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YoChanException {
        tasks.add(task);
        ui.showTaskAdded(task, tasks.size());
        storage.saveTasks(tasks);
    }

    @Override
    public String toString() {
        return this.task.toString();
    }
}
