package pl.transmar.balance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.transmar.balance.service.ProfitLossService;

@Controller
public class GraphController {

    private ProfitLossService profitLossService;

    @Autowired
    public GraphController(ProfitLossService profitLossService) {
        this.profitLossService = profitLossService;
    }

    @RequestMapping("/graph/{year}")
    String chart(@PathVariable int year, Model model) {


        model.addAttribute("map", profitLossService.findAmountInMonth(year));
        model.addAttribute("year", year);
        model.addAttribute("profit", profitLossService.findProfitInYear(year));
        model.addAttribute("loss", profitLossService.findLossInYear(year));


        return "graph";
    }
}
