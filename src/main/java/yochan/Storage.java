package yochan;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import yochan.task.Task;

/**
 * Deals with saving and loading the list of tasks to the disk.
 *
 * @author Michael Cheong (Reshiro)
 */
public class Storage {
    private final Path filePath;

    /**
     * Creates a YoChan object with the specified location for the tasks to be saved at.
     */
    public Storage(String directory, String filename) {
        File dir = new File(directory);
        dir.mkdirs();
        this.filePath = Paths.get(directory, filename);
    }

    /**
     * Saves the tasks to the disk.
     *
     * @throws YoChanException If file permission issues occur.
     */
    public void saveTasks(TaskList tasks) throws YoChanException {
        try {
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < tasks.size(); i++) {
                str.append((i + 1)).append(". ").append(tasks.get(i)).append(System.lineSeparator());
            }
            Files.writeString(filePath, str, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new YoChanException("Ough.. Failed to save tasks!");
        }
    }

    /**
     * Loads and returns the saved tasks if it exists.
     *
     * @throws YoChanException If file permission issues occur.
     */
    public TaskList loadTasks() throws YoChanException {
        TaskList tasks = new TaskList();
        if (!Files.exists(filePath)) {
            return tasks;
        }

        try {
            String content = Files.readString(filePath);
            String[] lines = content.split(System.lineSeparator());
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                int sepIndex = line.indexOf(". ");
                if (sepIndex == -1) {
                    System.err.println("Ough! Skipping malformed task line: " + line);
                    continue;
                }
                String taskData = line.substring(sepIndex + 2);
                Task task = Parser.parseSavedTask(taskData);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            throw new YoChanException("Ough... Failed to load tasks! Cause: " + e.getMessage());
        }
        return tasks;
    }
}
