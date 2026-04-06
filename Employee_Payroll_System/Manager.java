
import java.util.Date;

public class Manager extends FullTimeEmployee {

    private double perfBonus;
    private double travelAllowance;      // TA
    private double educationAllowance;   // eduAllowance

    public Manager(String name, int empID, int PANNo, int AadharNo,
            Date joiningDate, String designation,
            double baseSalary, double hiringCommission,
            double perfBonus, double travelAllowance, double educationAllowance) {
        super(name, empID, PANNo, AadharNo, joiningDate, designation, baseSalary, hiringCommission);
        this.perfBonus = perfBonus;
        this.travelAllowance = travelAllowance;
        this.educationAllowance = educationAllowance;
    }

    public double getPerfBonus() {
        return perfBonus;
    }

    public void setPerfBonus(double perfBonus) {
        this.perfBonus = perfBonus;
    }

    public double getTravelAllowance() {
        return travelAllowance;
    }

    public void setTravelAllowance(double travelAllowance) {
        this.travelAllowance = travelAllowance;
    }

    public double getEducationAllowance() {
        return educationAllowance;
    }

    public void setEducationAllowance(double educationAllowance) {
        this.educationAllowance = educationAllowance;
    }

    @Override
    public double calcCTC() {
        // CTC = baseSalary + perfBonus + TA + eduAllowance
        return getBaseSalary() + perfBonus + travelAllowance + educationAllowance;
    }
}
