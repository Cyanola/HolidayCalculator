package ru.neoflex.HolidayCalculator.service;
import org.springframework.stereotype.Service;
import ru.neoflex.HolidayCalculator.StrategyInterface.HolidayStrategy;
import ru.neoflex.HolidayCalculator.model.HolidayData;
@Service
public class HolidayService {
    private static final double AVERAGE_DAYS_IN_MONTH = 29.3;

    public HolidayData calculate(HolidayData request) {
        HolidayStrategy strategy;

        if (request.getVacationDates() != null && !request.getVacationDates().isEmpty()) {
            strategy = new DatesStrategy();
        } else {
            strategy = new CountDaysStrategy();
        }

        int paidDays = strategy.calculatePaidDays(request);
        double dailySalary = request.getAverageSalary() / AVERAGE_DAYS_IN_MONTH;
        double vacationPay = dailySalary * paidDays;

        HolidayData result = new HolidayData();
        result.setAverageSalary(request.getAverageSalary());
        result.setVacationDays(request.getVacationDays());
        result.setPaidDays(paidDays);
        result.setVacationPay(vacationPay);

        return result;
    }
}
