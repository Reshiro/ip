package yochan.command;

import yochan.Storage;
import yochan.TaskList;
import yochan.Ui;

/**
 * Represents the command for listing tasks in the YoChan.TaskList.
 *
 * @author Michael Cheong (Reshiro)
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks);
    }
}
