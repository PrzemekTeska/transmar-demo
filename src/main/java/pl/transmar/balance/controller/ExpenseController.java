package pl.transmar.balance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.transmar.balance.model.Expense;
import pl.transmar.balance.service.ExpenseService;
import pl.transmar.balance.service.VehicleService;

@Controller
public class ExpenseController {

    private ExpenseService expenseService;
    private VehicleService vehicleService;

    @Autowired
    public ExpenseController(ExpenseService expenseService, VehicleService vehicleService) {
        this.expenseService = expenseService;
        this.vehicleService = vehicleService;
    }

    @RequestMapping("/expenses")
    public String expenses(Model model) {
        model.addAttribute("expenseList", expenseService.getAllExpenses());
        return "expenses";
    }

    @RequestMapping("/add-expense")
    public ModelAndView addExpense(Model model) {
        model.addAttribute("vehicleList", vehicleService.getAllVehicles());
        return new ModelAndView("add-expense", "expense", new Expense());
    }

    @RequestMapping("/save-expense")
    public ModelAndView saveExpense(Expense expense, RedirectAttributes redirectAttributes) {
        Expense addExpense = expenseService.addExpense(expense);

        if(addExpense == null) {
            redirectAttributes.addFlashAttribute("error", "Koszty na ten samochód i na ten miesiąc już istnieją. Użyj przycisku \"Edytuj\"");
            return new ModelAndView("redirect:/expenses");
        }
        redirectAttributes.addFlashAttribute("success", "Koszty zostały dodane.");
        return new ModelAndView("redirect:/expenses");
    }

    @RequestMapping(value = "/expenses/delete/{id}", method = RequestMethod.GET)
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return "redirect:/expenses";
    }

    @RequestMapping(value = "expenses/edit/{id}", method = RequestMethod.POST)
    public String updateExpense(@PathVariable Long id, Expense expense) {
        expense.setExpenseAmount();
        expenseService.updateExpense(id, expense);
        return "redirect:/expenses";
    }

    @RequestMapping("/edit-expense/{id}")
    public String editOrder(@PathVariable Long id, Model model) {
        model.addAttribute("vehicleList", vehicleService.getAllVehicles());
        model.addAttribute("expense", expenseService.getExpenseById(id));
        return "edit-expense";
    }
}
