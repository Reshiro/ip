/**
 * Deals with processing user inputs and saved tasks.
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
                int taskNumber = Integer.parseInt(userInput.split(" ")[1]);
                return new MarkCommand(taskNumber);
            } catch (Exception e) {
                throw new YoChanException("Specify a valid task number after 'mark'! >:(");
            }
        } else if (userInput.startsWith("unmark")) {
            try {
                int taskNumber = Integer.parseInt(userInput.split(" ")[1]);
                return new UnmarkCommand(taskNumber);
            } catch (Exception e) {
                throw new YoChanException("Specify a valid task number after 'unmark'! >:(");
            }
        } else if (userInput.startsWith("todo")) {
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
        } else {
            throw new YoChanException("Ough!! Unknown command!");
        }
    }

    private static Command parseDeadline(String userInput) throws YoChanException {
        if (userInput.equals("deadline") || userInput.substring(8).trim().isEmpty()) {
            throw new YoChanException("Ough! The description of a deadline cannot be empty!");
        }
        String details = userInput.substring(9).trim();
        if (!details.contains("/by")) {
            throw new YoChanException("Ough! Please include /by in your deadline command!");
        }
        String[] parts = details.split(" /by ");
        if (parts.length != 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new YoChanException("Ough! The format should be: deadline <description> /by <YYYY-MM-DD HHMM>");
        }
        return new AddCommand(new Deadline(parts[0].trim(), parts[1].trim()));
    }

    private static Command parseEvent(String userInput) throws YoChanException {
        if (userInput.equals("event") || userInput.substring(5).trim().isEmpty()) {
            throw new YoChanException("Ough! The description of an event cannot be empty!");
        }
        String details = userInput.substring(6).trim();
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

    private static Command parseDelete(String userInput) throws YoChanException {
        if (userInput.equals("delete")) {
            throw new YoChanException("Ough! Please specify which task to delete!");
        }
        try {
            int taskNumber = Integer.parseInt(userInput.split(" ")[1]);
            return new DeleteCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new YoChanException("Ough! Please provide a valid task number after 'delete'!");
        }
    }
}
