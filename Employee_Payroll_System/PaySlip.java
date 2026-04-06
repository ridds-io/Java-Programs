
public class PaySlip {

    private Employee employee;

    public PaySlip(Employee employee) {
        this.employee = employee;
    }

    public void generate() {
        double ctc = employee.calcCTC();
        double tax = 0;
        double inHand = ctc;

        if (employee instanceof Taxable t) {
            tax = t.calcTax();
            inHand = t.calcInHandSalary();
        }

        System.out.println("\n+------------------------------------------+");
        System.out.println("|              PAY SLIP                    |");
        System.out.println("+------------------------------------------+");
        System.out.printf("| %-15s : %-24s|%n", "Name", employee.getName());
        System.out.printf("| %-15s : %-24d|%n", "Employee ID", employee.getEmpID());
        System.out.printf("| %-15s : %-24s|%n", "Designation", employee.getDesignation());
        System.out.println("+------------------------------------------+");
        System.out.printf("| %-15s : ₹%-23.2f|%n", "Gross CTC", ctc);

        if (employee instanceof Taxable) {
            System.out.printf("| %-15s : ₹%-23.2f|%n", "Tax Deduction", tax);
            System.out.printf("| %-15s : ₹%-23.2f|%n", "In-Hand", inHand);
        } else {
            System.out.printf("| %-15s : %-24s|%n", "Tax", "Not Applicable");
        }

        System.out.println("+------------------------------------------+");
    }
}
