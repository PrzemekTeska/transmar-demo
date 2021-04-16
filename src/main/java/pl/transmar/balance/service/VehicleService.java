package pl.transmar.balance.service;

import pl.transmar.balance.model.Vehicle;

import java.util.List;

public interface VehicleService {

    List<Vehicle> getAllVehicles();

    Vehicle addVehicle(Vehicle vehicle);

    Vehicle updateVehicle(long id, Vehicle newVehicle);

    void deleteVehicle(long id);

    Vehicle getVehicleById(long id);
}
