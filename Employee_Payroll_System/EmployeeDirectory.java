
import java.util.ArrayList;

public class EmployeeDirectory {

    private ArrayList<Employee> directory;

    public EmployeeDirectory() {
        this.directory = new ArrayList<>();
    }

    public void addEmployee(Employee e) {
        directory.add(e);
    }

    public void removeEmployee(int empID) {
        directory.removeIf(e -> e.getEmpID() == empID);
    }

    // Search by ID
    public Employee findByID(int empID) {
        for (Employee e : directory) {
            if (e.getEmpID() == empID) {
                return e;
            }
        }
        System.out.println("No employee found with ID: " + empID);
        return null;
    }

    // Search by name (case-insensitive, partial match)
    public ArrayList<Employee> findByName(String name) {
        ArrayList<Employee> results = new ArrayList<>();
        for (Employee e : directory) {
            if (e.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(e);
            }
        }
        return results;
    }

    // Search by designation
    public ArrayList<Employee> findByDesignation(String designation) {
        ArrayList<Employee> results = new ArrayList<>();
        for (Employee e : directory) {
            if (e.getDesignation().equalsIgnoreCase(designation)) {
                results.add(e);
            }
        }
        return results;
    }

    // Filter only Taxable employees and print their tax info
    public void printTaxReport() {
        System.out.println("\n========== TAX REPORT ==========");
        for (Employee e : directory) {
            if (e instanceof Taxable t) {
                System.out.printf("%-20s | Tax: ₹%.2f | In-Hand: ₹%.2f%n",
                        e.getName(), t.calcTax(), t.calcInHandSalary());
            }
        }
        System.out.println("=================================");
    }

    public void listAll() {
        System.out.println("\n===== ALL EMPLOYEES =====");
        for (Employee e : directory) {
            System.out.printf("[%d] %-20s | %-15s | CTC: ₹%.2f%n",
                    e.getEmpID(), e.getName(), e.getDesignation(), e.calcCTC());
        }
        System.out.println("=========================");
    }
}
