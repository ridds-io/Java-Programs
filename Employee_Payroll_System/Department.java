
import java.util.ArrayList;

public class Department {

    private String departmentName;
    private String departmentCode;
    private ArrayList<Employee> employees;

    public Department(String departmentName, String departmentCode) {
        this.departmentName = departmentName;
        this.departmentCode = departmentCode;
        this.employees = new ArrayList<>();
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void addEmployee(Employee e) {
        employees.add(e);
        System.out.println(e.getName() + " added to " + departmentName);
    }

    public void removeEmployee(int empID) {
        employees.removeIf(e -> e.getEmpID() == empID);
        System.out.println("Employee " + empID + " removed from " + departmentName);
    }

    public double getTotalPayroll() {
        double total = 0;
        for (Employee e : employees) {
            total += e.calcCTC();
        }
        return total;
    }

    public void printPayrollSummary() {
        System.out.println("\n===== Payroll Summary: " + departmentName + " =====");
        for (Employee e : employees) {
            System.out.printf("%-20s | CTC: ₹%.2f%n", e.getName(), e.calcCTC());
        }
        System.out.printf("%-20s | TOTAL: ₹%.2f%n", "Department Payroll", getTotalPayroll());
        System.out.println("=============================================");
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }
}
