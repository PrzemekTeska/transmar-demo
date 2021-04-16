package pl.transmar.balance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.transmar.balance.model.Vehicle;
import pl.transmar.balance.service.VehicleService;

@Controller
public class VehicleController {

    private VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @RequestMapping("/vehicles")
    public String vehicles(Model model) {
        model.addAttribute("vehicleList", vehicleService.getAllVehicles());
        return "vehicles";
    }

    @RequestMapping("/add-vehicle")
    public ModelAndView addVehicle() {
        return new ModelAndView("add-vehicle", "vehicle", new Vehicle());
    }

    @RequestMapping("/save-vehicle")
    public ModelAndView saveVehicle(Vehicle vehicle) {
        vehicleService.addVehicle(vehicle);
        return new ModelAndView("redirect:/vehicles");
    }

    @RequestMapping(value = "/vehicles/delete/{id}", method = RequestMethod.GET)
    public String deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return "redirect:/vehicles";
    }

    @RequestMapping(value = "vehicles/edit/{id}", method = RequestMethod.POST)
    public String updateVehicle(@PathVariable Long id, Vehicle vehicle) {
        vehicleService.updateVehicle(id, vehicle);
        return "redirect:/vehicles";
    }

    @RequestMapping("/edit-vehicle/{id}")
    public String editVehicle(@PathVariable Long id, Model model) {
        model.addAttribute("vehicle", vehicleService.getVehicleById(id));
        return "edit-vehicle";
    }
}
