package pl.transmar.balance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.transmar.balance.model.Expense;
import pl.transmar.balance.model.ProfitLoss;
import pl.transmar.balance.repositories.ExpenseRepo;
import pl.transmar.balance.repositories.ProfitLossRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImplementation implements ExpenseService {

    private ExpenseRepo expenseRepo;
    private ProfitLossRepo profitLossRepo;
    private ProfitLossService profitLossService;

    @Autowired
    public ExpenseServiceImplementation(ExpenseRepo expenseRepo, ProfitLossRepo profitLossRepo, ProfitLossService profitLossService) {
        this.expenseRepo = expenseRepo;
        this.profitLossRepo = profitLossRepo;
        this.profitLossService = profitLossService;
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }

    @Override
    public Expense getExpenseById(long id) {
        Optional<Expense> expense = expenseRepo.findById(id);
        return expense.orElse(null);
    }

    @Override
    public Expense addExpense(Expense expense) {
        Expense byExpenseMonthYear = expenseRepo.findByExpenseMonthYearAndVehicle_Id(expense.getExpenseMonthYear(), expense.getVehicle().getId());
        if (byExpenseMonthYear == null) {
            expense.setExpenseAmount();
            expenseRepo.save(expense);
            if (profitLossRepo.findByDateAndVehicle_Id(expense.getExpenseMonthYear(), expense.getVehicle().getId()) == null) {
                ProfitLoss profitLoss = new ProfitLoss();
                profitLoss.setProfit(0);
                profitLoss.setLoss(expense.getExpenseAmount());
                profitLoss.setVehicle(expense.getVehicle());
                profitLoss.setAmount();
                profitLoss.setDate(expense.getExpenseMonthYear());
                profitLoss.setCompareId(0);
                profitLossRepo.save(profitLoss);
            } else {
                profitLossService.updateLoss(expense.getExpenseMonthYear(), expense.getExpenseAmount(), expense.getVehicle().getId());
            }
            return expense;
        } else return null;
    }

    @Override
    public void deleteExpense(long id) {
        Optional<Expense> expense = expenseRepo.findById(id);
        expense.ifPresent(value -> profitLossService.updateLoss(value.getExpenseMonthYear(), 0, value.getVehicle().getId()));
        expenseRepo.deleteById(id);

    }

    @Override
    public boolean updateExpense(long id, Expense expense) {
        if (expenseRepo.findById(id).isPresent()) {
            Expense byExpenseMonthYear = expenseRepo.findByExpenseMonthYearAndVehicle_Id(expense.getExpenseMonthYear(), expense.getVehicle().getId());
            if (byExpenseMonthYear == null || byExpenseMonthYear.getExpenseMonthYear().equals(expense.getExpenseMonthYear())) {
                expense.setExpenseAmount();
                expenseRepo.save(expense);
                if (profitLossRepo.findByDateAndVehicle_Id(expense.getExpenseMonthYear(), expense.getVehicle().getId()) == null) {
                    ProfitLoss profitLoss = new ProfitLoss();
                    profitLoss.setProfit(0);
                    profitLoss.setLoss(expense.getExpenseAmount());
                    profitLoss.setVehicle(expense.getVehicle());
                    profitLoss.setAmount();
                    profitLoss.setDate(expense.getExpenseMonthYear());
                    profitLoss.setCompareId(0);
                    profitLossRepo.save(profitLoss);
                } else {
                    profitLossService.updateLoss(expense.getExpenseMonthYear(), expense.getExpenseAmount(), expense.getVehicle().getId());
                }
                return true;
            } else return false;
        }
        return false;
    }
}
