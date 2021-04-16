package pl.transmar.balance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.transmar.balance.model.Order;
import pl.transmar.balance.service.DriverService;
import pl.transmar.balance.service.OrderService;
import pl.transmar.balance.service.TrailerService;
import pl.transmar.balance.service.VehicleService;

@Controller
public class OrderController {

    private OrderService orderService;
    private VehicleService vehicleService;
    private TrailerService trailerService;
    private DriverService driverService;


    @Autowired
    public OrderController(OrderService orderService, VehicleService vehicleService, TrailerService trailerService, DriverService driverService) {
        this.orderService = orderService;
        this.vehicleService = vehicleService;
        this.trailerService = trailerService;
        this.driverService = driverService;
    }

    @RequestMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orderList", orderService.getAllOrders());
        return "orders";
    }

    @RequestMapping("/add-order")
    public ModelAndView addOrder(Model model) {
        model.addAttribute("vehicleList", vehicleService.getAllVehicles());
        model.addAttribute("driverList", driverService.getAllDrivers());
        model.addAttribute("trailerList", trailerService.getAllTrailers());
        Order order = new Order();
        order.setRate(0.91);
        return new ModelAndView("add-order", "order", order);
    }

    @RequestMapping("/save-order")
    public ModelAndView saveOrder(Order order, RedirectAttributes redirectAttributes) {
        Order addOrder = orderService.addOrder(order);

        if(addOrder == null) {
            redirectAttributes.addFlashAttribute("error", "Zlecenie o podanym numerze już istnieje!");
            return new ModelAndView("redirect:/orders");
        }
        redirectAttributes.addFlashAttribute("success", "Zlecenie zostało dodane.");
        return new ModelAndView("redirect:/orders");
    }

    @RequestMapping(value = "/orders/delete/{id}", method = RequestMethod.GET)
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }

    @RequestMapping(value = "orders/edit/{id}", method = RequestMethod.POST)
    public String updateOrder(@PathVariable Long id, Order order) {
        order.setOrderAmount();
        orderService.updateOrder(id, order);
        return "redirect:/orders";
    }

    @RequestMapping("/edit-order/{id}")
    public String editOrder(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        model.addAttribute("vehicleList", vehicleService.getAllVehicles());
        model.addAttribute("driverList", driverService.getAllDrivers());
        model.addAttribute("trailerList", trailerService.getAllTrailers());
        return "edit-order";
    }
}
