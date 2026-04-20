# Student CSV Management - Java Assignment

## Overview

This is a complete Java assignment that demonstrates CRUD (Create, Read, Update, Delete) operations on a CSV file with proper exception handling.

---

## Files Included

### 1. **StudentCSVManagement.java** (Main Program)

The primary program that performs all CRUD operations:

#### Features:

- ✅ **READ**: Read student data from CSV file
- ✅ **CREATE**: Add 3 new students with marks4 and marks5 as 0
- ✅ **UPDATE**: Update marks4 and marks5 for all students
- ✅ **UPDATE**: Calculate percentage for each student (average of 5 marks)
- ✅ **DELETE**: Remove a student from the records
- ✅ **Exception Handling**: Try-catch blocks for all file operations

#### Key Classes:

- `Student`: Data model class for storing student information
- `StudentCSVManagement`: Main class with CRUD methods

#### Key Methods:

```java
public static List<Student> readStudents()           // READ operation
public static void writeStudents(List<Student>)      // WRITE to file
public static void addNewStudents(List<Student>)     // CREATE operation
public static void updateMarks(List<Student>)        // UPDATE marks
public static void updatePercentages(List<Student>)  // UPDATE percentages
public static double calculatePercentage(Student)    // Calculate percentage
public static void deleteStudent(List<Student>)      // DELETE operation
```

---

### 2. **Students.csv** (Data File)

CSV file with the following structure:

**Header Row:**

```
studentId,name,branch,marks1,marks2,marks3,marks4,marks5,percentage
```

**Data Format:**

- studentId: Unique identifier (S001, S002, etc.)
- name: Student name
- branch: Department/Branch (CSE, ECE, IT)
- marks1-5: Marks in 5 subjects
- percentage: Average percentage (calculated)

**Final Records (after CRUD operations):**

```
S001,Rajesh Kumar,CSE,85,90,88,89,86,87.6
S003,Amit Patel,IT,88,85,90,87,89,87.8
S004,Anjali Verma,CSE,92,89,91,91,93,91.2
S005,Vikram Singh,ECE,76,80,78,79,82,79.0
```

---

### 3. **ExceptionHandlingDemo.java** (Exception Examples)

Demonstrates various IOException handling scenarios:

#### Test Cases:

1. **FileNotFoundException**: Reading from non-existent file
2. **Successful Write**: Writing to a new file
3. **Data Validation**: Reading and validating file format

#### Key Features:

- Catches FileNotFoundException (subclass of IOException)
- Displays error type and message
- Shows full stack trace
- Graceful error handling without program termination

---

## How to Run

### Step 1: Compile

```bash
javac StudentCSVManagement.java
javac ExceptionHandlingDemo.java
```

### Step 2: Run Main Program

```bash
java StudentCSVManagement
```

### Step 3: Run Exception Demo

```bash
java ExceptionHandlingDemo
```

---

## Program Output Summary

The program demonstrates the following output:

### Initial State (READ):

- Reads 2 initial students from Students.csv
- Displays them in a formatted table

### CREATE Operation:

- Adds 3 new students (S003, S004, S005)
- Sets marks4 and marks5 as 0 initially
- Writes updated file

### UPDATE Operations:

1. **Update Marks**: Fills in marks4 and marks5 for all students
2. **Update Percentage**: Calculates percentage as average of all 5 marks
3. Writes file after each update

### DELETE Operation:

- Removes student S002 (Priya Singh)
- Writes the final file with 4 students

### Final State:

- Displays all students with updated information
- Shows the percentage calculation: (M1+M2+M3+M4+M5)/5

---

## Exception Handling Implementation

### IOException Handling Pattern:

```java
try {
    // File operations
    BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
    // ... read data ...
    reader.close();
} catch (IOException e) {
    System.out.println("✗ IOException while reading file: " + e.getMessage());
}
```

### Exception Types Caught:

1. **FileNotFoundException**: When file doesn't exist
2. **IOException**: General I/O errors
3. **NumberFormatException**: Invalid number format (caught implicitly in data parsing)

### Error Output Format:

```
✗ IOException CAUGHT!
  Error Type: FileNotFoundException
  Error Message: Students.csv (No such file or directory)
  Stack Trace: [Full stack trace displayed]
✓ Exception handled gracefully - Program continues execution
```

---

## Percentage Calculation

The percentage is calculated as the average of all 5 marks:

```java
public static double calculatePercentage(Student student) {
    return (student.marks1 + student.marks2 + student.marks3 +
            student.marks4 + student.marks5) / 5.0;
}
```

**Example:**

- Student: Rajesh Kumar
- Marks: 85, 90, 88, 89, 86
- Percentage: (85+90+88+89+86)/5 = 438/5 = 87.6%

---

## Student Data Tracking

### Initial Data (2 students):

| ID   | Name         | Branch | M1  | M2  | M3  | M4  | M5  | %    |
| ---- | ------------ | ------ | --- | --- | --- | --- | --- | ---- |
| S001 | Rajesh Kumar | CSE    | 85  | 90  | 88  | 92  | 87  | 88.4 |
| S002 | Priya Singh  | ECE    | 78  | 82  | 80  | 75  | 79  | 78.8 |

### After CREATE (5 students):

- S003 added: Amit Patel, IT, marks4=0, marks5=0
- S004 added: Anjali Verma, CSE, marks4=0, marks5=0
- S005 added: Vikram Singh, ECE, marks4=0, marks5=0

### After UPDATE Marks:

All students now have valid marks for M4 and M5

### After UPDATE Percentage:

All percentages recalculated based on all 5 marks

### After DELETE:

S002 removed - Final count: 4 students

---

## Testing Scenarios

The ExceptionHandlingDemo.java file tests:

1. ✓ Reading from non-existent file (FileNotFoundException)
2. ✓ Writing to file (IOException handling)
3. ✓ Reading invalid format (Data validation)
4. ✓ Stack trace display
5. ✓ Error message formatting

---

## Best Practices Implemented

1. **Try-catch-finally**: Used for resource management
2. **Try-with-resources**: Automatic resource closure
3. **Specific Exception Catching**: FileNotFoundException before IOException
4. **Error Logging**: Clear error messages and stack traces
5. **Input Validation**: Checking data format before processing
6. **Separation of Concerns**: Different methods for CRUD operations
7. **Data Encapsulation**: Student class with getters/setters pattern

---


## Notes

- The percentage is displayed with 1 decimal place (e.g., 87.6)
- Student IDs are preserved throughout all operations
- CSV format is maintained with proper comma separation
- All operations are logged to console with visual indicators (✓, ✗, ⚠)

