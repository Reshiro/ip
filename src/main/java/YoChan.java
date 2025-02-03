
/**
 * Represents the YoChan chatbot.
 *
 * @author Michael Cheong (Reshiro)
 */
public class YoChan {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Represents the chatbot with the specified saved data.
     *
     * @param dataDir The directory of the saved data if it exists.
     * @param filename The name of the saved data file if it exists.
     */
    public YoChan(String dataDir, String filename) {
        ui = new Ui();
        storage = new Storage(dataDir, filename);
        TaskList loadedTasks;
        try {
            loadedTasks = storage.loadTasks();
        } catch (YoChanException e) {
            ui.showError(e.getMessage());
            loadedTasks = new TaskList();
        }
        tasks = loadedTasks;
    }

    private void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parseCommand(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (YoChanException e) {
                ui.showError(e.getMessage());
            }
        }
        ui.close();
    }

    public static void main(String[] args) {
        new YoChan("data", "YoChan.txt").run();
    }
}
