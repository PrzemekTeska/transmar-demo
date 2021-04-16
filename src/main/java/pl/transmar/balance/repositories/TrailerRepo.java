package pl.transmar.balance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.transmar.balance.model.Trailer;

@Repository
public interface TrailerRepo extends JpaRepository<Trailer, Long> {
}
