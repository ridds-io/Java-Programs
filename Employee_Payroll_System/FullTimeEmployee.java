
import java.util.Date;

public class FullTimeEmployee extends Employee {

    private double baseSalary;
    private double hiringCommission; // used when role is HR

    public FullTimeEmployee(String name, int empID, int PANNo, int AadharNo,
            Date joiningDate, String designation,
            double baseSalary, double hiringCommission) {
        super(name, empID, PANNo, AadharNo, joiningDate, designation);
        this.baseSalary = baseSalary;
        this.hiringCommission = hiringCommission;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public double getHiringCommission() {
        return hiringCommission;
    }

    public void setHiringCommission(double hiringCommission) {
        this.hiringCommission = hiringCommission;
    }

    @Override
    public double calcCTC() {
        // For HR role: CTC = baseSalary + hiringCommission
        // For other full-time roles: CTC = baseSalary
        if (getDesignation().equalsIgnoreCase("HR")) {
            return baseSalary + hiringCommission;
        }
        return baseSalary;
    }
}
