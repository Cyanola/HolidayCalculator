package ru.neoflex.HolidayCalculator.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class JsonLoader {

    private static final Set<LocalDate> HOLIDAYS = new HashSet<>();

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = JsonLoader.class.getClassLoader().getResourceAsStream("holidays.json");
            if (is != null) {
                Set<String> holidayStrings = mapper.readValue(is, new TypeReference<>() {});
                for (String dateStr : holidayStrings) {
                    HOLIDAYS.add(LocalDate.parse(dateStr));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Невозможно загрузить holidays.json", e);
        }
    }

    public static boolean isWeekendOrHoliday(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                HOLIDAYS.contains(date);
    }
}
