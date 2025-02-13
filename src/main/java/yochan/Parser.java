package yochan;

import yochan.command.AddCommand;
import yochan.command.Command;
import yochan.command.DeleteCommand;
import yochan.command.ExitCommand;
import yochan.command.FindCommand;
import yochan.command.ListCommand;
import yochan.command.MarkCommand;
import yochan.command.UnmarkCommand;
import yochan.task.Deadline;
import yochan.task.Event;
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
        if (userInput.equals("bye")) {
            return new ExitCommand();
        } else if (userInput.equals("list")) {
            return new ListCommand();
        } else if (userInput.startsWith("mark")) {
            try {
                // Uses the parameter immediately after the mark command as the index to be marked.
                int taskNumber = Integer.parseInt(userInput.split(" ")[1]);
                return new MarkCommand(taskNumber);
            } catch (Exception e) {
                throw new YoChanException("Specify a valid task number after 'mark'! >:(");
            }
        } else if (userInput.startsWith("unmark")) {
            try {
                // Uses the parameter immediately after the unmark command as the index to be unmarked.
                int taskNumber = Integer.parseInt(userInput.split(" ")[1]);
                return new UnmarkCommand(taskNumber);
            } catch (Exception e) {
                throw new YoChanException("Specify a valid task number after 'unmark'! >:(");
            }
        } else if (userInput.startsWith("todo")) {
            // Parse and return the todo command.
            if (userInput.equals("todo") || userInput.substring(5).trim().isEmpty()) {
                throw new YoChanException("Ough! The description of a todo cannot be empty!");
            }
            return new AddCommand(new Todo(userInput.substring(5).trim()));
        } else if (userInput.startsWith("deadline")) {
            return parseDeadline(userInput);
        } else if (userInput.startsWith("event")) {
            return parseEvent(userInput);
        } else if (userInput.startsWith("delete")) {
            return parseDelete(userInput);
        } else if (userInput.startsWith("find")) {
            return parseFind(userInput);
        } else {
            throw new YoChanException("Ough!! Unknown command!");
        }
    }

    private static Command parseDeadline(String userInput) throws YoChanException {
        // Throws an exception if the deadline parameters are empty.
        if (userInput.equals("deadline") || userInput.substring(8).trim().isEmpty()) {
            throw new YoChanException("Ough! The description of a deadline cannot be empty!");
        }
        String details = userInput.substring(9).trim();
        // Throws an exception if the input does not indicate deadline date.
        if (!details.contains("/by")) {
            throw new YoChanException("Ough! Please include /by in your deadline command!");
        }
        String[] parts = details.split(" /by ");
        // Throws an exception if the deadline date format is wrong.
        if (parts.length != 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new YoChanException("Ough! The format should be: deadline <description> /by <YYYY-MM-DD HHMM>");
        }
        return new AddCommand(new Deadline(parts[0].trim(), parts[1].trim()));
    }

    private static Command parseEvent(String userInput) throws YoChanException {
        // Throws an exception if the event parameters are empty.
        if (userInput.equals("event") || userInput.substring(5).trim().isEmpty()) {
            throw new YoChanException("Ough! The description of an event cannot be empty!");
        }
        String details = userInput.substring(6).trim();
        // Throws an exception if the input does not indicate event start and end date.
        if (!details.contains("/from") || !details.contains("/to")) {
            throw new YoChanException("Ough! Please include both /from and /to in your event command!");
        }
        String[] parts = details.split(" /from ");
        // Throws an exception if the event start date format is wrong.
        if (parts.length != 2 || parts[0].trim().isEmpty()) {
            throw new YoChanException(
                    "Ough! The format should be: event <description> /from <YYYY-MM-DD HHMM> /to <YYYY-MM-DD HHMM>");
        }
        String[] timeParts = parts[1].split(" /to ");
        // Throws an exception if the event start or end date is missing.
        if (timeParts.length != 2 || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
            throw new YoChanException("Ough! Please specify both start and end times!");
        }
        return new AddCommand(new Event(parts[0].trim(), timeParts[0].trim(), timeParts[1].trim()));
    }

    private static Command parseDelete(String userInput) throws YoChanException {
        // Throws an exception if the delete parameters are missing.
        if (userInput.equals("delete")) {
            throw new YoChanException("Ough! Please specify which task to delete!");
        }
        try {
            // Uses the first parameter after the delete as the task index to be deleted.
            int taskNumber = Integer.parseInt(userInput.split(" ")[1]);
            return new DeleteCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new YoChanException("Ough! Please provide a valid task number after 'delete'!");
        }
    }

    private static Command parseFind(String userInput) throws YoChanException {
        // Currently a different format from the other commands, will change later.
        if (userInput.equals("find") || userInput.substring(5).trim().isEmpty()) {
            throw new YoChanException("Ough! The search keyword cannot be empty!");
        }
        // Uses everything after the find statement as a search term.
        return new FindCommand(userInput.substring(5).trim());
    }
}
