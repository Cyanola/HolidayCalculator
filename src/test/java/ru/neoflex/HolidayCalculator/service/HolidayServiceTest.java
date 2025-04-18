package ru.neoflex.HolidayCalculator.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.neoflex.HolidayCalculator.model.HolidayData;

import java.util.List;

public class HolidayServiceTest {

    @Test
    void shouldCalculatePayForFixedDays() {
        HolidayData request = new HolidayData();
        request.setAverageSalary(60000);
        request.setVacationDays(5);

        HolidayService service = new HolidayService();
        HolidayData result = service.calculate(request);

        double expectedDaily = 60000 / 29.3;
        double expectedPay = expectedDaily * 5;

        Assertions.assertEquals(5, result.getPaidDays());
        Assertions.assertEquals(expectedPay, result.getVacationPay(), 0.01);
    }

    @Test
    void shouldCalculatePayForVacationDates() {
        HolidayData request = new HolidayData();
        request.setAverageSalary(60000);
        request.setVacationDates(List.of(
                "2025-05-05", "2025-05-06", "2025-05-07"
        )); // все рабочие дни

        HolidayService calculator = new HolidayService();
        HolidayData result = calculator.calculate(request);

        double expectedDaily = 60000 / 29.3;
        double expectedPay = expectedDaily * 3;

        Assertions.assertEquals(3, result.getPaidDays());
        Assertions.assertEquals(expectedPay, result.getVacationPay(), 0.01);
    }
}
