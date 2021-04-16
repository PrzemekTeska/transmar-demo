package pl.transmar.balance.model;

import javax.persistence.*;
import java.time.YearMonth;

@Entity(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private YearMonth expenseMonthYear;
    @OneToOne
    private Vehicle vehicle;
    private double highwaysExpenses;
    private double fuelExpenses;
    private double driverSalary;
    private double zus;
    private double leasing;
    private double insurance;
    private double otherExpenses;
    private double interest;
    private double incomeTax;

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(double incomeTax) {
        this.incomeTax = incomeTax;
    }

    private double expenseAmount;

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount() {
        expenseAmount = highwaysExpenses + fuelExpenses + driverSalary + zus + leasing + insurance + interest + incomeTax + otherExpenses;
    }

    public Expense() {
    }

    public long getId() {
        return id;
    }

    public YearMonth getExpenseMonthYear() {
        return expenseMonthYear;
    }

    public void setExpenseMonthYear(YearMonth expenseMonthAndYear) {
        this.expenseMonthYear = expenseMonthAndYear;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public double getHighwaysExpenses() {
        return highwaysExpenses;
    }

    public void setHighwaysExpenses(double highwaysExpenses) {
        this.highwaysExpenses = highwaysExpenses;
    }

    public double getFuelExpenses() {
        return fuelExpenses;
    }

    public void setFuelExpenses(double fuelExpenses) {
        this.fuelExpenses = fuelExpenses;
    }

    public double getDriverSalary() {
        return driverSalary;
    }

    public void setDriverSalary(double driverSalary) {
        this.driverSalary = driverSalary;
    }

    public double getZus() {
        return zus;
    }

    public void setZus(double zus) {
        this.zus = zus;
    }

    public double getLeasing() {
        return leasing;
    }

    public void setLeasing(double leasing) {
        this.leasing = leasing;
    }

    public double getInsurance() {
        return insurance;
    }

    public void setInsurance(double insurance) {
        this.insurance = insurance;
    }

    public double getOtherExpenses() {
        return otherExpenses;
    }

    public void setOtherExpenses(double otherExpenses) {
        this.otherExpenses = otherExpenses;
    }
}
