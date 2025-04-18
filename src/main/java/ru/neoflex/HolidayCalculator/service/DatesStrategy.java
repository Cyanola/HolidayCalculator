package ru.neoflex.HolidayCalculator.service;

import ru.neoflex.HolidayCalculator.StrategyInterface.HolidayStrategy;
import ru.neoflex.HolidayCalculator.model.HolidayData;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DatesStrategy implements HolidayStrategy {
    @Override
    public int calculatePaidDays(HolidayData request) {
        int paidDays = 0;
        if (request.getVacationDates() != null) {
            for (String dateStr : request.getVacationDates()) {
                try {
                    LocalDate date = LocalDate.parse(dateStr);
                    if (!JsonLoader.isWeekendOrHoliday(date)) {
                        paidDays++;
                    }
                } catch (DateTimeParseException e) {
       System.out.println(e.getMessage());
                }
            }
        }
        return paidDays;
    }
}