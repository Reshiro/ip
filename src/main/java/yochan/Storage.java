package yochan;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yochan.task.Deadline;
import yochan.task.Event;
import yochan.task.Task;
import yochan.task.Todo;

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
                Task task = parseTaskFromSaved(taskData);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            throw new YoChanException("Ough... Failed to load tasks! Cause: " + e.getMessage());
        }
        return tasks;
    }

    private Task parseTaskFromSaved(String taskData) {
        try {
            taskData = taskData.trim();
            Task task = null;
            // Extract priority from saved task data using regex
            int loadedPriority = 0;
            Pattern priorityPattern = Pattern.compile(" \\(Priority: (-?\\d+)\\)");
            Matcher matcher = priorityPattern.matcher(taskData);
            if (matcher.find()) {
                loadedPriority = Integer.parseInt(matcher.group(1));
            }

            if (taskData.startsWith("[T]")) {
                String description = taskData.substring(6);
                // Remove trailing ' (Priority: X)' if present
                description = description.replaceAll("\\s*\\(Priority: -?\\d+\\)", "");
                task = new Todo(description);
            } else if (taskData.startsWith("[D]")) {
                String[] parts = taskData.substring(6).split(" \\(by: ");
                if (parts.length < 2) {
                    throw new YoChanException("Malformed deadline task data");
                }
                // Remove trailing ' (Priority: X)' from description if present
                String description = parts[0].replaceAll("\\s*\\(Priority: -?\\d+\\)", "");
                String by = parts[1].substring(0, parts[1].length() - 1);
                task = new Deadline(description, convertSavedDateToInputFormat(by));
            } else if (taskData.startsWith("[E]")) {
                String[] parts = taskData.substring(6).split(" \\(from: ");
                if (parts.length < 2) {
                    throw new YoChanException("Malformed event task data");
                }
                // Remove trailing ' (Priority: X)' from description if present
                String description = parts[0].replaceAll("\\s*\\(Priority: -?\\d+\\)", "");
                String[] timeParts = parts[1].split(" to: ");
                if (timeParts.length < 2) {
                    throw new YoChanException("Malformed event time data");
                }
                String from = timeParts[0];
                String to = timeParts[1].substring(0, timeParts[1].length() - 1);
                task = new Event(description, convertSavedDateToInputFormat(from), convertSavedDateToInputFormat(to));
            }
            if (task != null) {
                task.setPriority(loadedPriority);
            }
            if (task != null && taskData.contains("[X]")) {
                task.mark();
            }
            return task;
        } catch (YoChanException e) {
            System.err.println("Ough! Failed to load task: " + taskData + ". Error: " + e.getMessage());
            return null;
        }
    }

    private String convertSavedDateToInputFormat(String savedDate) {
        try {
            DateTimeFormatter savedFormat = DateTimeFormatter.ofPattern("MMM d yyyy HHmm");
            LocalDateTime dateTime = LocalDateTime.parse(savedDate, savedFormat);
            return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        } catch (DateTimeParseException e) {
            System.err.println("Ough! Could not parse saved date: " + savedDate + ". Using original value.");
            return savedDate;
        }
    }
}
