package ru.neoflex.HolidayCalculator.service;

import ru.neoflex.HolidayCalculator.StrategyInterface.HolidayStrategy;
import ru.neoflex.HolidayCalculator.model.HolidayData;

public class CountDaysStrategy  implements HolidayStrategy {
    @Override
    public int calculatePaidDays(HolidayData request) {
        return request.getVacationDays();
    }
}