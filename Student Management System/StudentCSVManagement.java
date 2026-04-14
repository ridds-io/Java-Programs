import java.io.*;
import java.util.*;

class Student {
    String studentId;
    String name;
    String branch;
    int marks1, marks2, marks3, marks4, marks5;
    double percentage;

    public Student(String studentId, String name, String branch, int marks1, int marks2, 
                   int marks3, int marks4, int marks5, double percentage) {
        this.studentId = studentId;
        this.name = name;
        this.branch = branch;
        this.marks1 = marks1;
        this.marks2 = marks2;
        this.marks3 = marks3;
        this.marks4 = marks4;
        this.marks5 = marks5;
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return studentId + "," + name + "," + branch + "," + marks1 + "," + marks2 + "," + 
               marks3 + "," + marks4 + "," + marks5 + "," + String.format("%.1f", percentage);
    }
}

public class StudentCSVManagement {
    private static final String FILE_NAME = "Students.csv";

    // Method to read all students from CSV file
    public static List<Student> readStudents() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header
                }
                String[] parts = line.split(",");
                if (parts.length == 9) {
                    Student student = new Student(
                        parts[0],
                        parts[1],
                        parts[2],
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4]),
                        Integer.parseInt(parts[5]),
                        Integer.parseInt(parts[6]),
                        Integer.parseInt(parts[7]),
                        Double.parseDouble(parts[8])
                    );
                    students.add(student);
                }
            }
            System.out.println("✓ Successfully read " + students.size() + " students from CSV file");
        } catch (IOException e) {
            System.out.println("✗ IOException while reading file: " + e.getMessage());
        }
        return students;
    }

    // Method to write students back to CSV file
    public static void writeStudents(List<Student> students) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            // Write header
            writer.println("studentId,name,branch,marks1,marks2,marks3,marks4,marks5,percentage");
            // Write student data
            for (Student student : students) {
                writer.println(student.toString());
            }
            System.out.println("✓ Successfully wrote " + students.size() + " students to CSV file");
        } catch (IOException e) {
            System.out.println("✗ IOException while writing file: " + e.getMessage());
        }
    }

    // Method to display all students
    public static void displayAllStudents(List<Student> students) {
        System.out.println("\n" + "=".repeat(100));
        System.out.printf("%-10s %-20s %-15s %-10s %-10s %-10s %-10s %-10s %-12s\n",
            "Student ID", "Name", "Branch", "M1", "M2", "M3", "M4", "M5", "Percentage");
        System.out.println("=".repeat(100));
        for (Student student : students) {
            System.out.printf("%-10s %-20s %-15s %-10d %-10d %-10d %-10d %-10d %-12.1f\n",
                student.studentId, student.name, student.branch, student.marks1, 
                student.marks2, student.marks3, student.marks4, student.marks5, student.percentage);
        }
        System.out.println("=".repeat(100));
    }

    // CREATE: Add 3 new students with marks4 and marks5 as zero
    public static void addNewStudents(List<Student> students) {
        System.out.println("\n--- CREATE OPERATION: Adding 3 new students ---");
        students.add(new Student("S003", "Amit Patel", "IT", 88, 85, 90, 0, 0, 0));
        students.add(new Student("S004", "Anjali Verma", "CSE", 92, 89, 91, 0, 0, 0));
        students.add(new Student("S005", "Vikram Singh", "ECE", 76, 80, 78, 0, 0, 0));
        System.out.println("✓ Added 3 new students with marks4 and marks5 as 0");
        displayAllStudents(students);
        writeStudents(students);
    }

    // UPDATE: Update marks4 and marks5 for all students
    public static void updateMarks(List<Student> students) {
        System.out.println("\n--- UPDATE OPERATION: Updating marks4 and marks5 ---");
        students.get(0).marks4 = 89;
        students.get(0).marks5 = 86;
        students.get(1).marks4 = 81;
        students.get(1).marks5 = 83;
        students.get(2).marks4 = 87;
        students.get(2).marks5 = 89;
        students.get(3).marks4 = 91;
        students.get(3).marks5 = 93;
        students.get(4).marks4 = 79;
        students.get(4).marks5 = 82;
        System.out.println("✓ Updated marks4 and marks5 for all students");
        displayAllStudents(students);
        writeStudents(students);
    }

    // Method to calculate percentage
    public static double calculatePercentage(Student student) {
        return (student.marks1 + student.marks2 + student.marks3 + student.marks4 + student.marks5) / 5.0;
    }

    // UPDATE: Calculate and update percentage for all students
    public static void updatePercentages(List<Student> students) {
        System.out.println("\n--- UPDATE OPERATION: Calculating and updating percentages ---");
        for (Student student : students) {
            student.percentage = calculatePercentage(student);
        }
        System.out.println("✓ Calculated and updated percentage for all students");
        displayAllStudents(students);
        writeStudents(students);
    }

    // DELETE: Remove a student from the list
    public static void deleteStudent(List<Student> students) {
        System.out.println("\n--- DELETE OPERATION: Removing a student ---");
        if (!students.isEmpty()) {
            Student removedStudent = students.remove(1); // Remove second student (S002)
            System.out.println("✓ Deleted student: " + removedStudent.name + " (" + removedStudent.studentId + ")");
            displayAllStudents(students);
            writeStudents(students);
        } else {
            System.out.println("✗ No students to delete");
        }
    }

    // READ: Display the final state
    public static void displayFinalState(List<Student> students) {
        System.out.println("\n--- FINAL STATE: All CRUD operations completed ---");
        displayAllStudents(students);
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║         STUDENT CSV FILE - CRUD OPERATIONS WITH EXCEPTION HANDLING  ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");

        // READ: Initial data
        System.out.println("\n--- READ OPERATION: Initial data ---");
        List<Student> students = readStudents();
        displayAllStudents(students);

        // CREATE: Add 3 new students
        addNewStudents(students);

        // UPDATE: Marks
        updateMarks(students);

        // UPDATE: Percentages
        updatePercentages(students);

        // DELETE: Remove a student
        deleteStudent(students);

        // Final state
        displayFinalState(students);

        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    All operations completed successfully!            ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");
    }
}
