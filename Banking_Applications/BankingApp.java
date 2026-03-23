
import java.util.ArrayList;
import java.util.Scanner;

// ─────────────────────────────────────────────
//  Base Class: Account
// ─────────────────────────────────────────────
class Account {

    protected String accountNumber;
    protected String accountType;
    protected double balance;
    protected boolean active;
    protected ArrayList<String> transactionHistory;

    public Account(String accountNumber, String accountType, double balance) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.active = true;
        this.transactionHistory = new ArrayList<>();
    }

    public void deposit(double amount) {
        if (!active) {
            System.out.println("  [Error] Account is closed.");
            return;
        }
        if (amount <= 0) {
            System.out.println("  [Error] Deposit amount must be positive.");
            return;
        }
        balance += amount;
        String entry = String.format("DEPOSIT   | Rs.%,.2f | Balance: Rs.%,.2f", amount, balance);
        transactionHistory.add(entry);
        System.out.printf("  Deposited Rs.%.2f to %s. New balance: Rs.%.2f%n", amount, accountNumber, balance);
    }

    public void withdraw(double amount) {
        if (!active) {
            System.out.println("  [Error] Account is closed.");
            return;
        }
        if (amount <= 0) {
            System.out.println("  [Error] Withdrawal amount must be positive.");
            return;
        }
        if (amount > balance) {
            System.out.println("  [Error] Insufficient funds.");
            return;
        }
        balance -= amount;
        String entry = String.format("WITHDRAWAL| Rs.%,.2f | Balance: Rs.%,.2f", amount, balance);
        transactionHistory.add(entry);
        System.out.printf("  Withdrew Rs.%.2f from %s. New balance: Rs.%.2f%n", amount, accountNumber, balance);
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public boolean isActive() {
        return active;
    }

    public void close() {
        this.active = false;
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void displayInfo() {
        System.out.printf("    %-12s | %-10s | Balance: Rs.%,.2f | %s%n",
                accountNumber, accountType, balance, active ? "ACTIVE" : "CLOSED");
    }
}

// ─────────────────────────────────────────────
//  Child Class: SavingsAccount
// ─────────────────────────────────────────────
class SavingsAccount extends Account {

    private double interestRate;
    private static final double MIN_BALANCE = 500.0;

    public SavingsAccount(String accountNumber, double balance, double interestRate) {
        super(accountNumber, "Savings", balance);
        this.interestRate = interestRate;
    }

    // Overridden: enforces minimum balance rule
    @Override
    public void withdraw(double amount) {
        if (!active) {
            System.out.println("  [Error] Account is closed.");
            return;
        }
        if (amount <= 0) {
            System.out.println("  [Error] Amount must be positive.");
            return;
        }
        if (balance - amount < MIN_BALANCE) {
            System.out.printf("  [Error] Cannot withdraw Rs.%.2f — minimum balance of Rs.%.2f must be maintained.%n",
                    amount, MIN_BALANCE);
            return;
        }
        balance -= amount;
        transactionHistory.add(String.format("WITHDRAWAL| Rs.%,.2f | Balance: Rs.%,.2f", amount, balance));
        System.out.printf("  Withdrew Rs.%.2f from %s. New balance: Rs.%.2f%n", amount, accountNumber, balance);
    }

    public void applyInterest() {
        if (!active) {
            System.out.println("  [Error] Account is closed.");
            return;
        }
        double interest = balance * interestRate / 100.0;
        balance += interest;
        transactionHistory.add(String.format("INTEREST  | Rs.%,.2f | Balance: Rs.%,.2f", interest, balance));
        System.out.printf("  Interest of Rs.%.2f applied at %.1f%% p.a. New balance: Rs.%.2f%n",
                interest, interestRate, balance);
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double r) {
        this.interestRate = r;
    }

    @Override
    public void displayInfo() {
        System.out.printf("    %-12s | %-10s | Balance: Rs.%,.2f | Rate: %.1f%% | %s%n",
                accountNumber, accountType, balance, interestRate, active ? "ACTIVE" : "CLOSED");
    }
}

// ─────────────────────────────────────────────
//  Child Class: LoanAccount  (extends Account)
//  Fields from whiteboard:
//    principalAmt, interestRate, interestAmt,
//    loanType, tenure, collaterals[], penalty
//  Methods from whiteboard:
//    calcEMI(), updatePrincipal(),
//    updateInterestAmt(), payPenalty()
// ─────────────────────────────────────────────
class LoanAccount extends Account {

    private double principalAmt;
    private double interestRate;
    private double interestAmt;
    private String loanType;
    private int tenure;           // months
    private String[] collaterals;
    private double penalty;
    private double emi;

    public LoanAccount(String accountNumber, double principalAmt,
            double interestRate, int tenure, String loanType) {
        super(accountNumber, "Loan", principalAmt);
        this.principalAmt = principalAmt;
        this.interestRate = interestRate;
        this.tenure = tenure;
        this.loanType = loanType;
        this.interestAmt = calcInterestAmt(principalAmt, interestRate, tenure);
        this.emi = calcEMI();
        this.collaterals = new String[0];
        this.penalty = 0.0;
    }

    // Whiteboard method: calcEMI()
    public double calcEMI() {
        double mr = interestRate / 1200.0;
        if (mr == 0) {
            return principalAmt / tenure;
        }
        emi = principalAmt * mr * Math.pow(1 + mr, tenure) / (Math.pow(1 + mr, tenure) - 1);
        return emi;
    }

    private double calcInterestAmt(double p, double r, int n) {
        return (p * r * n) / (12 * 100.0);
    }

    // Whiteboard method: updatePrincipal()
    public void updatePrincipal(double newPrincipal) {
        if (newPrincipal <= 0) {
            System.out.println("  [Error] Principal must be positive.");
            return;
        }
        this.principalAmt = newPrincipal;
        this.balance = newPrincipal;
        this.interestAmt = calcInterestAmt(newPrincipal, interestRate, tenure);
        this.emi = calcEMI();
        System.out.printf("  Principal updated to Rs.%,.2f. New EMI: Rs.%,.2f/mo%n", newPrincipal, emi);
    }

    // Whiteboard method: updateInterestAmt()
    public void updateInterestAmt(double newRate) {
        if (newRate <= 0) {
            System.out.println("  [Error] Interest rate must be positive.");
            return;
        }
        this.interestRate = newRate;
        this.interestAmt = calcInterestAmt(principalAmt, newRate, tenure);
        this.emi = calcEMI();
        System.out.printf("  Interest rate updated to %.1f%%. Interest amount: Rs.%,.2f. New EMI: Rs.%,.2f/mo%n",
                newRate, interestAmt, emi);
    }

    // Whiteboard method: payPenalty()
    public void payPenalty() {
        if (penalty <= 0) {
            System.out.println("  No outstanding penalty.");
            return;
        }
        System.out.printf("  Penalty of Rs.%,.2f paid and cleared.%n", penalty);
        transactionHistory.add(String.format("PENALTY   | Rs.%,.2f | Cleared", penalty));
        penalty = 0.0;
    }

    public void addPenalty(double amount) {
        if (amount <= 0) {
            System.out.println("  [Error] Penalty amount must be positive.");
            return;
        }
        this.penalty += amount;
        System.out.printf("  Penalty of Rs.%,.2f added. Total penalty: Rs.%,.2f%n", amount, penalty);
    }

    public void setCollaterals(String[] collaterals) {
        this.collaterals = collaterals;
    }

    public void setTenure(int t) {
        this.tenure = t;
        this.interestAmt = calcInterestAmt(principalAmt, interestRate, t);
        this.emi = calcEMI();
    }

    // Overridden: deposit = loan repayment (reduces outstanding balance)
    @Override
    public void deposit(double amount) {
        if (!active) {
            System.out.println("  [Error] Account is closed.");
            return;
        }
        if (amount <= 0) {
            System.out.println("  [Error] Repayment amount must be positive.");
            return;
        }
        if (amount > balance) {
            System.out.printf("  [Error] Amount exceeds outstanding loan of Rs.%.2f.%n", balance);
            return;
        }
        balance -= amount;
        transactionHistory.add(String.format("REPAYMENT | Rs.%,.2f | Outstanding: Rs.%,.2f", amount, balance));
        System.out.printf("  Repayment of Rs.%.2f received. Outstanding loan: Rs.%.2f%n", amount, balance);
    }

    // Overridden: withdrawal not applicable on loan account
    @Override
    public void withdraw(double amount) {
        System.out.println("  [Error] Withdrawal is not allowed on a Loan Account.");
    }

    // Getters
    public double getPrincipalAmt() {
        return principalAmt;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getInterestAmt() {
        return interestAmt;
    }

    public String getLoanType() {
        return loanType;
    }

    public int getTenure() {
        return tenure;
    }

    public String[] getCollaterals() {
        return collaterals;
    }

    public double getPenalty() {
        return penalty;
    }

    public double getEMI() {
        return emi;
    }

    public double getOutstanding() {
        return balance;
    }

    // Setters
    public void setInterestRate(double r) {
        updateInterestAmt(r);
    }

    public void setTenureMonths(int t) {
        setTenure(t);
    }

    public void setLoanType(String t) {
        this.loanType = t;
    }

    @Override
    public void displayInfo() {
        System.out.printf("    %-12s | %-12s | %-10s | Outstanding: Rs.%,.2f | EMI: Rs.%,.2f/mo | %s%n",
                accountNumber, accountType, loanType, balance, emi, active ? "ACTIVE" : "CLOSED");
    }
}

// ─────────────────────────────────────────────
//  Customer Class
//  Fields from whiteboard:
//    custId, name, PAN, Aadhar, dob, addressProof
//  Methods from whiteboard:
//    viewTransactionHistory(), downloadStatement(),
//    updatePersonalDetails(), viewDashboard()
// ─────────────────────────────────────────────
class Customer {

    private int custId;
    private String name;
    private String PAN;
    private String aadhar;
    private String dob;
    private String addressProof;
    private String email;
    private String phone;
    private ArrayList<Account> accounts;

    public Customer(int custId, String name, String PAN, String aadhar,
            String dob, String addressProof, String email, String phone) {
        this.custId = custId;
        this.name = name;
        this.PAN = PAN;
        this.aadhar = aadhar;
        this.dob = dob;
        this.addressProof = addressProof;
        this.email = email;
        this.phone = phone;
        this.accounts = new ArrayList<>();
    }

    // ── Getters ───────────────────────────────────────────────────────
    public int getCustomerId() {
        return custId;
    }

    public String getName() {
        return name;
    }

    public String getPAN() {
        return PAN;
    }

    public String getAadhar() {
        return aadhar;
    }

    public String getDob() {
        return dob;
    }

    public String getAddressProof() {
        return addressProof;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    // ── Setters ───────────────────────────────────────────────────────
    public void setName(String n) {
        this.name = n;
    }

    public void setPAN(String p) {
        this.PAN = p;
    }

    public void setAadhar(String a) {
        this.aadhar = a;
    }

    public void setDob(String d) {
        this.dob = d;
    }

    public void setAddressProof(String ap) {
        this.addressProof = ap;
    }

    public void setEmail(String e) {
        this.email = e;
    }

    public void setPhone(String p) {
        this.phone = p;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
    }

    // ── Whiteboard method: viewTransactionHistory() ───────────────────
    public void viewTransactionHistory() {
        System.out.println("\n  +----- Transaction History: " + name + " " + "-".repeat(35) + "+");
        boolean any = false;
        for (Account a : accounts) {
            ArrayList<String> hist = a.getTransactionHistory();
            if (!hist.isEmpty()) {
                System.out.printf("  Account: %s (%s)%n", a.getAccountNumber(), a.getAccountType());
                for (String entry : hist) {
                    System.out.println("    " + entry);
                }
                any = true;
            }
        }
        if (!any) {
            System.out.println("  No transactions recorded yet.");
        }
        System.out.println("  +" + "-".repeat(62) + "+\n");
    }

    // ── Whiteboard method: downloadStatement() ────────────────────────
    public void downloadStatement() {
        System.out.println("\n  [Statement] Generating statement for " + name + "...");
        System.out.println("  +" + "=".repeat(62) + "+");
        System.out.printf("  | ACCOUNT STATEMENT                                            |%n");
        System.out.printf("  | Customer : %-50s|%n", name);
        System.out.printf("  | Cust ID  : %-50d|%n", custId);
        System.out.printf("  | PAN      : %-50s|%n", PAN);
        System.out.println("  +" + "=".repeat(62) + "+");
        for (Account a : accounts) {
            System.out.printf("  Account: %s | Type: %s | Balance: Rs.%,.2f%n",
                    a.getAccountNumber(), a.getAccountType(), a.getBalance());
            ArrayList<String> hist = a.getTransactionHistory();
            if (hist.isEmpty()) {
                System.out.println("    (No transactions)");
            } else {
                for (String entry : hist) {
                    System.out.println("    " + entry);
                }
            }
        }
        System.out.println("  +" + "=".repeat(62) + "+");
        System.out.println("  [Statement downloaded successfully.]\n");
    }

    // ── Whiteboard method: updatePersonalDetails() ────────────────────
    public void updatePersonalDetails(Scanner sc) {
        System.out.println("\n  Update Personal Details for: " + name);
        System.out.printf("  Name         [%s]: ", name);
        String v = sc.nextLine().trim();
        if (!v.isEmpty()) {
            setName(v);
        }
        System.out.printf("  Email        [%s]: ", email);
        v = sc.nextLine().trim();
        if (!v.isEmpty()) {
            setEmail(v);
        }
        System.out.printf("  Phone        [%s]: ", phone);
        v = sc.nextLine().trim();
        if (!v.isEmpty()) {
            setPhone(v);
        }
        System.out.printf("  PAN          [%s]: ", PAN);
        v = sc.nextLine().trim();
        if (!v.isEmpty()) {
            setPAN(v);
        }
        System.out.printf("  Aadhar       [%s]: ", aadhar);
        v = sc.nextLine().trim();
        if (!v.isEmpty()) {
            setAadhar(v);
        }
        System.out.printf("  Date of Birth[%s]: ", dob);
        v = sc.nextLine().trim();
        if (!v.isEmpty()) {
            setDob(v);
        }
        System.out.printf("  Address Proof[%s]: ", addressProof);
        v = sc.nextLine().trim();
        if (!v.isEmpty()) {
            setAddressProof(v);
        }
        System.out.println("  Personal details updated successfully.");
    }

    // ── Whiteboard method: viewDashboard() ────────────────────────────
    public void viewDashboard() {
        System.out.println("\n  +" + "=".repeat(62) + "+");
        System.out.printf("  |  DASHBOARD — %-50s|%n", name);
        System.out.println("  +" + "=".repeat(62) + "+");
        System.out.printf("  |  Customer ID  : %-45d|%n", custId);
        System.out.printf("  |  Name         : %-45s|%n", name);
        System.out.printf("  |  PAN          : %-45s|%n", PAN);
        System.out.printf("  |  Aadhar       : %-45s|%n", aadhar);
        System.out.printf("  |  Date of Birth: %-45s|%n", dob);
        System.out.printf("  |  Address Proof: %-45s|%n", addressProof);
        System.out.printf("  |  Email        : %-45s|%n", email);
        System.out.printf("  |  Phone        : %-45s|%n", phone);
        System.out.println("  +" + "-".repeat(62) + "+");
        System.out.printf("  |  %-14s %-14s %-12s %-18s|%n", "Account No.", "Type", "Status", "Balance");
        System.out.println("  |  " + "-".repeat(59) + "|");
        for (Account a : accounts) {
            if (a instanceof LoanAccount) {
                LoanAccount la = (LoanAccount) a;
                System.out.printf("  |  %-14s %-14s %-12s Rs.%,.2f (EMI: Rs.%,.2f/mo)%n",
                        la.getAccountNumber(), la.getLoanType() + " Loan",
                        la.isActive() ? "ACTIVE" : "CLOSED", la.getBalance(), la.getEMI());
            } else {
                System.out.printf("  |  %-14s %-14s %-12s Rs.%,.2f%n",
                        a.getAccountNumber(), a.getAccountType(),
                        a.isActive() ? "ACTIVE" : "CLOSED", a.getBalance());
            }
        }
        System.out.println("  +" + "-".repeat(62) + "+");
        System.out.printf("  |  Total Savings : %-43s|%n", String.format("Rs.%,.2f", getTotalSavings()));
        System.out.printf("  |  Total Loans   : %-43s|%n", String.format("Rs.%,.2f", getTotalLoan()));
        System.out.println("  +" + "=".repeat(62) + "+\n");
    }

    public double getTotalSavings() {
        double t = 0;
        for (Account a : accounts) {
            if (a instanceof SavingsAccount && a.isActive()) {
                t += a.getBalance();
            }
        }
        return t;
    }

    public double getTotalLoan() {
        double t = 0;
        for (Account a : accounts) {
            if (a instanceof LoanAccount && a.isActive()) {
                t += a.getBalance();
            }
        }
        return t;
    }

    public void displayConsolidatedInfo() {
        System.out.println("  +-----------------------------------------------------------------+");
        System.out.printf("  |  Customer ID  : %-48d|%n", custId);
        System.out.printf("  |  Name         : %-48s|%n", name);
        System.out.printf("  |  PAN          : %-48s|%n", PAN);
        System.out.printf("  |  Email        : %-48s|%n", email);
        System.out.printf("  |  Phone        : %-48s|%n", phone);
        System.out.println("  +-----------------------------------------------------------------+");
        if (accounts.isEmpty()) {
            System.out.println("  |  (no accounts linked)                                           |");
        } else {
            System.out.println("  |  Accounts:                                                      |");
            for (Account a : accounts) {
                System.out.print("  |  ");
                a.displayInfo();
            }
        }
        System.out.println("  +-----------------------------------------------------------------+");
        System.out.printf("  |  Total Active Savings : %-39s|%n",
                String.format("Rs.%,.2f", getTotalSavings()));
        System.out.printf("  |  Total Active Loans   : %-39s|%n",
                String.format("Rs.%,.2f", getTotalLoan()));
        System.out.println("  +-----------------------------------------------------------------+");
        System.out.println();
    }
}

// ─────────────────────────────────────────────
//  Main Class
// ─────────────────────────────────────────────
public class BankingApp {

    private static int savingsCounter = 1005;
    private static int loanCounter = 2004;
    private static int customerCounter = 4;

    // ── utilities ─────────────────────────────────────────────────────
    static void div() {
        System.out.println("  " + "-".repeat(72));
    }

    static Customer ownerOf(Account acc, ArrayList<Customer> customers) {
        for (Customer c : customers) {
            for (Account a : c.getAccounts()) {
                if (a == acc) {
                    return c;
                }
            }
        }
        return null;
    }

    static void listAccounts(ArrayList<Account> accounts, ArrayList<Customer> customers) {
        System.out.println();
        System.out.printf("  %-4s %-14s %-16s %-12s %-10s %-8s %-18s%n",
                "No.", "Account No.", "Owner", "Type", "Loan Type", "Status", "Balance");
        div();
        for (int i = 0; i < accounts.size(); i++) {
            Account a = accounts.get(i);
            Customer c = ownerOf(a, customers);
            String loanType = (a instanceof LoanAccount) ? ((LoanAccount) a).getLoanType() : "-";
            System.out.printf("  %-4d %-14s %-16s %-12s %-10s %-8s Rs.%,.2f%n",
                    i + 1, a.getAccountNumber(),
                    (c != null ? c.getName() : "Unknown"),
                    a.getAccountType(), loanType,
                    a.isActive() ? "ACTIVE" : "CLOSED",
                    a.getBalance());
        }
        System.out.println();
    }

    static void listCustomers(ArrayList<Customer> customers) {
        System.out.println();
        System.out.printf("  %-4s %-6s %-18s %-26s %-14s %-12s%n",
                "No.", "ID", "Name", "Email", "Phone", "PAN");
        div();
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            System.out.printf("  %-4d %-6d %-18s %-26s %-14s %-12s%n",
                    i + 1, c.getCustomerId(), c.getName(),
                    c.getEmail(), c.getPhone(), c.getPAN());
        }
        System.out.println();
    }

    static void displayAccountDetails(Account acc, ArrayList<Customer> customers) {
        Customer c = ownerOf(acc, customers);
        System.out.println();
        System.out.println("  +-- Account Details -----------------------------------------------+");
        System.out.printf("  |  Owner       : %-52s|%n", (c != null ? c.getName() : "Unknown"));
        System.out.printf("  |  Account No. : %-52s|%n", acc.getAccountNumber());
        System.out.printf("  |  Type        : %-52s|%n", acc.getAccountType());
        System.out.printf("  |  Status      : %-52s|%n", acc.isActive() ? "ACTIVE" : "CLOSED");
        System.out.printf("  |  Balance     : %-52s|%n", String.format("Rs.%,.2f", acc.getBalance()));
        if (acc instanceof SavingsAccount) {
            SavingsAccount sa = (SavingsAccount) acc;
            System.out.printf("  |  Interest    : %-52s|%n", sa.getInterestRate() + "% p.a.");
        }
        if (acc instanceof LoanAccount) {
            LoanAccount la = (LoanAccount) acc;
            System.out.printf("  |  Loan Type   : %-52s|%n", la.getLoanType());
            System.out.printf("  |  Principal   : %-52s|%n", String.format("Rs.%,.2f", la.getPrincipalAmt()));
            System.out.printf("  |  Rate        : %-52s|%n", la.getInterestRate() + "% p.a.");
            System.out.printf("  |  Interest Amt: %-52s|%n", String.format("Rs.%,.2f", la.getInterestAmt()));
            System.out.printf("  |  Tenure      : %-52s|%n", la.getTenure() + " months");
            System.out.printf("  |  EMI         : %-52s|%n", String.format("Rs.%,.2f /month", la.getEMI()));
            System.out.printf("  |  Penalty     : %-52s|%n", String.format("Rs.%,.2f", la.getPenalty()));
            if (la.getCollaterals().length > 0) {
                System.out.printf("  |  Collaterals : %-52s|%n", String.join(", ", la.getCollaterals()));
            }
        }
        System.out.println("  +-----------------------------------------------------------------+");
        System.out.println();
    }

    static Account pickAccount(ArrayList<Account> accounts, Scanner sc) {
        System.out.print("  Select account (1-" + accounts.size() + "): ");
        try {
            int idx = Integer.parseInt(sc.nextLine().trim());
            if (idx < 1 || idx > accounts.size()) {
                System.out.println("  Invalid selection.");
                return null;
            }
            return accounts.get(idx - 1);
        } catch (NumberFormatException e) {
            System.out.println("  Invalid input.");
            return null;
        }
    }

    static Customer pickCustomer(ArrayList<Customer> customers, Scanner sc) {
        System.out.print("  Select customer (1-" + customers.size() + "): ");
        try {
            int idx = Integer.parseInt(sc.nextLine().trim());
            if (idx < 1 || idx > customers.size()) {
                System.out.println("  Invalid selection.");
                return null;
            }
            return customers.get(idx - 1);
        } catch (NumberFormatException e) {
            System.out.println("  Invalid input.");
            return null;
        }
    }

    static double readAmount(Scanner sc) {
        try {
            double v = Double.parseDouble(sc.nextLine().trim());
            if (v <= 0) {
                System.out.println("  Amount must be positive.");
                return -1;
            }
            return v;
        } catch (NumberFormatException e) {
            System.out.println("  Invalid amount.");
            return -1;
        }
    }

    static int readPositiveInt(Scanner sc) {
        try {
            int v = Integer.parseInt(sc.nextLine().trim());
            if (v <= 0) {
                System.out.println("  Value must be a positive integer.");
                return -1;
            }
            return v;
        } catch (NumberFormatException e) {
            System.out.println("  Invalid number.");
            return -1;
        }
    }

    // ══════════════════════════════════════════════════════════════════
    //  CUSTOMER SELF-SERVICE MENU
    //  (viewDashboard, viewTransactionHistory, downloadStatement,
    //   updatePersonalDetails, payPenalty)
    // ══════════════════════════════════════════════════════════════════
    static void customerMenu(ArrayList<Customer> customers,
            ArrayList<Account> allAccounts, Scanner sc) {
        if (customers.isEmpty()) {
            System.out.println("  No customers found.");
            return;
        }
        listCustomers(customers);
        Customer c = pickCustomer(customers, sc);
        if (c == null) {
            return;
        }

        while (true) {
            System.out.println("\n  +---------------------------------------------+");
            System.out.printf("  |  Customer: %-33s|%n", c.getName());
            System.out.println("  +---------------------------------------------+");
            System.out.println("  |  1. View Dashboard                          |");
            System.out.println("  |  2. View Transaction History                |");
            System.out.println("  |  3. Download Statement                      |");
            System.out.println("  |  4. Update Personal Details                 |");
            System.out.println("  |  5. Pay Loan Penalty                        |");
            System.out.println("  |  0. Back                                    |");
            System.out.println("  +---------------------------------------------+");
            System.out.print("  Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input.");
                continue;
            }

            if (choice == 0) {
                break;
            }

            switch (choice) {
                case 1:
                    c.viewDashboard();
                    break;
                case 2:
                    c.viewTransactionHistory();
                    break;
                case 3:
                    c.downloadStatement();
                    break;
                case 4:
                    c.updatePersonalDetails(sc);
                    break;
                case 5: {
                    ArrayList<Account> loans = new ArrayList<>();
                    for (Account a : c.getAccounts()) {
                        if (a instanceof LoanAccount && a.isActive()) {
                            loans.add(a);
                        }
                    }
                    if (loans.isEmpty()) {
                        System.out.println("  No active loan accounts.");
                        break;
                    }
                    listAccounts(loans, customers);
                    Account acc = pickAccount(loans, sc);
                    if (acc == null) {
                        break;
                    }
                    ((LoanAccount) acc).payPenalty();
                    break;
                }
                default:
                    System.out.println("  Invalid choice. Please enter 0-5.");
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════
    //  ACCOUNT & CUSTOMER MANAGEMENT MENU
    // ══════════════════════════════════════════════════════════════════
    static void managementMenu(ArrayList<Account> allAccounts,
            ArrayList<Customer> customers, Scanner sc) {
        while (true) {
            System.out.println("\n  +-----------------------------------------------+");
            System.out.println("  |      Account & Customer Management            |");
            System.out.println("  +-----------------------------------------------+");
            System.out.println("  |  1. Open new account for a customer           |");
            System.out.println("  |  2. Close an account                          |");
            System.out.println("  |  3. Modify account details                    |");
            System.out.println("  |  4. Add penalty to loan account               |");
            System.out.println("  |  5. Set collaterals for loan account          |");
            System.out.println("  |  6. Add new customer                          |");
            System.out.println("  |  7. Show all customers                        |");
            System.out.println("  |  0. Back                                      |");
            System.out.println("  +-----------------------------------------------+");
            System.out.print("  Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input.");
                continue;
            }

            if (choice == 0) {
                break;
            }

            switch (choice) {

                // ── 1. Open new account ──────────────────────────────
                case 1: {
                    if (customers.isEmpty()) {
                        System.out.println("  No customers yet. Add a customer first (option 6).");
                        break;
                    }
                    listCustomers(customers);
                    Customer owner = pickCustomer(customers, sc);
                    if (owner == null) {
                        break;
                    }

                    System.out.println("\n  Account type:  1. Savings   2. Loan");
                    System.out.print("  Choice: ");
                    String typeChoice = sc.nextLine().trim();

                    if (typeChoice.equals("1")) {
                        System.out.print("  Opening balance (min Rs.500): Rs.");
                        double bal = readAmount(sc);
                        if (bal < 500) {
                            System.out.println("  Opening balance must be at least Rs.500.");
                            break;
                        }
                        System.out.print("  Interest rate (% p.a., e.g. 3.5): ");
                        double rate = readAmount(sc);
                        if (rate <= 0) {
                            break;
                        }
                        String accNum = "SA" + (++savingsCounter);
                        SavingsAccount sa = new SavingsAccount(accNum, bal, rate);
                        owner.addAccount(sa);
                        allAccounts.add(sa);
                        System.out.println("\n  Savings Account opened successfully!");
                        displayAccountDetails(sa, customers);

                    } else if (typeChoice.equals("2")) {
                        System.out.print("  Loan principal: Rs.");
                        double principal = readAmount(sc);
                        if (principal <= 0) {
                            break;
                        }
                        System.out.print("  Interest rate (% p.a., e.g. 9.5): ");
                        double rate = readAmount(sc);
                        if (rate <= 0) {
                            break;
                        }
                        System.out.print("  Tenure (months, e.g. 60): ");
                        int tenure = readPositiveInt(sc);
                        if (tenure <= 0) {
                            break;
                        }
                        System.out.print("  Loan type (e.g. Home / Car / Personal / Education): ");
                        String loanType = sc.nextLine().trim();
                        if (loanType.isEmpty()) {
                            loanType = "Personal";
                        }
                        String accNum = "LA" + (++loanCounter);
                        LoanAccount la = new LoanAccount(accNum, principal, rate, tenure, loanType);
                        owner.addAccount(la);
                        allAccounts.add(la);
                        System.out.println("\n  Loan Account opened successfully!");
                        displayAccountDetails(la, customers);

                    } else {
                        System.out.println("  Invalid account type.");
                    }
                    break;
                }

                // ── 2. Close an account ──────────────────────────────
                case 2: {
                    ArrayList<Account> active = new ArrayList<>();
                    for (Account a : allAccounts) {
                        if (a.isActive()) {
                            active.add(a);
                        }
                    }
                    if (active.isEmpty()) {
                        System.out.println("  No active accounts to close.");
                        break;
                    }
                    listAccounts(active, customers);
                    Account acc = pickAccount(active, sc);
                    if (acc == null) {
                        break;
                    }
                    displayAccountDetails(acc, customers);

                    if (acc instanceof LoanAccount && acc.getBalance() > 0) {
                        System.out.printf("  [Error] Cannot close loan — outstanding balance of Rs.%,.2f must be cleared.%n",
                                acc.getBalance());
                        break;
                    }
                    if (acc instanceof SavingsAccount && acc.getBalance() > 0) {
                        System.out.printf("  Warning: Balance of Rs.%,.2f will be forfeited on closure.%n",
                                acc.getBalance());
                    }
                    System.out.print("  Confirm close account " + acc.getAccountNumber() + "? (yes / no): ");
                    String confirm = sc.nextLine().trim().toLowerCase();
                    if (confirm.equals("yes") || confirm.equals("y")) {
                        acc.close();
                        System.out.println("  Account " + acc.getAccountNumber() + " closed successfully.");
                    } else {
                        System.out.println("  Close operation cancelled.");
                    }
                    break;
                }

                // ── 3. Modify account details ────────────────────────
                case 3: {
                    ArrayList<Account> active = new ArrayList<>();
                    for (Account a : allAccounts) {
                        if (a.isActive()) {
                            active.add(a);
                        }
                    }
                    if (active.isEmpty()) {
                        System.out.println("  No active accounts.");
                        break;
                    }
                    listAccounts(active, customers);
                    Account acc = pickAccount(active, sc);
                    if (acc == null) {
                        break;
                    }
                    displayAccountDetails(acc, customers);

                    if (acc instanceof SavingsAccount) {
                        SavingsAccount sa = (SavingsAccount) acc;
                        System.out.printf("  Current interest rate: %.1f%% p.a.%n", sa.getInterestRate());
                        System.out.print("  New interest rate (Enter to skip): ");
                        String input = sc.nextLine().trim();
                        if (!input.isEmpty()) {
                            try {
                                double r = Double.parseDouble(input);
                                if (r <= 0) {
                                    System.out.println("  Rate must be positive.");
                                    break;
                                }
                                sa.setInterestRate(r);
                                System.out.printf("  Interest rate updated to %.1f%% p.a.%n", r);
                            } catch (NumberFormatException e) {
                                System.out.println("  Invalid rate.");
                            }
                        }
                    } else if (acc instanceof LoanAccount) {
                        LoanAccount la = (LoanAccount) acc;
                        System.out.println("  Modify:  1. Interest rate  2. Tenure  3. Loan type  4. Principal");
                        System.out.print("  Choice: ");
                        String sub = sc.nextLine().trim();
                        if (sub.equals("1")) {
                            System.out.printf("  Current: %.1f%% p.a.  New rate: ", la.getInterestRate());
                            String input = sc.nextLine().trim();
                            if (!input.isEmpty()) {
                                try {
                                    la.updateInterestAmt(Double.parseDouble(input));
                                } catch (NumberFormatException e) {
                                    System.out.println("  Invalid input.");
                                }
                            }
                        } else if (sub.equals("2")) {
                            System.out.printf("  Current tenure: %d months  New tenure: ", la.getTenure());
                            String input = sc.nextLine().trim();
                            if (!input.isEmpty()) {
                                try {
                                    int t = Integer.parseInt(input);
                                    if (t <= 0) {
                                        System.out.println("  Must be positive.");
                                        break;
                                    }
                                    la.setTenure(t);
                                    System.out.printf("  Tenure updated to %d months. EMI: Rs.%,.2f/mo%n", t, la.getEMI());
                                } catch (NumberFormatException e) {
                                    System.out.println("  Invalid.");
                                }
                            }
                        } else if (sub.equals("3")) {
                            System.out.printf("  Current loan type: %s  New type: ", la.getLoanType());
                            String input = sc.nextLine().trim();
                            if (!input.isEmpty()) {
                                la.setLoanType(input);
                                System.out.println("  Loan type updated.");
                            }
                        } else if (sub.equals("4")) {
                            System.out.printf("  Current principal: Rs.%,.2f  New principal: Rs.", la.getPrincipalAmt());
                            String input = sc.nextLine().trim();
                            if (!input.isEmpty()) {
                                try {
                                    la.updatePrincipal(Double.parseDouble(input));
                                } catch (NumberFormatException e) {
                                    System.out.println("  Invalid.");
                                }
                            }
                        } else {
                            System.out.println("  Invalid choice.");
                        }
                    }
                    break;
                }

                // ── 4. Add penalty to loan account ───────────────────
                case 4: {
                    ArrayList<Account> loans = new ArrayList<>();
                    for (Account a : allAccounts) {
                        if (a instanceof LoanAccount && a.isActive()) {
                            loans.add(a);
                        }
                    }
                    if (loans.isEmpty()) {
                        System.out.println("  No active loan accounts.");
                        break;
                    }
                    listAccounts(loans, customers);
                    Account acc = pickAccount(loans, sc);
                    if (acc == null) {
                        break;
                    }
                    System.out.print("  Enter penalty amount: Rs.");
                    double amt = readAmount(sc);
                    if (amt > 0) {
                        ((LoanAccount) acc).addPenalty(amt);
                    }
                    break;
                }

                // ── 5. Set collaterals for loan account ───────────────
                case 5: {
                    ArrayList<Account> loans = new ArrayList<>();
                    for (Account a : allAccounts) {
                        if (a instanceof LoanAccount && a.isActive()) {
                            loans.add(a);
                        }
                    }
                    if (loans.isEmpty()) {
                        System.out.println("  No active loan accounts.");
                        break;
                    }
                    listAccounts(loans, customers);
                    Account acc = pickAccount(loans, sc);
                    if (acc == null) {
                        break;
                    }
                    System.out.print("  Enter collaterals (comma-separated, e.g. Property,Gold): ");
                    String input = sc.nextLine().trim();
                    if (!input.isEmpty()) {
                        String[] cols = input.split(",");
                        for (int i = 0; i < cols.length; i++) {
                            cols[i] = cols[i].trim();
                        }
                        ((LoanAccount) acc).setCollaterals(cols);
                        System.out.println("  Collaterals set: " + String.join(", ", cols));
                    }
                    break;
                }

                // ── 6. Add new customer ──────────────────────────────
                case 6: {
                    System.out.print("  Full name      : ");
                    String name = sc.nextLine().trim();
                    if (name.isEmpty()) {
                        System.out.println("  Name cannot be empty.");
                        break;
                    }
                    System.out.print("  PAN            : ");
                    String pan = sc.nextLine().trim();
                    System.out.print("  Aadhar         : ");
                    String aadhar = sc.nextLine().trim();
                    System.out.print("  Date of Birth  : ");
                    String dob = sc.nextLine().trim();
                    System.out.print("  Address Proof  : ");
                    String addr = sc.nextLine().trim();
                    System.out.print("  Email          : ");
                    String email = sc.nextLine().trim();
                    System.out.print("  Phone          : ");
                    String phone = sc.nextLine().trim();
                    Customer nc = new Customer(customerCounter++, name, pan, aadhar, dob, addr, email, phone);
                    customers.add(nc);
                    System.out.println("\n  Customer added! ID: " + nc.getCustomerId() + " | Name: " + nc.getName());
                    break;
                }

                // ── 7. Show all customers ────────────────────────────
                case 7: {
                    listCustomers(customers);
                    break;
                }

                default:
                    System.out.println("  Invalid choice. Please enter 0-7.");
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════
    //  TRANSACTION MENU
    // ══════════════════════════════════════════════════════════════════
    static void transactionMenu(ArrayList<Account> allAccounts,
            ArrayList<Customer> customers, Scanner sc) {
        while (true) {
            System.out.println("\n  +---------------------------------------------+");
            System.out.println("  |              Transaction Menu               |");
            System.out.println("  +---------------------------------------------+");
            System.out.println("  |  1. Display account details                 |");
            System.out.println("  |  2. Deposit                                 |");
            System.out.println("  |  3. Withdraw                                |");
            System.out.println("  |  4. Loan repayment                          |");
            System.out.println("  |  5. Apply interest  (Savings only)          |");
            System.out.println("  |  6. Show all accounts                       |");
            System.out.println("  |  7. Consolidated customer report            |");
            System.out.println("  |  0. Back                                    |");
            System.out.println("  +---------------------------------------------+");
            System.out.print("  Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input.");
                continue;
            }

            if (choice == 0) {
                break;
            }

            switch (choice) {
                case 1: {
                    if (allAccounts.isEmpty()) {
                        System.out.println("  No accounts found.");
                        break;
                    }
                    listAccounts(allAccounts, customers);
                    Account acc = pickAccount(allAccounts, sc);
                    if (acc != null) {
                        displayAccountDetails(acc, customers);
                    }
                    break;
                }
                case 2: {
                    ArrayList<Account> active = new ArrayList<>();
                    for (Account a : allAccounts) {
                        if (a.isActive()) {
                            active.add(a);
                        }
                    }
                    if (active.isEmpty()) {
                        System.out.println("  No active accounts.");
                        break;
                    }
                    listAccounts(active, customers);
                    Account acc = pickAccount(active, sc);
                    if (acc == null) {
                        break;
                    }
                    displayAccountDetails(acc, customers);
                    System.out.print("  Enter deposit amount: Rs.");
                    double amt = readAmount(sc);
                    if (amt > 0) {
                        System.out.println();
                        acc.deposit(amt);
                    }
                    break;
                }
                case 3: {
                    ArrayList<Account> active = new ArrayList<>();
                    for (Account a : allAccounts) {
                        if (a.isActive()) {
                            active.add(a);
                        }
                    }
                    if (active.isEmpty()) {
                        System.out.println("  No active accounts.");
                        break;
                    }
                    listAccounts(active, customers);
                    Account acc = pickAccount(active, sc);
                    if (acc == null) {
                        break;
                    }
                    displayAccountDetails(acc, customers);
                    System.out.print("  Enter withdrawal amount: Rs.");
                    double amt = readAmount(sc);
                    if (amt > 0) {
                        System.out.println();
                        acc.withdraw(amt);
                    }
                    break;
                }
                case 4: {
                    ArrayList<Account> loans = new ArrayList<>();
                    for (Account a : allAccounts) {
                        if (a instanceof LoanAccount && a.isActive()) {
                            loans.add(a);
                        }
                    }
                    if (loans.isEmpty()) {
                        System.out.println("  No active loan accounts.");
                        break;
                    }
                    listAccounts(loans, customers);
                    Account acc = pickAccount(loans, sc);
                    if (acc == null) {
                        break;
                    }
                    displayAccountDetails(acc, customers);
                    System.out.print("  Enter repayment amount: Rs.");
                    double amt = readAmount(sc);
                    if (amt > 0) {
                        System.out.println();
                        acc.deposit(amt);
                    }
                    break;
                }
                case 5: {
                    ArrayList<Account> savings = new ArrayList<>();
                    for (Account a : allAccounts) {
                        if (a instanceof SavingsAccount && a.isActive()) {
                            savings.add(a);
                        }
                    }
                    if (savings.isEmpty()) {
                        System.out.println("  No active savings accounts.");
                        break;
                    }
                    listAccounts(savings, customers);
                    Account acc = pickAccount(savings, sc);
                    if (acc == null) {
                        break;
                    }
                    displayAccountDetails(acc, customers);
                    System.out.println();
                    ((SavingsAccount) acc).applyInterest();
                    break;
                }
                case 6: {
                    if (allAccounts.isEmpty()) {
                        System.out.println("  No accounts found.");
                        break;
                    }
                    listAccounts(allAccounts, customers);
                    break;
                }
                case 7: {
                    if (customers.isEmpty()) {
                        System.out.println("  No customers found.");
                        break;
                    }
                    System.out.println("\n  =============================================================");
                    System.out.println("       Consolidated Account Information - All Customers      ");
                    System.out.println("  =============================================================\n");
                    for (Customer c : customers) {
                        c.displayConsolidatedInfo();
                    }
                    break;
                }
                default:
                    System.out.println("  Invalid choice. Please enter 0-7.");
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════
    //  MAIN MENU
    // ══════════════════════════════════════════════════════════════════
    static void mainMenu(ArrayList<Account> allAccounts,
            ArrayList<Customer> customers, Scanner sc) {
        while (true) {
            System.out.println("\n  +===============================================+");
            System.out.println("  |            NexBank  -  Main Menu              |");
            System.out.println("  +===============================================+");
            System.out.println("  |  1.  Transactions                             |");
            System.out.println("  |  2.  Account & Customer Management            |");
            System.out.println("  |  3.  Customer Self-Service                    |");
            System.out.println("  |  0.  Exit                                     |");
            System.out.println("  +===============================================+");
            System.out.print("  Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input.");
                continue;
            }

            switch (choice) {
                case 0:
                    System.out.println("\n  Thank you for using NexBank. Goodbye!\n");
                    return;
                case 1:
                    transactionMenu(allAccounts, customers, sc);
                    break;
                case 2:
                    managementMenu(allAccounts, customers, sc);
                    break;
                case 3:
                    customerMenu(customers, allAccounts, sc);
                    break;
                default:
                    System.out.println("  Invalid choice. Please enter 0-3.");
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════
    //  ENTRY POINT
    // ══════════════════════════════════════════════════════════════════
    public static void main(String[] args) {

        ArrayList<Account> allAccounts = new ArrayList<>();
        ArrayList<Customer> customers = new ArrayList<>();

        // Seed customers — now with PAN, Aadhar, DOB, addressProof
        Customer c1 = new Customer(1, "Aanya Sharma", "ABCPA1234A", "1234-5678-9012",
                "01/04/1990", "Passport", "aanya@email.com", "98765-43210");
        Customer c2 = new Customer(2, "Rohan Mehta", "XYZRM5678B", "9876-5432-1098",
                "15/08/1985", "DrivingLicense", "rohan@email.com", "91234-56789");
        Customer c3 = new Customer(3, "Priya Nair", "PQRPN9012C", "4567-8901-2345",
                "22/11/1995", "VoterID", "priya@email.com", "87654-32109");
        customers.add(c1);
        customers.add(c2);
        customers.add(c3);

        // Seed accounts — LoanAccount now takes loanType as 5th arg
        SavingsAccount sa1 = new SavingsAccount("SA1001", 85000.00, 4.0);
        SavingsAccount sa2 = new SavingsAccount("SA1002", 12000.00, 3.5);
        LoanAccount la1 = new LoanAccount("LA2001", 500000.00, 9.5, 120, "Home");
        SavingsAccount sa3 = new SavingsAccount("SA1003", 240000.00, 4.5);
        LoanAccount la2 = new LoanAccount("LA2002", 200000.00, 8.5, 60, "Car");
        SavingsAccount sa4 = new SavingsAccount("SA1004", 55000.00, 3.5);
        LoanAccount la3 = new LoanAccount("LA2003", 750000.00, 10.0, 180, "Education");

        // Set some collaterals
        la1.setCollaterals(new String[]{"Property", "FD"});
        la2.setCollaterals(new String[]{"Vehicle"});

        c1.addAccount(sa1);
        c1.addAccount(sa2);
        c1.addAccount(la1);
        c2.addAccount(sa3);
        c2.addAccount(la2);
        c3.addAccount(sa4);
        c3.addAccount(la3);

        allAccounts.add(sa1);
        allAccounts.add(sa2);
        allAccounts.add(la1);
        allAccounts.add(sa3);
        allAccounts.add(la2);
        allAccounts.add(sa4);
        allAccounts.add(la3);

        System.out.println("\n  +===============================================+");
        System.out.println("  |        Welcome to NexBank OOP System          |");
        System.out.println("  +===============================================+");

        Scanner sc = new Scanner(System.in);
        mainMenu(allAccounts, customers, sc);
        sc.close();
    }
}
