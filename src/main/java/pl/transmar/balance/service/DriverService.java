package pl.transmar.balance.service;

import pl.transmar.balance.model.Driver;

import java.util.List;

public interface DriverService {

    List<Driver> getAllDrivers();

    Driver addDriver(Driver driver);

    Driver updateDriver(long id, Driver newDriver);

    void deleteDriver(long id);

    Driver getDriverById(long id);
}
