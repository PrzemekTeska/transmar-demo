package pl.transmar.balance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.transmar.balance.model.ProfitLoss;

import java.time.YearMonth;
import java.util.List;

@Repository
public interface ProfitLossRepo extends JpaRepository<ProfitLoss, Long> {

    ProfitLoss findByDateAndVehicle_Id(YearMonth date, long vehicle_id);

    List<ProfitLoss> findAllByDate(YearMonth date);

    @Query("SELECT SUM(pl.amount) from ProfitLoss pl WHERE pl.date = ?1")
    double findAmountInMonth(YearMonth month);

    @Query("SELECT SUM(pl.profit) from ProfitLoss pl WHERE pl.date = ?1")
    double findProfitInMonth(YearMonth month);

    @Query("SELECT SUM(pl.loss) from ProfitLoss pl WHERE pl.date = ?1")
    double findLossInMonth(YearMonth month);
}
