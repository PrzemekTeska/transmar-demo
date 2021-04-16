package pl.transmar.balance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.transmar.balance.model.Order;
import pl.transmar.balance.model.ProfitLoss;
import pl.transmar.balance.repositories.OrderRepo;
import pl.transmar.balance.repositories.ProfitLossRepo;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImplementation implements OrderService {

    private OrderRepo orderRepo;
    private ProfitLossRepo profitLossRepo;
    private ProfitLossService profitLossService;

    @Autowired
    public OrderServiceImplementation(OrderRepo orderRepo, ProfitLossRepo profitLossRepo, ProfitLossService profitLossService) {
        this.orderRepo = orderRepo;
        this.profitLossRepo = profitLossRepo;
        this.profitLossService = profitLossService;
//
//        for(int i=0; i<50; i++) {
//            Order order = new Order();
//            order.setOrderNumber("Test " + i);
//            orderRepo.save(order);
//        }
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orderList = orderRepo.findAll();
        orderList.sort(Comparator.comparing(Order::getLoadingDate));
        return orderList;

    }

    @Override
    public Order getOrderById(long id) {
        Optional<Order> order = orderRepo.findById(id);
        return order.orElse(null);
    }

    @Override
    public Order addOrder(Order order) {
        Order byOrderNumber = orderRepo.findByOrderNumber(order.getOrderNumber());
        if (byOrderNumber == null) {
            order.setOrderAmount();
            orderRepo.save(order);
            if (profitLossRepo.findByDateAndVehicle_Id(YearMonth.from(order.getUnloadingDate()), order.getVehicle().getId()) == null) {
                ProfitLoss profitLoss = new ProfitLoss();
                profitLoss.setProfit(order.getOrderAmount());
                profitLoss.setLoss(0);
                profitLoss.setVehicle(order.getVehicle());
                profitLoss.setAmount();
                profitLoss.setDate(YearMonth.from(order.getUnloadingDate()));
                profitLoss.setCompareId(0);
                profitLossRepo.save(profitLoss);
            } else {
                profitLossService.updateProfit(YearMonth.from(order.getUnloadingDate()), order.getOrderAmount(), order.getVehicle().getId());
            }
            return order;
        } else return null;
        //jeśli po dodaniu ordera nie ma LossProfit na miesiąc na dany samochód z daty rozładunku to trzeba dodać ProfitLoss z loss 0 i profit 0
    }

    @Override
    public void deleteOrder(long id) {
        Optional<Order> order = orderRepo.findById(id);
        order.ifPresent(value -> profitLossService.updateProfitAfterDeletingOrder(YearMonth.from(value.getUnloadingDate()), value.getOrderAmount(), value.getVehicle().getId()));
        orderRepo.deleteById(id);
    }

    @Override
    public boolean updateOrder(long id, Order order) {
        Optional<Order> byId = orderRepo.findById(id);
        double oldProfit;
        LocalDate oldUnloadingDate;
        if (byId.isPresent()) {
            byId.get().setOrderNumber(order.getOrderNumber());
            byId.get().setVehicle(order.getVehicle());
            byId.get().setTrailer(order.getTrailer());
            byId.get().setDriver(order.getDriver());
            byId.get().setLoadingDate(order.getLoadingDate());
            oldUnloadingDate = byId.get().getUnloadingDate();
            byId.get().setUnloadingDate(order.getUnloadingDate());
            byId.get().setKilometres(order.getKilometres());
            byId.get().setMaut(order.getMaut());
            byId.get().setBonus(order.getBonus());
            byId.get().setRate(order.getRate());
            oldProfit = byId.get().getOrderAmount();
            byId.get().setOrderAmount();
            orderRepo.save(byId.get());
            if (profitLossRepo.findByDateAndVehicle_Id(YearMonth.from(order.getUnloadingDate()), order.getVehicle().getId()) == null) {
                ProfitLoss profitLoss = new ProfitLoss();
                profitLoss.setProfit(order.getOrderAmount());
                profitLoss.setLoss(0);
                profitLoss.setVehicle(order.getVehicle());
                profitLoss.setAmount();
                profitLoss.setDate(YearMonth.from(order.getUnloadingDate()));
                profitLoss.setCompareId(0);
                profitLossRepo.save(profitLoss);
                profitLossService.updateProfitAfterDeletingOrder(YearMonth.from(oldUnloadingDate), oldProfit, order.getVehicle().getId());
            } else {
                profitLossService.updateProfitAfterEditingOrder(YearMonth.from(order.getUnloadingDate()), oldProfit, byId.get().getOrderAmount(), order.getVehicle().getId());
            }
            return true;
        }

        return false;
    }
}
