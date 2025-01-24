import java.util.Scanner;

public class YoChan {
    public static void main(String[] args) {
        Task[] tasks = new Task[100];
        int taskCount = 0;

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
                String description = userInput.substring(5);
                tasks[taskCount] = new Todo(description);
                taskCount++;
                System.out.println("-*-*-*-*-");
                System.out.println("Oughkay, I've added this task:");
                System.out.println(tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println("-*-*-*-*-");

            // Add the deadline task type into the list of Tasks
            } else if (userInput.startsWith("deadline")) {
                String[] parts = userInput.substring(9).split(" /by ");
                tasks[taskCount] = new Deadline(parts[0], parts[1]);
                taskCount++;
                System.out.println("-*-*-*-*-");
                System.out.println("Oughh. I've added this task:");
                System.out.println(tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println("-*-*-*-*-");

            // Add the event task type into the list of Tasks
            } else if (userInput.startsWith("event")) {
                try {
                    // Extract the description, from, and to parts
                    String[] parts = userInput.substring(6).split(" /from ");
                    String description = parts[0];
                    String[] timeParts = parts[1].split(" /to ");
                    String from = timeParts[0];
                    String to = timeParts[1];

                    tasks[taskCount] = new Event(description, from, to);
                    taskCount++;

                    System.out.println("-*-*-*-*-");
                    System.out.println("Got it. I've added this task:");
                    System.out.println(tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    System.out.println("-*-*-*-*-");
                } catch (Exception e) {
                    System.out.println("-*-*-*-*-");
                    System.out.println("Invalid event format! Use: event <description> /from <start> /to <end>");
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
}
