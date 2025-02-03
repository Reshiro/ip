package yochan.command;

import yochan.Storage;
import yochan.TaskList;
import yochan.Ui;

/**
 * Represents the end of the program.
 *
 * @author Michael Cheong (Reshiro)
 */
public class ExitCommand extends Command {
    /**
     * Creates an YoChan.ExitCommand object that indicates the program is to end.
     */
    public ExitCommand() {
        super();
        this.isExit = true;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }
}
