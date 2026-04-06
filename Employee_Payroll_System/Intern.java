
import java.util.Date;

public class Intern extends Employee {

    private double stipend;
    private double projectBonus;
    private int durationMonths;
    private String collegeName;
    private boolean ppoOffered; // Pre-Placement Offer

    public Intern(String name, int empID, int PANNo, int AadharNo,
            Date joiningDate, String designation,
            double stipend, double projectBonus,
            int durationMonths, String collegeName) {
        super(name, empID, PANNo, AadharNo, joiningDate, designation);
        this.stipend = stipend;
        this.projectBonus = projectBonus;
        this.durationMonths = durationMonths;
        this.collegeName = collegeName;
        this.ppoOffered = false;
    }

    public double getStipend() {
        return stipend;
    }

    public void setStipend(double stipend) {
        this.stipend = stipend;
    }

    public double getProjectBonus() {
        return projectBonus;
    }

    public void setProjectBonus(double projectBonus) {
        this.projectBonus = projectBonus;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(int durationMonths) {
        this.durationMonths = durationMonths;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public boolean isPpoOffered() {
        return ppoOffered;
    }

    public void offerPPO() {
        this.ppoOffered = true;
    }

    @Override
    public double calcCTC() {
        return stipend + projectBonus;
    }

    @Override
    public void displayEmployeeDetails() {
        super.displayEmployeeDetails();
        System.out.println("College       : " + collegeName);
        System.out.println("Duration      : " + durationMonths + " months");
        System.out.println("PPO Offered   : " + (ppoOffered ? "Yes" : "No"));
    }
}
