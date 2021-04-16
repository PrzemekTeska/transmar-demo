package pl.transmar.balance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.transmar.balance.model.Driver;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Long> {
}
