package yochan.command;

import yochan.Storage;
import yochan.TaskList;
import yochan.Ui;
import yochan.YoChanException;

/**
 * Represents marking a YoChan.Task as incomplete.
 *
 * @author Michael Cheong (Reshiro)
 */
public class UnmarkCommand extends Command {
    private final int taskNumber;

    /**
     * Creates an YoChan.UnmarkCommand object with the index of the task to be marked as incomplete.
     */
    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YoChanException {
        tasks.unmarkTask(taskNumber - 1);
        ui.showTaskUnmarked(tasks.get(taskNumber - 1));
        storage.saveTasks(tasks);
    }
}
