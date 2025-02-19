package yochan.command;

import yochan.Storage;
import yochan.TaskList;
import yochan.Ui;
import yochan.YoChanException;

/**
 * Represents setting a priority level for a task.
 *
 * @author Michael Cheong (Reshiro)
 */
public class PriorityCommand extends Command {
    private int taskNumber;
    private int priority;

    /**
     * Creates a PriorityCommand object that indicates the program is to end.
     */
    public PriorityCommand(int taskNumber, int priority) {
        super();
        this.taskNumber = taskNumber;
        this.priority = priority;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws YoChanException {
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            throw new YoChanException("Ough! Please provide a valid task number to set the priority for!");
        }
        tasks.setTaskPriority(taskNumber - 1, priority);
        ui.showTaskModifiedPriority(tasks.get(taskNumber - 1));
        storage.saveTasks(tasks);
    }
}
