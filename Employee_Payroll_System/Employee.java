
import java.util.Date;

public abstract class Employee {

    private String name;
    private final int empID;
    private int PANNo;
    private int AadharNo;
    private Date joiningDate;
    private String designation;

    public Employee(String name, int empID, int PANNo, int AadharNo, Date joiningDate, String designation) {
        this.name = name;
        this.empID = empID;
        this.PANNo = PANNo;
        this.AadharNo = AadharNo;
        this.joiningDate = joiningDate;
        this.designation = designation;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getEmpID() {
        return empID;
    }

    public int getPANNo() {
        return PANNo;
    }

    public int getAadharNo() {
        return AadharNo;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public String getDesignation() {
        return designation;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPANNo(int PANNo) {
        this.PANNo = PANNo;
    }

    public void setAadharNo(int AadharNo) {
        this.AadharNo = AadharNo;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    // Abstract method — every subclass must implement this
    public abstract double calcCTC();

    public void displayEmployeeDetails() {
        System.out.println("=============================");
        System.out.println("Employee ID   : " + empID);
        System.out.println("Name          : " + name);
        System.out.println("PAN No.       : " + PANNo);
        System.out.println("Aadhar No.    : " + AadharNo);
        System.out.println("Designation   : " + designation);
        System.out.println("Joining Date  : " + joiningDate);
        System.out.println("CTC           : ₹" + calcCTC());
        System.out.println("=============================");
    }
}
