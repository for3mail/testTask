package common;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


/*Отвечает за хранение параметров случайной транзакции*/
public  class TransactionInputParameters {
    private LocalDate startDate;
    private int DateRange;
    private final BigDecimal MAX;
    private final BigDecimal MIN;
    private final BigDecimal RANGE;
    private List<String> offices;

    // Создание параметров случайной транзакции
    public TransactionInputParameters(LocalDate startDate, int dateRange, BigDecimal MIN, BigDecimal MAX, List<String> offices) {
        this.startDate = startDate;
        this.DateRange = dateRange;
        this.MAX = MAX;
        this.MIN = MIN;
        RANGE = MAX.subtract(MIN);
        this.offices = offices;
    }

    LocalDate getStartDate() {
        return startDate;
    }

    int getDateRange() {
        return DateRange;
    }

    public BigDecimal getMAX() {
        return MAX;
    }

    BigDecimal getMIN() {
        return MIN;
    }

    BigDecimal getRANGE() {
        return RANGE;
    }

    List<String> getOffices() {
        return offices;
    }
}



