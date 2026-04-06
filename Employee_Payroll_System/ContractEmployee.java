
import java.util.Date;

public class ContractEmployee extends Employee {

    private int noOfHours;
    private double hourlyRate;

    public ContractEmployee(String name, int empID, int PANNo, int AadharNo,
            Date joiningDate, String designation,
            int noOfHours, double hourlyRate) {
        super(name, empID, PANNo, AadharNo, joiningDate, designation);
        this.noOfHours = noOfHours;
        this.hourlyRate = hourlyRate;
    }

    public int getNoOfHours() {
        return noOfHours;
    }

    public void setNoOfHours(int noOfHours) {
        this.noOfHours = noOfHours;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calcCTC() {
        // CTC = noOfHours * hourlyRate
        return noOfHours * hourlyRate;
    }
}
