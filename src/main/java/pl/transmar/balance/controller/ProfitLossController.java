package pl.transmar.balance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.transmar.balance.service.ProfitLossService;

import java.time.YearMonth;

@Controller
public class ProfitLossController {

    private ProfitLossService profitLossService;

    @Autowired
    public ProfitLossController(ProfitLossService profitLossService) {
        this.profitLossService = profitLossService;
    }

    @RequestMapping("/profit-loss")
    public String profitLoss(Model model) {
        model.addAttribute("profitLossList", profitLossService.findAllProfitLoss());
        model.addAttribute("currentYear", YearMonth.now().getYear());
        return "profit-loss";
    }
}
