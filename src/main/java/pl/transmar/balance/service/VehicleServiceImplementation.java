package pl.transmar.balance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.transmar.balance.model.Vehicle;
import pl.transmar.balance.repositories.VehicleRepo;

import java.util.List;

@Service
public class VehicleServiceImplementation implements VehicleService {

    private VehicleRepo vehicleRepo;

    @Autowired
    public VehicleServiceImplementation(VehicleRepo vehicleRepo) {
        this.vehicleRepo = vehicleRepo;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepo.findAll();
    }

    @Override
    public Vehicle addVehicle(Vehicle vehicle) {
        vehicle.setMake(vehicle.getMake().toUpperCase().replaceAll("\\s+", ""));
        vehicle.setNumber(vehicle.getNumber().toUpperCase().replaceAll("\\s+", ""));
        vehicle.setVin(vehicle.getVin().toUpperCase().replaceAll("\\s+", ""));
        vehicleRepo.save(vehicle);
        return vehicle;
    }

    @Override
    public Vehicle updateVehicle(long id, Vehicle newVehicle) {
        Vehicle vehicle = vehicleRepo.findById(id).orElse(null);
        if(vehicle != null) {
            vehicle.setMake(newVehicle.getMake().toUpperCase().replaceAll("\\s+", ""));
            vehicle.setVin(newVehicle.getVin().toUpperCase().replaceAll("\\s+", ""));
            vehicle.setNumber(newVehicle.getNumber().toUpperCase().replaceAll("\\s+", ""));
            vehicleRepo.save(vehicle);
        }
        return vehicle;
    }

    @Override
    public void deleteVehicle(long id) {

        vehicleRepo.deleteById(id);

    }

    @Override
    public Vehicle getVehicleById(long id) {
        return vehicleRepo.findById(id).orElse(null);
    }
}
