package ru.neoflex.HolidayCalculator.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.HolidayCalculator.model.HolidayData;
import ru.neoflex.HolidayCalculator.service.HolidayService;

import java.util.List;

/**
 // * Контроллер расчета отпускных
 *
 * @version 1.0
 * @author Artemova Olga
 */
@RestController
@RequestMapping("/calculate")
public class HolidayController {
    private final HolidayService service;
    public HolidayController(HolidayService service) {
        this.service = service;
    }
    /**
     @param averageSalary средняя зарплата
     @param vacationDays количество дней отпуска
     @param vacationDates точные дни отпуска
     @return данные об отпускных
     **/
    @GetMapping
    public ResponseEntity<HolidayData> calculateVacation(
            @RequestParam double averageSalary,
            @RequestParam(required = false) Integer vacationDays,
            @RequestParam(required = false) List<String> vacationDates
    ) {
        HolidayData request = new HolidayData();
        request.setAverageSalary(averageSalary);
        request.setVacationDays(vacationDays != null ? vacationDays : 0);
        request.setVacationDates(vacationDates);

        HolidayData result = service.calculate(request);
        return ResponseEntity.ok(result);
    }
    }
