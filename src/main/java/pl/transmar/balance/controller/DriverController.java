package pl.transmar.balance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.transmar.balance.model.Driver;
import pl.transmar.balance.service.DriverService;

@Controller
public class DriverController {

    private DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @RequestMapping("/drivers")
    public String drivers(Model model) {
        model.addAttribute("driverList", driverService.getAllDrivers());
        return "drivers";
    }

    @RequestMapping("/add-driver")
    public ModelAndView addDriver() {
        return new ModelAndView("add-driver", "driver", new Driver());
    }

    @RequestMapping("/save-driver")
    public ModelAndView saveDriver(Driver driver) {
        driverService.addDriver(driver);
        return new ModelAndView("redirect:/drivers");
    }

    @RequestMapping(value = "/drivers/delete/{id}", method = RequestMethod.GET)
    public String deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return "redirect:/drivers";
    }

    @RequestMapping(value = "drivers/edit/{id}", method = RequestMethod.POST)
    public String updateDriver(@PathVariable Long id, Driver driver) {
        driverService.updateDriver(id, driver);
        return "redirect:/drivers";
    }

    @RequestMapping("/edit-driver/{id}")
    public String editDriver(@PathVariable Long id, Model model) {
        model.addAttribute("driver", driverService.getDriverById(id));
        return "edit-driver";
    }
}
