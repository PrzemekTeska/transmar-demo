package pl.transmar.balance.service;

import pl.transmar.balance.model.ProfitLoss;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface ProfitLossService {

    void updateProfit(YearMonth date, double profit, long vehicleId);

    void updateProfitAfterDeletingOrder(YearMonth date, double profit, long vehicleId);

    void updateProfitAfterEditingOrder(YearMonth date, double oldProfit, double newProfit, long vehicleId);

    void updateLoss(YearMonth date, double loss, long vehicleId);

    List<ProfitLoss> findAllProfitLoss();

    ProfitLoss findAllProfitLossInMonth(YearMonth date);

    Map<String, Double> findAmountInMonth(int year);

    double findProfitInYear(int year);

    double findLossInYear(int year);

}
