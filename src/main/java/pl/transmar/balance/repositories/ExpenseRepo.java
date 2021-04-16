package pl.transmar.balance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.transmar.balance.model.Expense;

import java.time.YearMonth;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long> {

    Expense findByExpenseMonthYearAndVehicle_Id(YearMonth expenseMonthYear, long vehicle_id);
}
