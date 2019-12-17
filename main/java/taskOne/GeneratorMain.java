package taskOne;

import common.TransactionInputParameters;
import java.math.BigDecimal;
import java.time.LocalDate;
import static java.time.LocalDate.now;

public class GeneratorMain {
    public static void main(String[] args) {

        // Определение входных условий задачи
        BigDecimal MIN = new BigDecimal("10000.12");
        BigDecimal MAX = new BigDecimal("100000.50");
        LocalDate startDate = LocalDate.of(now().getYear()-1, 1, 1);
        int dateRange = (LocalDate.now().getYear()%4 ==1)? 366:365;

        // создание файлов со случайными транзакциями
        FilesGenerator filesGenerator = new FilesGenerator(args);
        TransactionInputParameters transactionInputParameters = new TransactionInputParameters(startDate, dateRange, MIN, MAX, filesGenerator.parseOfficesList());
        filesGenerator.generateAllFiles(transactionInputParameters);

    }
}
