package numbers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static final BigInteger EXIT_CODE = BigInteger.ZERO;

    public static void main(String[] args) {
        //read the number
        System.out.println("Welcome to Amazing Numbers!");

        showMenu();
        System.out.print("\nEnter a request: ");
        String numberText = scanner.nextLine();
        while (!numberText.trim().equals("0")) {
            String[] numbers = numberText.split(" ");
            try {
                NumberService service = null;
                switch (numbers.length) {
                    case 1:
                        service = new NumberService(numbers[0]);
                        break;
                    case 2:
                        service = new NumberService(numbers[0], numbers[1]);
                        break;
                    default:
                        service = new NumberService(numbers[0], numbers[1], Arrays.copyOfRange(numbers, 2, numbers.length));
                        break;
                }
                service.start();
            } catch (ParameterException ex) {
                System.out.println(ex.getMessage());
            } catch (IllegalArgumentException ex) {
                System.out.println("The first parameter should be a natural number or zero.");
            }

            if (numberText.isBlank()) {
                showMenu();
                System.out.println();
            }
            System.out.print("\nEnter a request: ");
            numberText = scanner.nextLine();
        }
        System.out.println("\nGoodbye!");
    }

    private static void showMenu() {
        System.out.println("Supported requests:");
        System.out.println("- enter a natural number to know its properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println("  * the first parameter represents a starting number;");
        System.out.println("  * the second parameter shows how many consecutive numbers are to be processed;");
        System.out.println("- two natural numbers and a properties to search for;");
        System.out.println("- a property preceded by minus must not be present in numbers;");
        System.out.println("- separate the parameters with one space;");
        System.out.println("- enter 0 to exit.");
    }


}
