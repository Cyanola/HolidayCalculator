# Описание приложение "Калькулятор отпускных"

## Условие задачи
Микросервис на SpringBoot + Java 11 c одним API:
GET "/calculacte"
<p>
Минимальные требования: Приложение принимает твою среднюю зарплату за 12 месяцев и количество дней отпуска - отвечает суммой отпускных, которые придут сотруднику.
Доп. задание: При запросе также можно указать точные дни ухода в отпуск, тогда должен проводиться рассчет отпускных с учётом праздников и выходных.</p>

## Выполнение задания
### Паттерн "Стратегия"
<details>
    <summary><ins><b>Теоретическая справка</b></ins></summary>
    <p>
        <b>Паттерн "Стратегия"</b> — это поведенческий паттерн проектирования, который определяет семейство схожих алгоритмов и помещает каждый из них в собственный класс, после чего алгоритмы можно взаимозаменять прямо во время исполнения программы.
     </p>
<hr/>
    <img src="HolidayCalculator/src/main/resources/Images/img.png" alt="Пример стратегии" />
</details>

> [!IMPORTANT]
> Реализация интерфейса `HolidayStrategy`:

```java
public interface HolidayStrategy {
    int calculatePaidDays(HolidayData request);
}
```
Данные о выходных и праздничных днях хранятся в JSON-файле, который можно дополнять. <br>Эта технология позволяет отделить данные от логики, повышает читаемость и упрощает поддержку (если дни праздников меняются ежегодно).
```json 'holidays.json' <br>
[
  "2025-01-01",
  "2025-01-07",
  "2025-02-23",
  "2025-03-08",
  "2025-05-01",
  "2025-05-09",
  "2025-06-12",
  "2025-11-04",
  "2025-12-31",
  "2025-05-08"
]

```
> [__Содержимое класса контроллера__](HolidayCalculator/src/main/java/ru/neoflex/HolidayCalculator/controller/HolidayController.java) `HolidayController`<br>
> [__Реализация класса стандартного расчета отпускных__](HolidayCalculator/src/main/java/ru/neoflex/HolidayCalculator/service/CountDaysStrategy.java) `CountDaysStrategy`<br>
>  [__Реализация класса расчета отпускных с учетом праздничных и выходных дней__](HolidayCalculator/src/main/java/ru/neoflex/HolidayCalculator/service/DatesStrategy.java) `DatesStrategy`<br>
>  [__Реализация класса-навигатора__](HolidayCalculator/src/main/java/ru/neoflex/HolidayCalculator/service/HolidayService.java) `Класс-навигатор`

## Unit-тесты

Тест на правильный подсчет суммы в случае, когда указано количество дней
```java
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

```
Тест на правильный подсчет суммы в случае, когда введены даты отпуска
```java

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

```

Тест на расчет отпускных с праздниками и выходными:
```java
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
    
    Assertions.assertEquals(3, result);
}
```
Если пользователь указал конкретные даты отпуска, стратегия должна отфильтровать из них:

- праздники (например, 9 мая)

- выходные (суббота и воскресенье)

Оставшиеся — это рабочие дни и только они считаются оплачиваемыми.
