import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class YoChan {
    private static int taskCount = 0;

    public static void main(String[] args) {
        Task[] tasks = new Task[100];
        loadTasksFromFile(tasks);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ough... What do you want?");

        // Infinite event loop, until user exits by inputting "bye"
        while (true) {
            String userInput = scanner.nextLine();

            // Exit program
            if (userInput.equals("bye")) {
                System.out.println("-*-*-*-*-");
                System.out.println("Bye bye!");
                System.out.println("-*-*-*-*-");
                break;

            // List all tasks in the list of tasks
            } else if (userInput.equals("list")) {
                System.out.println("-*-*-*-*-");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println("-*-*-*-*-");

            // Mark the specified task as completed
            } else if (userInput.startsWith("mark")) {
                try {
                    int taskNumber = Integer.parseInt(userInput.split(" ")[1]);
                    if (taskNumber > 0 && taskNumber <= taskCount) {
                        tasks[taskNumber - 1].mark();
                        writeTasksToFile(tasks, taskCount);
                        System.out.println("-*-*-*-*-");
                        System.out.println("Ough! I've marked this task as done:");
                        System.out.println(tasks[taskNumber - 1]);
                        System.out.println("-*-*-*-*-");
                    } else {
                        System.out.println("-*-*-*-*-");
                        System.out.println("Invalid task number! >:(");
                        System.out.println("-*-*-*-*-");
                    }
                // Ask user to try again
                } catch (Exception e) {
                    System.out.println("-*-*-*-*-");
                    System.out.println("Specify a valid task number after 'mark'! >:(");
                    System.out.println("-*-*-*-*-");
                }

            // Unmark specified task as completed
            } else if (userInput.startsWith("unmark")) {
                try {
                    int taskNumber = Integer.parseInt(userInput.split(" ")[1]);
                    if (taskNumber > 0 && taskNumber <= taskCount) {
                        tasks[taskNumber - 1].unmark();
                        writeTasksToFile(tasks, taskCount);
                        System.out.println("-*-*-*-*-");
                        System.out.println("Ough... I've marked this task as not done yet:");
                        System.out.println(tasks[taskNumber - 1]);
                        System.out.println("-*-*-*-*-");
                    } else {
                        System.out.println("-*-*-*-*-");
                        System.out.println("Invalid task number! >:(");
                        System.out.println("-*-*-*-*-");
                    }
                } catch (Exception e) {
                    System.out.println("-*-*-*-*-");
                    System.out.println("Specify a valid task number after 'unmark'! >:(");
                    System.out.println("-*-*-*-*-");
                }

            // Add the todo task type into the list of Tasks
            } else if (userInput.startsWith("todo")) {
                try {
                    if (userInput.equals("todo")) {
                        throw new YoChanException("Ough! The description of a todo cannot be empty!");
                    }
                    String description = userInput.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new YoChanException("Ough! The description of a todo cannot be empty!");
                    }
                    tasks[taskCount] = new Todo(description);
                    taskCount++;
                    writeTasksToFile(tasks, taskCount);
                    System.out.println("-*-*-*-*-");
                    System.out.println("Oughkay, I've added this task:");
                    System.out.println(tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    System.out.println("-*-*-*-*-");
                } catch (YoChanException e) {
                    System.out.println("-*-*-*-*-");
                    System.out.println(e.getMessage());
                    System.out.println("-*-*-*-*-");
                }

            // Add the deadline task type into the list of Tasks
            } else if (userInput.startsWith("deadline")) {
                try {
                    String details = userInput.substring(9).trim();
                    if (details.isEmpty()) {
                        throw new YoChanException("Ough! The description of a deadline cannot be empty!");
                    }
                    if (!details.contains("/by")) {
                        throw new YoChanException("Ough! Please include /by in your deadline command!");
                    }
                    String[] parts = details.split(" /by ");
                    if (parts.length != 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new YoChanException("Ough! The format should be: deadline <description> /by <deadline>");
                    }
                    tasks[taskCount] = new Deadline(parts[0].trim(), parts[1].trim());
                    taskCount++;
                    writeTasksToFile(tasks, taskCount);
                    System.out.println("-*-*-*-*-");
                    System.out.println("Oughh. I've added this task:");
                    System.out.println(tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    System.out.println("-*-*-*-*-");
                } catch (YoChanException e) {
                    System.out.println("-*-*-*-*-");
                    System.out.println(e.getMessage());
                    System.out.println("-*-*-*-*-");
                }

            // Add the event task type into the list of Tasks
            } else if (userInput.startsWith("event")) {
                try {
                    String details = userInput.substring(6).trim();
                    if (details.isEmpty()) {
                        throw new YoChanException("Ough! The description of an event cannot be empty!");
                    }
                    if (!details.contains("/from") || !details.contains("/to")) {
                        throw new YoChanException("Ough! Please include both /from and /to in your event command!");
                    }
                    String[] parts = details.split(" /from ");
                    if (parts.length != 2 || parts[0].trim().isEmpty()) {
                        throw new YoChanException("Ough! The format should be: event <description> /from <start> /to <end>");
                    }
                    String[] timeParts = parts[1].split(" /to ");
                    if (timeParts.length != 2 || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
                        throw new YoChanException("Ough! Please specify both start and end times!");
                    }

                    tasks[taskCount] = new Event(parts[0].trim(), timeParts[0].trim(), timeParts[1].trim());
                    taskCount++;
                    writeTasksToFile(tasks, taskCount);
                    System.out.println("-*-*-*-*-");
                    System.out.println("Oughkay. I've added this task:");
                    System.out.println(tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    System.out.println("-*-*-*-*-");
                } catch (YoChanException e) {
                    System.out.println("-*-*-*-*-");
                    System.out.println(e.getMessage());
                    System.out.println("-*-*-*-*-");
                }

            // Delete the specified task from the list
            } else if (userInput.startsWith("delete")) {
                try {
                    if (userInput.equals("delete")) {
                        throw new YoChanException("Ough! Please specify which task to delete!");
                    }
                    int taskNumber = Integer.parseInt(userInput.split(" ")[1]);
                    if (taskNumber > 0 && taskNumber <= taskCount) {
                        Task deletedTask = tasks[taskNumber - 1];
                        // Shift remaining tasks to fill the gap
                        for (int i = taskNumber - 1; i < taskCount - 1; i++) {
                            tasks[i] = tasks[i + 1];
                        }
                        taskCount--;
                        writeTasksToFile(tasks, taskCount);
                        System.out.println("-*-*-*-*-");
                        System.out.println("Ough! I've removed this task:");
                        System.out.println(deletedTask);
                        System.out.println("Now you have " + taskCount + " tasks in the list.");
                        System.out.println("-*-*-*-*-");
                    } else {
                        throw new YoChanException("Ough! Please provide a valid task number to delete!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("-*-*-*-*-");
                    System.out.println("Ough! Please provide a valid task number after 'delete'!");
                    System.out.println("-*-*-*-*-");
                } catch (YoChanException e) {
                    System.out.println("-*-*-*-*-");
                    System.out.println(e.getMessage());
                    System.out.println("-*-*-*-*-");
                }
            } else {
                System.out.println("-*-*-*-*-");
                System.out.println("Ough!! Unknown command!");
                System.out.println("-*-*-*-*-");
            }
        }

        scanner.close();
    }

    private static void writeTasksToFile(Task[] tasks, int taskCount) {
        File dir = new File("data");
        dir.mkdirs();
        Path filePath = Paths.get("data", "YoChan.txt");

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < taskCount; i++) {
            str.append((i + 1) + ". " + tasks[i] + System.lineSeparator());
        }
        try {
            Files.writeString(filePath, str, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println("Ough.. what de hel!");
        }
    }

    private static void loadTasksFromFile(Task[] tasks) {
        Path filePath = Paths.get("data", "YoChan.txt");
        if (!Files.exists(filePath)) {
            return;
        }

        try {
            String content = Files.readString(filePath);
            String[] lines = content.split(System.lineSeparator());
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                // Remove the task number
                String taskData = line.substring(line.indexOf(". ") + 2);
                
                if (taskData.startsWith("[T]")) {
                    // Parse Todo
                    String description = taskData.substring(6);
                    tasks[taskCount] = new Todo(description);
                } else if (taskData.startsWith("[D]")) {
                    // Parse Deadline
                    String[] parts = taskData.substring(6).split(" \\(by: ");
                    String description = parts[0];
                    String by = parts[1].substring(0, parts[1].length() - 1);
                    tasks[taskCount] = new Deadline(description, by);
                } else if (taskData.startsWith("[E]")) {
                    // Parse Event
                    String[] parts = taskData.substring(6).split(" \\(from: ");
                    String description = parts[0];
                    String[] timeParts = parts[1].split(" to: ");
                    String from = timeParts[0];
                    String to = timeParts[1].substring(0, timeParts[1].length() - 1);
                    tasks[taskCount] = new Event(description, from, to);
                }

                // Check if task was marked as done
                if (taskData.contains("[X]")) {
                    tasks[taskCount].mark();
                }
                
                taskCount++;
            }
        } catch (IOException e) {
            System.out.println("Ough... Failed to load tasks!");
        }
    }
}
