import java.util.Scanner;

public class YoChan {
    public static void main(String[] args) {

        String[] tasks = new String[100];
        int taskCount = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ough... What do you want?");

        // Infinite loop until user inputs "bye"
        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("bye")) {
                System.out.println("-*-*-*-*-");
                System.out.println("Bye bye!");
                System.out.println("-*-*-*-*-");
                break;

            // Lists all tasks inputted by user
            } else if (userInput.equals("list")) {
                System.out.println("-*-*-*-*-");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println("-*-*-*-*-");

            // Adds inputted task to list of tasks
            } else {
                tasks[taskCount] = userInput;
                taskCount++;
                System.out.println("-*-*-*-*-");
                System.out.println("added: " + userInput);
                System.out.println("-*-*-*-*-");
            }
        }

        scanner.close();
    }
}
