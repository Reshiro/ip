package yochan.command;

import yochan.Storage;
import yochan.TaskList;
import yochan.Ui;
import yochan.YoChanException;
import yochan.task.Task;

/**
 * Represents the deletion of a YoChan.Task from the list of tasks.
 *
 * @author Michael Cheong (Reshiro)
 */
public class DeleteCommand extends Command {
    private final int taskNumber;

    /**
     * Creates a YoChan.DeleteCommand object with the specified index of the YoChan.Task to be deleted.
     */
    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YoChanException {
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            throw new YoChanException("Ough! Please provide a valid task number to delete!");
        }
        Task deletedTask = tasks.remove(taskNumber - 1);
        ui.showTaskDeleted(deletedTask, tasks.size());
        storage.saveTasks(tasks);
    }
}
