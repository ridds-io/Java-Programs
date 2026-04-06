
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        // --- Create Employees ---
        FullTimeEmployee hrEmp = new FullTimeEmployee(
                "Priya Sharma", 101, 123456789, 987654321,
                new Date(), "HR", 50000, 8000
        );

        SalesEmployee salesEmp = new SalesEmployee(
                "Rohan Das", 102, 222333444, 555666777,
                new Date(), "Sales Executive",
                40000, 200000, 0.05, 230000 // hit target
        );

        Manager manager = new Manager(
                "Anjali Verma", 103, 999888777, 112233445,
                new Date(), "Manager",
                80000, 0, 15000, 5000, 3000
        );

        ContractEmployee contractor = new ContractEmployee(
                "Rahul Mehta", 104, 111222333, 444555666,
                new Date(), "Developer",
                160, 500
        );

        Intern intern = new Intern(
                "Sneha Kulkarni", 105, 333444555, 666777888,
                new Date(), "Intern",
                15000, 5000, 6, "PICT Pune"
        );
        intern.offerPPO();

        // --- Department ---
        Department techDept = new Department("Technology", "TECH-01");
        techDept.addEmployee(manager);
        techDept.addEmployee(contractor);
        techDept.addEmployee(intern);
        techDept.printPayrollSummary();

        Department salesDept = new Department("Sales", "SALES-01");
        salesDept.addEmployee(hrEmp);
        salesDept.addEmployee(salesEmp);
        salesDept.printPayrollSummary();

        // --- Employee Directory ---
        EmployeeDirectory directory = new EmployeeDirectory();
        directory.addEmployee(hrEmp);
        directory.addEmployee(salesEmp);
        directory.addEmployee(manager);
        directory.addEmployee(contractor);
        directory.addEmployee(intern);

        directory.listAll();
        directory.printTaxReport(); // only SalesEmployee is Taxable here

        // Search demos
        System.out.println("\nSearch by name 'an':");
        directory.findByName("an").forEach(e
                -> System.out.println("  -> " + e.getName()));

        System.out.println("\nSearch by ID 103:");
        Employee found = directory.findByID(103);
        if (found != null) {
            found.displayEmployeeDetails();
        }

        // --- PaySlips ---
        new PaySlip(salesEmp).generate();    // Taxable — shows tax
        new PaySlip(contractor).generate();  // Not taxable
        new PaySlip(intern).generate();      // Not taxable
    }
}
