package yochan.command;

import yochan.Storage;
import yochan.TaskList;
import yochan.Ui;
import yochan.YoChanException;

/**
 * Represents finding tasks containing a keyword.
 *
 * @author Michael Cheong (Reshiro)
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Creates a FindCommand object with the specified keyword.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YoChanException {
        TaskList matchingTasks = new TaskList();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).toString().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(tasks.get(i));
            }
        }
        ui.showMatchingTasks(matchingTasks);
    }
}
