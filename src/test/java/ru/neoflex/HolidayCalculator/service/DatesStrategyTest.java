package ru.neoflex.HolidayCalculator.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.neoflex.HolidayCalculator.StrategyInterface.HolidayStrategy;
import ru.neoflex.HolidayCalculator.model.HolidayData;

import java.util.List;

public class DatesStrategyTest {
    @Test
    void shouldCountOnlyWeekdays() {
        HolidayData request = new HolidayData();
        request.setVacationDates(List.of(
                "2025-05-05", // понедельник
                "2025-05-06", // вторник
                "2025-05-07", // среда
                "2025-05-08", // четверг - выходной
                "2025-05-09", // праздник (Победа)
                "2025-05-10", // суббота
                "2025-05-11"  // воскресенье
        ));

        HolidayStrategy strategy = new DatesStrategy();
        int result = strategy.calculatePaidDays(request);

        // считаем только 5, 6, 7 мая = 3 рабочих дня
        Assertions.assertEquals(3, result);
    }
}
