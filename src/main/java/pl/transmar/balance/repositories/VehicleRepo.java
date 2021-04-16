package pl.transmar.balance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.transmar.balance.model.Vehicle;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, Long> {
}
