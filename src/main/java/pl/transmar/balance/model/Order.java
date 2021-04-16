package pl.transmar.balance.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String orderNumber;
    @OneToOne
    private Vehicle vehicle;
    @OneToOne
    private Trailer trailer;
    @OneToOne
    private Driver driver;
    private @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate loadingDate;
    private @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate unloadingDate;
    private int kilometres;
    private int maut;
    private int bonus;
    private double rate;
    private int orderAmount;

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount() {
        orderAmount = (int) ((rate * kilometres) + maut + bonus);
    }

    private boolean abrechnung;

    public boolean isAbrechnung() {
        return abrechnung;
    }

    public void setAbrechnung(boolean abrechnung) {
        this.abrechnung = abrechnung;
    }

    public Order() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public LocalDate getLoadingDate() {
        return loadingDate;
    }

    public void setLoadingDate(LocalDate loadingDate) {
        this.loadingDate = loadingDate;
    }

    public LocalDate getUnloadingDate() {
        return unloadingDate;
    }

    public void setUnloadingDate(LocalDate unloadingDate) {
        this.unloadingDate = unloadingDate;
    }

    public int getKilometres() {
        return kilometres;
    }

    public void setKilometres(int kilometres) {
        this.kilometres = kilometres;
    }

    public int getMaut() {
        return maut;
    }

    public void setMaut(int maut) {
        this.maut = maut;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
