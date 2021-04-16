package pl.transmar.balance.model;

import javax.persistence.*;
import java.time.YearMonth;

@Entity
public class ProfitLoss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public ProfitLoss() {
    }

    private YearMonth date;
    private double profit;
    private double loss;
    private double amount;
    @OneToOne
    private Vehicle vehicle;

    private int compareId;

    public int getCompareId() {
        return compareId;
    }

    public void setCompareId(int compareId) {
        this.compareId = compareId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getLoss() {
        return loss;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public YearMonth getDate() {
        return date;
    }

    public void setDate(YearMonth date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount() {
        amount = profit-loss;
    }
}
