import java.util.Scanner;

public class YoChan {
    public static void main(String[] args) {
        Task[] tasks = new Task[100];
        int taskCount = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ough... What do you want?");

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals("bye")) {
                System.out.println("-*-*-*-*-");
                System.out.println("Bye bye!");
                System.out.println("-*-*-*-*-");
                break;

            } else if (userInput.equals("list")) {
                System.out.println("-*-*-*-*-");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println("-*-*-*-*-");

            } else if (userInput.startsWith("mark")) {
                try {
                    int taskNumber = Integer.parseInt(userInput.split(" ")[1]);
                    if (taskNumber > 0 && taskNumber <= taskCount) {
                        tasks[taskNumber - 1].markAsDone();
                        System.out.println("-*-*-*-*-");
                        System.out.println("Ough! I've marked this task as done:");
                        System.out.println(tasks[taskNumber - 1]);
                        System.out.println("-*-*-*-*-");
                    } else {
                        System.out.println("-*-*-*-*-");
                        System.out.println("Invalid task number!");
                        System.out.println("-*-*-*-*-");
                    }
                } catch (Exception e) {
                    System.out.println("-*-*-*-*-");
                    System.out.println("Please specify a valid task number after 'mark'!");
                    System.out.println("-*-*-*-*-");
                }

            } else if (userInput.startsWith("unmark")) {
                try {
                    int taskNumber = Integer.parseInt(userInput.split(" ")[1]);
                    if (taskNumber > 0 && taskNumber <= taskCount) {
                        tasks[taskNumber - 1].unmarkAsDone();
                        System.out.println("-*-*-*-*-");
                        System.out.println("Ough... I've marked this task as not done yet:");
                        System.out.println(tasks[taskNumber - 1]);
                        System.out.println("-*-*-*-*-");
                    } else {
                        System.out.println("-*-*-*-*-");
                        System.out.println("Invalid task number!");
                        System.out.println("-*-*-*-*-");
                    }
                } catch (Exception e) {
                    System.out.println("-*-*-*-*-");
                    System.out.println("Please specify a valid task number after 'unmark'!");
                    System.out.println("-*-*-*-*-");
                }

            } else {
                tasks[taskCount] = new Task(userInput);
                taskCount++;
                System.out.println("-*-*-*-*-");
                System.out.println("added: " + userInput);
                System.out.println("-*-*-*-*-");
            }
        }

        scanner.close();
    }
}
