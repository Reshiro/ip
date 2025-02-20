package yochan;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yochan.command.AddCommand;
import yochan.command.Command;
import yochan.command.DeleteCommand;
import yochan.command.ExitCommand;
import yochan.command.FindCommand;
import yochan.command.ListCommand;
import yochan.command.MarkCommand;
import yochan.command.PriorityCommand;
import yochan.command.UnmarkCommand;
import yochan.task.Deadline;
import yochan.task.Event;
import yochan.task.Task;
import yochan.task.Todo;

/**
 * Deals with processing user inputs and saved tasks.
 *
 * @author Michael Cheong (Reshiro)
 */
public class Parser {
    /**
     * Parses the given input into a command and returns it.
     *
     * @throws YoChanException If input is invalid.
     */
    public static Command parseCommand(String userInput) throws YoChanException {
        Command directCommand = getDirectCommand(userInput);
        if (directCommand != null) {
            return directCommand;
        }
        return parseOtherCommands(userInput);
    }

    private static Command getDirectCommand(String userInput) {
        if (userInput.equals("bye")) {
            return new ExitCommand();
        } else if (userInput.equals("list")) {
            return new ListCommand();
        }
        return null; // No direct command found
    }

    private static Command parseOtherCommands(String userInput) throws YoChanException {
        String trimmed = userInput.trim();
        String[] tokens = trimmed.split("\\s+", 2);
        String commandWord = tokens[0];
        String args = tokens.length > 1 ? tokens[1] : "";
        return switch (commandWord) {
        case "mark" -> parseMarkCommand(args);
        case "unmark" -> parseUnmarkCommand(args);
        case "todo" -> parseTodoCommand(args);
        case "deadline" -> parseDeadline(args);
        case "event" -> parseEvent(args);
        case "delete" -> parseDelete(args);
        case "find" -> parseFind(args);
        case "priority" -> parsePriorityCommand(args);
        default -> throw new YoChanException("Ough!! Unknown command!");
        };
    }

    private static Command parseMarkCommand(String args) throws YoChanException {
        if (args.trim().isEmpty()) {
            throw new YoChanException("Specify a valid task number after 'mark'! >:(");
        }
        try {
            int taskNumber = Integer.parseInt(args.trim());
            return new MarkCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new YoChanException("Specify a valid task number after 'mark'! >:(");
        }
    }

    private static Command parseUnmarkCommand(String args) throws YoChanException {
        if (args.trim().isEmpty()) {
            throw new YoChanException("Specify a valid task number after 'unmark'! >:(");
        }
        try {
            int taskNumber = Integer.parseInt(args.trim());
            return new UnmarkCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new YoChanException("Specify a valid task number after 'unmark'! >:(");
        }
    }

    private static Command parseTodoCommand(String args) throws YoChanException {
        if (args.trim().isEmpty()) {
            throw new YoChanException("Ough! The description of a todo cannot be empty!");
        }
        String description = args.trim();
        return new AddCommand(new Todo(description));
    }

    private static Command parseDeadline(String args) throws YoChanException {
        if (args.trim().isEmpty()) {
            throw new YoChanException("Ough! The description of a deadline cannot be empty!");
        }
        String details = args.trim();
        if (!details.contains("/by")) {
            throw new YoChanException("Ough! Please include /by in your deadline command!");
        }
        String[] parts = details.split(" /by ");
        if (parts.length != 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new YoChanException("Ough! The format should be: deadline <description> /by <YYYY-MM-DD HHMM>");
        }
        return new AddCommand(new Deadline(parts[0].trim(), parts[1].trim()));
    }

    private static Command parseEvent(String args) throws YoChanException {
        if (args.trim().isEmpty()) {
            throw new YoChanException("Ough! The description of an event cannot be empty!");
        }
        String details = args.trim();
        if (!details.contains("/from") || !details.contains("/to")) {
            throw new YoChanException("Ough! Please include both /from and /to in your event command!");
        }
        String[] parts = details.split(" /from ");
        if (parts.length != 2 || parts[0].trim().isEmpty()) {
            throw new YoChanException(
                    "Ough! The format should be: event <description> /from <YYYY-MM-DD HHMM> /to <YYYY-MM-DD HHMM>");
        }
        String[] timeParts = parts[1].split(" /to ");
        if (timeParts.length != 2 || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
            throw new YoChanException("Ough! Please specify both start and end times!");
        }
        return new AddCommand(new Event(parts[0].trim(), timeParts[0].trim(), timeParts[1].trim()));
    }

    private static Command parseDelete(String args) throws YoChanException {
        if (args.trim().isEmpty()) {
            throw new YoChanException("Ough! Please specify which task to delete!");
        }
        try {
            int taskNumber = Integer.parseInt(args.trim());
            return new DeleteCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new YoChanException("Ough! Please provide a valid task number after 'delete'!");
        }
    }

    private static Command parseFind(String args) throws YoChanException {
        if (args.trim().isEmpty()) {
            throw new YoChanException("Ough! The search keyword cannot be empty!");
        }
        return new FindCommand(args.trim());
    }

    private static Command parsePriorityCommand(String args) throws YoChanException {
        String[] tokens = args.trim().split("\\s+");
        if (tokens.length < 2) {
            throw new YoChanException("Ough! The format should be: priority <taskNumber> <priorityLevel>!");
        }
        try {
            int taskNumber = Integer.parseInt(tokens[0]);
            int priorityLevel = Integer.parseInt(tokens[1]);
            return new PriorityCommand(taskNumber, priorityLevel);
        } catch (NumberFormatException e) {
            throw new YoChanException(
                    "Ough! Please provide a valid task number and priority level after 'priority'! >:(");
        }
    }

    /**
     * Parses the task description of the saved task.
     *
     * @return The task represented by its description.
     */
    public static Task parseSavedTask(String taskData) {
        taskData = taskData.trim();
        try {
            int loadedPriority = extractPriority(taskData);
            Task task = null;
            if (taskData.startsWith("[T]")) {
                task = parseTodoTask(taskData);
            } else if (taskData.startsWith("[D]")) {
                task = parseDeadlineTask(taskData);
            } else if (taskData.startsWith("[E]")) {
                task = parseEventTask(taskData);
            }
            if (task != null) {
                task.setPriority(loadedPriority);
                if (isTaskCompleted(taskData)) {
                    task.mark();
                }
            }
            return task;
        } catch (YoChanException e) {
            System.err.println("Ough! Failed to load task: " + taskData + ". Error: " + e.getMessage());
            return null;
        }
    }

    private static int extractPriority(String taskData) {
        Pattern priorityPattern = Pattern.compile(" \\(Priority: (-?\\d+)\\)");
        Matcher matcher = priorityPattern.matcher(taskData);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }

    private static boolean isTaskCompleted(String taskData) {
        return taskData.contains("[X]");
    }

    private static String removePrioritySuffix(String description) {
        return description.replaceAll("\\s*\\(Priority: -?\\d+\\)", "");
    }

    private static Task parseTodoTask(String taskData) throws YoChanException {
        String description = removePrioritySuffix(taskData.substring(6));
        return new Todo(description);
    }

    private static Task parseDeadlineTask(String taskData) throws YoChanException {
        String[] parts = taskData.substring(6).split(" \\(by: ");
        if (parts.length < 2) {
            throw new YoChanException("Malformed deadline task data");
        }
        String description = removePrioritySuffix(parts[0]);
        String by = parts[1].substring(0, parts[1].length() - 1);
        return new Deadline(description, convertSavedDateToInputFormat(by));
    }

    private static Task parseEventTask(String taskData) throws YoChanException {
        String[] parts = taskData.substring(6).split(" \\(from: ");
        if (parts.length < 2) {
            throw new YoChanException("Malformed event task data");
        }
        String description = removePrioritySuffix(parts[0]);
        String[] timeParts = parts[1].split(" to: ");
        if (timeParts.length < 2) {
            throw new YoChanException("Malformed event time data");
        }
        String from = timeParts[0];
        String to = timeParts[1].substring(0, timeParts[1].length() - 1);
        return new Event(description, convertSavedDateToInputFormat(from), convertSavedDateToInputFormat(to));
    }

    private static String convertSavedDateToInputFormat(String savedDate) {
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
