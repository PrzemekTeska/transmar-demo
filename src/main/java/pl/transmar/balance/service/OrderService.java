package pl.transmar.balance.service;

import pl.transmar.balance.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    Order getOrderById(long id);

    Order addOrder(Order order);

    void deleteOrder(long id);

    boolean updateOrder(long id, Order order);

}
