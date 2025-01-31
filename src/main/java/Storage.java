import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Deals with saving and loading the list of tasks to the disk.
 */
public class Storage {
    private final Path filePath;

    /**
     * Creates a Storage object with the specified location for the tasks to be saved at.
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
                String taskData = line.substring(line.indexOf(". ") + 2);
                Task task = parseTaskFromSaved(taskData);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            throw new YoChanException("Ough... Failed to load tasks!");
        }
        return tasks;
    }

    private Task parseTaskFromSaved(String taskData) {
        try {
            Task task = null;
            if (taskData.startsWith("[T]")) {
                String description = taskData.substring(6);
                task = new Todo(description);
            } else if (taskData.startsWith("[D]")) {
                String[] parts = taskData.substring(6).split(" \\(by: ");
                String description = parts[0];
                String by = parts[1].substring(0, parts[1].length() - 1);
                task = new Deadline(description, convertSavedDateToInputFormat(by));
            } else if (taskData.startsWith("[E]")) {
                String[] parts = taskData.substring(6).split(" \\(from: ");
                String description = parts[0];
                String[] timeParts = parts[1].split(" to: ");
                String from = timeParts[0];
                String to = timeParts[1].substring(0, timeParts[1].length() - 1);
                task = new Event(description, convertSavedDateToInputFormat(from),
                        convertSavedDateToInputFormat(to));
            }
            if (task != null && taskData.contains("[X]")) {
                task.mark();
            }
            return task;
        } catch (YoChanException e) {
            System.out.println("Ough! Failed to load task: " + taskData);
            return null;
        }
    }

    private String convertSavedDateToInputFormat(String savedDate) {
        try {
            DateTimeFormatter savedFormat = DateTimeFormatter.ofPattern("MMM d yyyy HHmm");
            LocalDateTime dateTime = LocalDateTime.parse(savedDate, savedFormat);
            return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        } catch (DateTimeParseException e) {
            return savedDate;
        }
    }
}
