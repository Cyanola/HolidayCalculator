package ru.neoflex.HolidayCalculator.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class HolidayData {
    private double averageSalary;
    private int vacationDays;
    private List<String> vacationDates;
    private double vacationPay;

    public int getPaidDays() {
        return paidDays;
    }

    public void setPaidDays(int paidDays) {
        this.paidDays = paidDays;
    }

    private int paidDays;

    public double getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(double averageSalary) {
        this.averageSalary = averageSalary;
    }

    public int getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }

    public List<String > getVacationDates() {
        return vacationDates;
    }

    public void setVacationDates(List<String> vacationDates) {
        this.vacationDates = vacationDates;
    }

    public double getVacationPay() {
        return vacationPay;
    }

    public void setVacationPay(double vacationPay) {
        this.vacationPay = vacationPay;
    }
}
