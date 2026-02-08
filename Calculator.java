
import java.util.Scanner;

public class Calculator {

    public int ch, num1, num2;
    public int sum, difference, product, modulus;
    public double quotient;

    public int addNums(int a, int b) {
        return a + b;
    }

    public int subtractNums(int a, int b) {
        return a - b;
    }

    public long multiplyNums(int a, int b) {
        return (long) a * b;
    }

    public double divideNums(int a, int b) {
        if (b != 0) {
            return (double) a / b;
        } else {
            System.out.println("Error: Cannot divide by zero.");
            return Double.NaN;
        }
    }

    public int calcMod(int a, int b) {
        return a % b;
    }

    public static void main(String[] args) {
        Calculator c = new Calculator(); // default constructor, created by the Java compiler

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter first number: ");
        c.num1 = scan.nextInt();
        System.out.print("Enter second number: ");
        c.num2 = scan.nextInt();

        do {
            System.out.println("Select Operation: ");
            System.out.println("0. Exit");
            System.out.println("1. Addition");
            System.out.println("2. Subtraction");
            System.out.println("3. Multiplication");
            System.out.println("4. Division");
            System.out.println("5. Modulus");

            System.out.print("Enter choice (0-5): ");
            try {
                c.ch = scan.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 0 and 5.");
                scan.next(); // consume the invalid token
                continue;
            }

            switch (c.ch) {
                case 0:
                    System.out.println("Exiting...");
                    break;
                case 1:
                    System.out.println("Sum: " + c.addNums(c.num1, c.num2));
                    break;
                case 2:
                    System.out.println("Difference: " + c.subtractNums(c.num1, c.num2));
                    break;
                case 3:
                    System.out.println("Product: " + c.multiplyNums(c.num1, c.num2));
                    break;
                case 4:
                    System.out.println("Quotient: " + c.divideNums(c.num1, c.num2));
                    break;
                case 5:
                    System.out.println("Modulus: " + c.calcMod(c.num1, c.num2));
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (c.ch != 0);
        scan.close();
    }
}
