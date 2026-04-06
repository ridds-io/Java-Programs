
public interface Taxable {

    double TAX_RATE_LOW = 0.10; // 10% for CTC <= 5 LPA
    double TAX_RATE_MEDIUM = 0.20; // 20% for CTC <= 10 LPA
    double TAX_RATE_HIGH = 0.30; // 30% for CTC > 10 LPA

    double calcTax();

    double calcInHandSalary();
}
