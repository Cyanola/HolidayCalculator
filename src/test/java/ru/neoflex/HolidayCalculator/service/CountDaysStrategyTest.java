package ru.neoflex.HolidayCalculator.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.neoflex.HolidayCalculator.StrategyInterface.HolidayStrategy;
import ru.neoflex.HolidayCalculator.model.HolidayData;

public class CountDaysStrategyTest {

    @Test
    void shouldReturnVacationDaysFromRequest() {
        HolidayData request = new HolidayData();
        request.setVacationDays(5);

        HolidayStrategy strategy = new CountDaysStrategy();
        int result = strategy.calculatePaidDays(request);

        Assertions.assertEquals(5, result);
    }
}
