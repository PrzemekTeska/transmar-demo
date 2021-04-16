package pl.transmar.balance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.transmar.balance.model.Driver;
import pl.transmar.balance.repositories.DriverRepo;

import java.util.List;

@Service
public class DriverServiceImplementation implements DriverService {

    private DriverRepo driverRepo;

    @Autowired
    public DriverServiceImplementation(DriverRepo driverRepo) {
        this.driverRepo = driverRepo;
    }

    @Override
    public List<Driver> getAllDrivers() {
        return driverRepo.findAll();
    }

    @Override
    public Driver addDriver(Driver driver) {
        driver.setFirstName(driver.getFirstName().toUpperCase());
        driver.setLastName(driver.getLastName().toUpperCase());
        driverRepo.save(driver);
        return driver;
    }

    @Override
    public Driver updateDriver(long id, Driver newDriver) {
        Driver driver = driverRepo.findById(id).orElse(null);
        if (driver != null) {
            driver.setFirstName(newDriver.getFirstName().toUpperCase());
            driver.setLastName(newDriver.getLastName().toUpperCase());
            driverRepo.save(driver);
        }
        return driver;
    }

    @Override
    public void deleteDriver(long id) {
        driverRepo.deleteById(id);
    }

    @Override
    public Driver getDriverById(long id) {
        return driverRepo.findById(id).orElse(null);
    }
}
