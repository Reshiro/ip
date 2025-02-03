package yochan.command;

import yochan.Storage;
import yochan.TaskList;
import yochan.Ui;
import yochan.YoChanException;

/**
 * Represents marking a Task as complete.
 *
 * @author Michael Cheong (Reshiro)
 */
public class MarkCommand extends Command {
    private final int taskNumber;

    /**
     * Creates a MarkCommand object with the index of the Task to be marked.
     */
    public MarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YoChanException {
        tasks.markTask(taskNumber - 1);
        ui.showTaskMarked(tasks.get(taskNumber - 1));
        storage.saveTasks(tasks);
    }
}
