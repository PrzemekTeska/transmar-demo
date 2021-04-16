package pl.transmar.balance.service;

import pl.transmar.balance.model.Expense;

import java.util.List;

public interface ExpenseService {

    List<Expense> getAllExpenses();

    Expense getExpenseById(long id);

    Expense addExpense(Expense expense);

    void deleteExpense(long id);

    boolean updateExpense(long id, Expense expense);

}
