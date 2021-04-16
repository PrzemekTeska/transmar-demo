package pl.transmar.balance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.transmar.balance.model.ProfitLoss;
import pl.transmar.balance.model.Vehicle;
import pl.transmar.balance.repositories.ProfitLossRepo;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProfitLossServiceImplementation implements ProfitLossService {


    private ProfitLossRepo profitLossRepo;

    @Autowired
    public ProfitLossServiceImplementation(ProfitLossRepo profitLossRepo) {
        this.profitLossRepo = profitLossRepo;
    }

    @Override
    public void updateProfit(YearMonth date, double profit, long vehicleId) {
        ProfitLoss profitLoss = profitLossRepo.findByDateAndVehicle_Id(date, vehicleId);
        profitLoss.setProfit(profitLoss.getProfit() + profit);
        if (profitLoss.getProfit() == 0 && profitLoss.getLoss() == 0) {
            profitLossRepo.deleteById(profitLoss.getId());
            return;
        }
        profitLoss.setAmount();
        profitLossRepo.save(profitLoss);
    }

    @Override
    public void updateProfitAfterEditingOrder(YearMonth date, double oldProfit, double newProfit, long vehicleId) {
        ProfitLoss profitLoss = profitLossRepo.findByDateAndVehicle_Id(date, vehicleId);
        profitLoss.setProfit(profitLoss.getProfit() - oldProfit + newProfit);
        if (profitLoss.getProfit() == 0 && profitLoss.getLoss() == 0) {
            profitLossRepo.deleteById(profitLoss.getId());
            return;
        }
        profitLoss.setAmount();
        profitLossRepo.save(profitLoss);
    }

    @Override
    public void updateProfitAfterDeletingOrder(YearMonth date, double profit, long vehicleId) {
        ProfitLoss profitLoss = profitLossRepo.findByDateAndVehicle_Id(date, vehicleId);
        profitLoss.setProfit(profitLoss.getProfit() - profit);
        if (profitLoss.getProfit() == 0 && profitLoss.getLoss() == 0) {
            profitLossRepo.deleteById(profitLoss.getId());
            return;
        }
        profitLoss.setAmount();
        profitLossRepo.save(profitLoss);
    }

    @Override
    public void updateLoss(YearMonth date, double loss, long vehicleId) {
        ProfitLoss profitLoss = profitLossRepo.findByDateAndVehicle_Id(date, vehicleId);
        profitLoss.setLoss(loss);
        if (profitLoss.getProfit() == 0 && profitLoss.getLoss() == 0) {
            profitLossRepo.deleteById(profitLoss.getId());
            return;
        }
        profitLoss.setAmount();
        profitLossRepo.save(profitLoss);
    }

    @Override
    public List<ProfitLoss> findAllProfitLoss() {
        List<ProfitLoss> allProfitLoss = profitLossRepo.findAll();
        List<YearMonth> allMonths = new ArrayList<>();
        for (ProfitLoss profitloss :
                allProfitLoss) {
            allMonths.add(profitloss.getDate());
        }
        List<YearMonth> allMonthsWithoutDuplicates = allMonths.stream().distinct().collect(Collectors.toList());

        for (YearMonth allMonthsWithoutDuplicate : allMonthsWithoutDuplicates) {
            allProfitLoss.add(findAllProfitLossInMonth(allMonthsWithoutDuplicate));
        }
        allProfitLoss.sort(Comparator.comparing(ProfitLoss::getDate).thenComparing(ProfitLoss::getCompareId));
        return allProfitLoss;

    }

    @Override
    public ProfitLoss findAllProfitLossInMonth(YearMonth date) {
        List<ProfitLoss> allByDate = profitLossRepo.findAllByDate(date);
        List<String> allVehicles = new ArrayList<>();
        ProfitLoss profitLossInMonth = new ProfitLoss();
        profitLossInMonth.setDate(allByDate.get(0).getDate());
        for (ProfitLoss profitloss :
                allByDate) {
            profitLossInMonth.setProfit(profitLossInMonth.getProfit() + profitloss.getProfit());
            profitLossInMonth.setLoss(profitLossInMonth.getLoss() + profitloss.getLoss());
            allVehicles.add(profitloss.getVehicle().getNumber());
        }
        profitLossInMonth.setCompareId(1);
        String allVehiclesWithoutDuplicates = allVehicles.stream().distinct().collect(Collectors.joining(" + "));

        Vehicle vehicle = new Vehicle();
        vehicle.setNumber(allVehiclesWithoutDuplicates);
        profitLossInMonth.setVehicle(vehicle);
        profitLossInMonth.setAmount();
        return profitLossInMonth;
    }

    @Override
    public Map<String, Double> findAmountInMonth(int year) {
        double[] amounts = new double[12];
        for (int i =1; i<=12; i++) {
            try {
                amounts[i - 1] = profitLossRepo.findAmountInMonth(YearMonth.of(year, i));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        Map<String, Double> map = new LinkedHashMap<>();
        map.put("Styczeń", amounts[0]);
        map.put("Luty", amounts[1]);
        map.put("Marzec", amounts[2]);
        map.put("Kwiecień", amounts[3]);
        map.put("Maj", amounts[4]);
        map.put("Czerwiec", amounts[5]);
        map.put("Lipiec", amounts[6]);
        map.put("Sierpień", amounts[7]);
        map.put("Wrzesień", amounts[8]);
        map.put("Październik", amounts[9]);
        map.put("Listopad", amounts[10]);
        map.put("Grudzień", amounts[11]);

        return map;
    }

    @Override
    public double findProfitInYear(int year) {
        double profit = 0;
        for (int i =1; i<=12; i++) {
            try {
                profit +=  profitLossRepo.findProfitInMonth(YearMonth.of(year, i));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return profit;
    }

    @Override
    public double findLossInYear(int year) {
        double loss = 0;
        for (int i =1; i<=12; i++) {
            try {
                loss +=  profitLossRepo.findLossInMonth(YearMonth.of(year, i));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return loss;
    }
}
