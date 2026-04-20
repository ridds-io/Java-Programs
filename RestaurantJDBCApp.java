
import java.sql.*;

public class RestaurantJDBCApp {

    // ===== DATABASE CONFIGURATION =====
    // Modify these values according to your MySQL setup
    private static final String DB_URL = "jdbc:mysql://localhost:3306/restaurant_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";  // Change if you have a password
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    private static Connection connection = null;

    /**
     * Main method - Entry point of the program
     */
    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(100));
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    RESTAURANT MANAGEMENT SYSTEM - JDBC CRUD OPERATIONS                       ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("=".repeat(100) + "\n");

        try {
            // Initialize database connection
            if (connectToDatabase()) {
                // Execute all CRUD operations
                executeAllOperations();
            }

        } finally {
            // Always close the connection
            closeConnection();
        }
    }

    /**
     * Establish connection to MySQL database
     */
    private static boolean connectToDatabase() {
        try {
            // Load MySQL JDBC Driver
            Class.forName(DRIVER_CLASS);
            System.out.println("✓ MySQL JDBC Driver loaded: " + DRIVER_CLASS);

            // Establish connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("✓ Connected to database: " + DB_URL);
            System.out.println("✓ User: " + DB_USER);
            System.out.println("\n" + "=".repeat(100) + "\n");
            return true;

        } catch (ClassNotFoundException e) {
            System.err.println("\n✗ ERROR: MySQL JDBC Driver not found!");
            System.err.println("   Class: " + DRIVER_CLASS);
            System.err.println("   Solution: Add mysql-connector-java-X.X.X.jar to classpath");
            System.err.println("   Download from: https://dev.mysql.com/downloads/connector/j/");
            return false;

        } catch (SQLException e) {
            System.err.println("\n✗ ERROR: Cannot connect to database!");
            System.err.println("   URL: " + DB_URL);
            System.err.println("   User: " + DB_USER);
            System.err.println("   Error: " + e.getMessage());
            System.err.println("\n   Troubleshooting:");
            System.err.println("   1. Ensure MySQL server is running");
            System.err.println("   2. Check database URL and port");
            System.err.println("   3. Verify username and password");
            System.err.println("   4. Create restaurant_db database if not exists");
            return false;
        }
    }

    /**
     * Execute all CRUD operations
     */
    private static void executeAllOperations() {
        operation1_DisplayAllRestaurants();
        operation2_DisplayAllMenuItems();
        operation3_SelectMenuItemsWithPriceLessEqual100();
        operation4_SelectMenuItemsFromCafeJava();
        operation5_UpdateMenuItemsPriceToEqual100();
        operation6_DeleteMenuItemsStartingWithP();

        System.out.println("\n" + "=".repeat(100));
        System.out.println("✓ All CRUD operations completed successfully!");
        System.out.println("=".repeat(100) + "\n");
    }

    /**
     * OPERATION 1: Display all restaurants Query: SELECT * FROM Restaurant
     */
    private static void operation1_DisplayAllRestaurants() {
        System.out.println("\n┌─ OPERATION 1: Display All Restaurants ─────────────────────────────────────────────────────┐");
        System.out.println("│ Purpose: Read all records from Restaurant table                                         │");
        System.out.println("│ Query:   SELECT * FROM Restaurant                                                     │");
        System.out.println("└────────────────────────────────────────────────────────────────────────────────────────────┘\n");

        String query = "SELECT * FROM Restaurant ORDER BY Id";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            // Print table
            System.out.println("┌────┬─────────────────────────────────┬──────────────────────────────────────────┐");
            System.out.println("│ Id │ Restaurant Name                 │ Address                                  │");
            System.out.println("├────┼─────────────────────────────────┼──────────────────────────────────────────┤");

            int count = 0;
            while (rs.next()) {
                System.out.printf("│ %2d │ %-31s │ %-40s │\n",
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getString("Address"));
                count++;
            }

            System.out.println("├────┴─────────────────────────────────┴──────────────────────────────────────────┤");
            System.out.printf("│ Total Records: %d\n", count);
            System.out.println("└────────────────────────────────────────────────────────────────────────────────────┘\n");

        } catch (SQLException e) {
            System.err.println("✗ Error in Operation 1: " + e.getMessage());
        }
    }

    /**
     * OPERATION 2: Display all menu items with restaurant names Query: SELECT *
     * FROM MenuItem
     */
    private static void operation2_DisplayAllMenuItems() {
        System.out.println("\n┌─ OPERATION 2: Display All Menu Items ──────────────────────────────────────────────────────┐");
        System.out.println("│ Purpose: Read all records from MenuItem table with restaurant names                  │");
        System.out.println("│ Query:   SELECT m.*, r.Name FROM MenuItem m JOIN Restaurant r ON m.ResId = r.Id     │");
        System.out.println("└────────────────────────────────────────────────────────────────────────────────────────────┘\n");

        String query = "SELECT m.Id, m.Name, m.Price, r.Name AS RestaurantName "
                + "FROM MenuItem m JOIN Restaurant r ON m.ResId = r.Id ORDER BY m.Id";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            // Print table
            System.out.println("┌────┬──────────────────────┬───────┬──────────────────────┐");
            System.out.println("│ Id │ Item Name            │ Price │ Restaurant Name      │");
            System.out.println("├────┼──────────────────────┼───────┼──────────────────────┤");

            int count = 0;
            while (rs.next()) {
                System.out.printf("│ %2d │ %-20s │ %5.2f │ %-20s │\n",
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getString("RestaurantName"));
                count++;
            }

            System.out.println("├────┴──────────────────────┴───────┴──────────────────────┤");
            System.out.printf("│ Total Records: %d\n", count);
            System.out.println("└──────────────────────────────────────────────────────────┘\n");

        } catch (SQLException e) {
            System.err.println("✗ Error in Operation 2: " + e.getMessage());
        }
    }

    /**
     * OPERATION 3: Select menu items with price <= 100 Query: SELECT * FROM
     * MenuItem WHERE Price <= 100
     */
    private static void operation3_SelectMenuItemsWithPriceLessEqual100() {
        System.out.println("\n┌─ OPERATION 3: Menu Items with Price <= 100 ──────────────────────────────────────────────┐");
        System.out.println("│ Purpose: Read records where Price is less than or equal to 100                      │");
        System.out.println("│ Query:   SELECT * FROM MenuItem WHERE Price <= 100                                 │");
        System.out.println("└────────────────────────────────────────────────────────────────────────────────────────────┘\n");

        String query = "SELECT m.Id, m.Name, m.Price, r.Name AS RestaurantName "
                + "FROM MenuItem m JOIN Restaurant r ON m.ResId = r.Id "
                + "WHERE m.Price <= 100 ORDER BY m.Price";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            // Print table
            System.out.println("┌────┬──────────────────────┬───────┬──────────────────────┐");
            System.out.println("│ Id │ Item Name            │ Price │ Restaurant Name      │");
            System.out.println("├────┼──────────────────────┼───────┼──────────────────────┤");

            int count = 0;
            while (rs.next()) {
                System.out.printf("│ %2d │ %-20s │ %5.2f │ %-20s │\n",
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getString("RestaurantName"));
                count++;
            }

            System.out.println("├────┴──────────────────────┴───────┴──────────────────────┤");
            System.out.printf("│ Total Records Found: %d\n", count);
            System.out.println("└──────────────────────────────────────────────────────────┘\n");

        } catch (SQLException e) {
            System.err.println("✗ Error in Operation 3: " + e.getMessage());
        }
    }

    /**
     * OPERATION 4: Select menu items from "Cafe Java" restaurant Query: SELECT
     * * FROM MenuItem WHERE ResId = (SELECT Id FROM Restaurant WHERE Name =
     * 'Cafe Java')
     */
    private static void operation4_SelectMenuItemsFromCafeJava() {
        System.out.println("\n┌─ OPERATION 4: Menu Items from 'Cafe Java' Restaurant ───────────────────────────────────┐");
        System.out.println("│ Purpose: Read all menu items available in 'Cafe Java' restaurant                   │");
        System.out.println("│ Query:   SELECT * FROM MenuItem m JOIN Restaurant r ON m.ResId = r.Id             │");
        System.out.println("│          WHERE r.Name = 'Cafe Java'                                               │");
        System.out.println("└────────────────────────────────────────────────────────────────────────────────────────────┘\n");

        String query = "SELECT m.Id, m.Name, m.Price, r.Name AS RestaurantName "
                + "FROM MenuItem m JOIN Restaurant r ON m.ResId = r.Id "
                + "WHERE r.Name = 'Cafe Java' ORDER BY m.Id";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            // Print table
            System.out.println("┌────┬──────────────────────┬───────┬──────────────────────┐");
            System.out.println("│ Id │ Item Name            │ Price │ Restaurant Name      │");
            System.out.println("├────┼──────────────────────┼───────┼──────────────────────┤");

            int count = 0;
            while (rs.next()) {
                System.out.printf("│ %2d │ %-20s │ %5.2f │ %-20s │\n",
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getString("RestaurantName"));
                count++;
            }

            System.out.println("├────┴──────────────────────┴───────┴──────────────────────┤");
            System.out.printf("│ Total Records Found: %d\n", count);
            System.out.println("└──────────────────────────────────────────────────────────┘\n");

        } catch (SQLException e) {
            System.err.println("✗ Error in Operation 4: " + e.getMessage());
        }
    }

    /**
     * OPERATION 5: Update menu items with price <= 100 to price = 200 Query:
     * UPDATE MenuItem SET Price = 200 WHERE Price <= 100
     */
    private static void operation5_UpdateMenuItemsPriceToEqual100() {
        System.out.println("\n┌─ OPERATION 5: Update Menu Items (Price <= 100 to 200) ─────────────────────────────────┐");
        System.out.println("│ Purpose: Update records where Price is <= 100 to new Price = 200                  │");
        System.out.println("│ Query:   UPDATE MenuItem SET Price = 200 WHERE Price <= 100                       │");
        System.out.println("└────────────────────────────────────────────────────────────────────────────────────────────┘\n");

        try {
            // Show records BEFORE update
            System.out.println("BEFORE UPDATE - Items with Price <= 100:");
            System.out.println("┌────┬──────────────────────┬───────┬──────────────────────┐");
            System.out.println("│ Id │ Item Name            │ Price │ Restaurant Name      │");
            System.out.println("├────┼──────────────────────┼───────┼──────────────────────┤");

            String selectBefore = "SELECT m.Id, m.Name, m.Price, r.Name AS RestaurantName "
                    + "FROM MenuItem m JOIN Restaurant r ON m.ResId = r.Id "
                    + "WHERE m.Price <= 100 ORDER BY m.Id";

            int beforeCount = 0;
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(selectBefore)) {
                while (rs.next()) {
                    System.out.printf("│ %2d │ %-20s │ %5.2f │ %-20s │\n",
                            rs.getInt("Id"),
                            rs.getString("Name"),
                            rs.getDouble("Price"),
                            rs.getString("RestaurantName"));
                    beforeCount++;
                }
            }
            System.out.println("├────┴──────────────────────┴───────┴──────────────────────┤");
            System.out.printf("│ Records to update: %d\n", beforeCount);
            System.out.println("└──────────────────────────────────────────────────────────┘\n");

            // Perform UPDATE
            String updateQuery = "UPDATE MenuItem SET Price = 200 WHERE Price <= 100";
            try (Statement stmt = connection.createStatement()) {
                int updatedRows = stmt.executeUpdate(updateQuery);
                System.out.println("UPDATE RESULT:");
                System.out.println("┌─────────────────────────────────────────────────┐");
                System.out.printf("│ ✓ %d record(s) updated to Price = 200          │\n", updatedRows);
                System.out.println("└─────────────────────────────────────────────────┘\n");
            }

            // Show records AFTER update
            System.out.println("AFTER UPDATE - Items with Price = 200:");
            System.out.println("┌────┬──────────────────────┬───────┬──────────────────────┐");
            System.out.println("│ Id │ Item Name            │ Price │ Restaurant Name      │");
            System.out.println("├────┼──────────────────────┼───────┼──────────────────────┤");

            String selectAfter = "SELECT m.Id, m.Name, m.Price, r.Name AS RestaurantName "
                    + "FROM MenuItem m JOIN Restaurant r ON m.ResId = r.Id "
                    + "WHERE m.Price = 200 ORDER BY m.Id";

            int afterCount = 0;
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(selectAfter)) {
                while (rs.next()) {
                    System.out.printf("│ %2d │ %-20s │ %5.2f │ %-20s │\n",
                            rs.getInt("Id"),
                            rs.getString("Name"),
                            rs.getDouble("Price"),
                            rs.getString("RestaurantName"));
                    afterCount++;
                }
            }
            System.out.println("├────┴──────────────────────┴───────┴──────────────────────┤");
            System.out.printf("│ Updated records: %d\n", afterCount);
            System.out.println("└──────────────────────────────────────────────────────────┘\n");

        } catch (SQLException e) {
            System.err.println("✗ Error in Operation 5: " + e.getMessage());
        }
    }

    /**
     * OPERATION 6: Delete menu items with name starting with 'P' Query: DELETE
     * FROM MenuItem WHERE Name LIKE 'P%'
     */
    private static void operation6_DeleteMenuItemsStartingWithP() {
        System.out.println("\n┌─ OPERATION 6: Delete Menu Items (Name starts with 'P') ─────────────────────────────────┐");
        System.out.println("│ Purpose: Delete records where Item Name starts with character 'P'                 │");
        System.out.println("│ Query:   DELETE FROM MenuItem WHERE Name LIKE 'P%'                                │");
        System.out.println("└────────────────────────────────────────────────────────────────────────────────────────────┘\n");

        try {
            // Show records BEFORE delete
            System.out.println("BEFORE DELETE - Items starting with 'P':");
            System.out.println("┌────┬──────────────────────┬───────┬──────────────────────┐");
            System.out.println("│ Id │ Item Name            │ Price │ Restaurant Name      │");
            System.out.println("├────┼──────────────────────┼───────┼──────────────────────┤");

            String selectBefore = "SELECT m.Id, m.Name, m.Price, r.Name AS RestaurantName "
                    + "FROM MenuItem m JOIN Restaurant r ON m.ResId = r.Id "
                    + "WHERE m.Name LIKE 'P%' ORDER BY m.Id";

            int beforeCount = 0;
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(selectBefore)) {
                while (rs.next()) {
                    System.out.printf("│ %2d │ %-20s │ %5.2f │ %-20s │\n",
                            rs.getInt("Id"),
                            rs.getString("Name"),
                            rs.getDouble("Price"),
                            rs.getString("RestaurantName"));
                    beforeCount++;
                }
            }
            System.out.println("├────┴──────────────────────┴───────┴──────────────────────┤");
            System.out.printf("│ Records to delete: %d\n", beforeCount);
            System.out.println("└──────────────────────────────────────────────────────────┘\n");

            // Perform DELETE
            String deleteQuery = "DELETE FROM MenuItem WHERE Name LIKE 'P%'";
            try (Statement stmt = connection.createStatement()) {
                int deletedRows = stmt.executeUpdate(deleteQuery);
                System.out.println("DELETE RESULT:");
                System.out.println("┌─────────────────────────────────────────────────┐");
                System.out.printf("│ ✓ %d record(s) deleted successfully            │\n", deletedRows);
                System.out.println("└─────────────────────────────────────────────────┘\n");
            }

            // Show ALL remaining records
            System.out.println("FINAL STATE - All remaining menu items:");
            System.out.println("┌────┬──────────────────────┬───────┬──────────────────────┐");
            System.out.println("│ Id │ Item Name            │ Price │ Restaurant Name      │");
            System.out.println("├────┼──────────────────────┼───────┼──────────────────────┤");

            String selectAfter = "SELECT m.Id, m.Name, m.Price, r.Name AS RestaurantName "
                    + "FROM MenuItem m JOIN Restaurant r ON m.ResId = r.Id "
                    + "ORDER BY m.Id";

            int afterCount = 0;
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(selectAfter)) {
                while (rs.next()) {
                    System.out.printf("│ %2d │ %-20s │ %5.2f │ %-20s │\n",
                            rs.getInt("Id"),
                            rs.getString("Name"),
                            rs.getDouble("Price"),
                            rs.getString("RestaurantName"));
                    afterCount++;
                }
            }
            System.out.println("├────┴──────────────────────┴───────┴──────────────────────┤");
            System.out.printf("│ Total remaining items: %d\n", afterCount);
            System.out.println("└──────────────────────────────────────────────────────────┘\n");

        } catch (SQLException e) {
            System.err.println("✗ Error in Operation 6: " + e.getMessage());
        }
    }

    /**
     * Close database connection
     */
    private static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("✓ Database connection closed");
            } catch (SQLException e) {
                System.err.println("✗ Error closing connection: " + e.getMessage());
            }
        }
    }
}
