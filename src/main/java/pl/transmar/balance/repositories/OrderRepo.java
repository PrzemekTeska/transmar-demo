package pl.transmar.balance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.transmar.balance.model.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    Order findByOrderNumber(String orderNumber);
}
