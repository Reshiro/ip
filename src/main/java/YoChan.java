import java.util.Scanner;

public class YoChan {
    public static void main(String[] args) {
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
            }
            // Echoes user input
            System.out.println("-*-*-*-*-");
            System.out.println(userInput);
            System.out.println("-*-*-*-*-");
        }

        scanner.close();
    }
}
