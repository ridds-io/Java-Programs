
import java.util.Date;

public class SalesEmployee extends FullTimeEmployee implements Taxable {

    private double salesTarget;
    private double commissionRate; // e.g. 0.05 = 5%
    private double actualSales;

    public SalesEmployee(String name, int empID, int PANNo, int AadharNo,
            Date joiningDate, String designation,
            double baseSalary, double salesTarget,
            double commissionRate, double actualSales) {
        super(name, empID, PANNo, AadharNo, joiningDate, designation, baseSalary, 0);
        this.salesTarget = salesTarget;
        this.commissionRate = commissionRate;
        this.actualSales = actualSales;
    }

    public double getSalesTarget() {
        return salesTarget;
    }

    public void setSalesTarget(double salesTarget) {
        this.salesTarget = salesTarget;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public double getActualSales() {
        return actualSales;
    }

    public void setActualSales(double actualSales) {
        this.actualSales = actualSales;
    }

    public boolean hitTarget() {
        return actualSales >= salesTarget;
    }

    @Override
    public double calcCTC() {
        // Commission only paid if target is met
        double commission = hitTarget() ? (actualSales * commissionRate) : 0;
        return getBaseSalary() + commission;
    }

    @Override
    public double calcTax() {
        double annualCTC = calcCTC() * 12;
        if (annualCTC <= 500000) {
            return calcCTC() * TAX_RATE_LOW; 
        }else if (annualCTC <= 1000000) {
            return calcCTC() * TAX_RATE_MEDIUM; 
        }else {
            return calcCTC() * TAX_RATE_HIGH;
        }
    }

    @Override
    public double calcInHandSalary() {
        return calcCTC() - calcTax();
    }
}
