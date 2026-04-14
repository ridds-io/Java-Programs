import java.io.*;

public class ExceptionHandlingDemo {
    
    public static void main(String[] args) {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║           IOException HANDLING - DEMONSTRATION                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝\n");

        // Test 1: Reading from non-existent file
        System.out.println("--- TEST 1: Attempting to read from non-existent file ---");
        readNonExistentFile();

        // Test 2: Writing to a read-only directory (simulated)
        System.out.println("\n--- TEST 2: Attempting to write to a file ---");
        writeToFile();

        // Test 3: Reading with invalid data format
        System.out.println("\n--- TEST 3: Attempting to read file with invalid format ---");
        readInvalidFormatFile();

        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║              All exception tests completed successfully             ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝\n");
    }

    // Test reading from non-existent file
    public static void readNonExistentFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("NonExistent.csv"));
            String line = reader.readLine();
            reader.close();
        } catch (IOException e) {
            System.out.println("✗ IOException CAUGHT!");
            System.out.println("  Error Type: " + e.getClass().getSimpleName());
            System.out.println("  Error Message: " + e.getMessage());
            System.out.println("  Stack Trace:");
            e.printStackTrace(System.out);
            System.out.println("✓ Exception handled gracefully - Program continues execution");
        }
    }

    // Test writing to file
    public static void writeToFile() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("TestOutput.csv"));
            writer.println("Test,Data,Here");
            writer.close();
            System.out.println("✓ File written successfully: TestOutput.csv");
        } catch (IOException e) {
            System.out.println("✗ IOException CAUGHT!");
            System.out.println("  Error Type: " + e.getClass().getSimpleName());
            System.out.println("  Error Message: " + e.getMessage());
        }
    }

    // Test reading file with invalid format
    public static void readInvalidFormatFile() {
        // Create a file with invalid format first
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("InvalidFormat.csv"));
            writer.println("This is invalid data");
            writer.println("Not proper CSV format at all!");
            writer.close();
        } catch (IOException e) {
            System.out.println("✗ IOException while creating test file: " + e.getMessage());
            return;
        }

        // Now try to parse it as proper CSV
        try (BufferedReader reader = new BufferedReader(new FileReader("InvalidFormat.csv"))) {
            String line;
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                String[] parts = line.split(",");
                if (parts.length != 9) {
                    System.out.println("⚠ Warning: Line " + lineNum + " has invalid format (expected 9 columns, got " + parts.length + ")");
                    System.out.println("  Content: " + line);
                }
            }
            System.out.println("✓ File read attempt completed - Invalid data detected and reported");
        } catch (IOException e) {
            System.out.println("✗ IOException CAUGHT!");
            System.out.println("  Error Type: " + e.getClass().getSimpleName());
            System.out.println("  Error Message: " + e.getMessage());
        }
    }
}
