package ru.neoflex.HolidayCalculator.StrategyInterface;


import ru.neoflex.HolidayCalculator.model.HolidayData;

public interface HolidayStrategy {
    int calculatePaidDays(HolidayData request);
}
