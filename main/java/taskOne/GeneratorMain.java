package taskOne;

import common.TransactionInputParameters;
import java.math.BigDecimal;
import java.time.LocalDate;
import static java.time.LocalDate.now;

public class GeneratorMain {
    public static void main(String[] args) {

        //заглушка консоли
        args = new String[6];
        args [0] = "offices.txt";
        args [1] = "1000";
        args [2] = "f1";
        args [3] = "f2";
        args [4] = "f3";
        args [5] = "f4";

        // Определение входных условий задачи
        BigDecimal MIN = new BigDecimal("10000.12");
        BigDecimal MAX = new BigDecimal("100000.50");
        LocalDate startDate = LocalDate.of(now().getYear()-1, 1, 1);
        int dateRange = (LocalDate.now().getYear()%4 ==1)? 366:365;

        // Сделать чекер ввода из консоли
        FilesGenerator filesGenerator = new FilesGenerator(args);
        TransactionInputParameters transactionInputParameters = new TransactionInputParameters(startDate, dateRange, MIN, MAX, filesGenerator.parseOfficesList());
        filesGenerator.generateAllFiles(transactionInputParameters);

    }
}
