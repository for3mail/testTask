package taskOne;

import common.InputValidator;
import common.TransactionInputParameters;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static java.time.LocalDate.now;

public class GeneratorMain {
    public static void main(String[] args) {

        //Заглушка для ввода из командной строки
/*        args = new String[12];
        args [0]= "locs.txt";
        args [1] = "5000000";
        args [2] = "f1";
        args [3] = "f2";
        args [4] = "f3";
        args [5] = "f4";
        args [6] = "f5";
        args [7] = "f6";
        args [8] = "f7";
        args [9] = "f8";
        args [10] = "f9";
        args [11] = "f10";*/


        // Валидация входных параметров
        InputValidator inputValidator = new InputValidator(args);
        int minSize =3;
        if (args.length < minSize) inputValidator.requestNewInput("Мало входных данных");
        int [] temp1 = new int[args.length];
        for (int i = 0; i < temp1.length; i++) temp1[i]=i;
        inputValidator.validate(
                minSize,
                Arrays.copyOfRange(temp1, 2, args.length),
                Arrays.copyOfRange(temp1,0,1),
                Arrays.copyOfRange(temp1,1,2));

        // Определение исходных условий задачи
        BigDecimal MIN = new BigDecimal("10000.12");
        BigDecimal MAX = new BigDecimal("100000.50");
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear()-1,01,01);
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(),01,01);
        int dateRange = (int)(endDate.toEpochDay() - startDate.toEpochDay());

        // создание файлов со случайными транзакциями
        FilesGenerator filesGenerator = new FilesGenerator(args);
        TransactionInputParameters transactionInputParameters = new TransactionInputParameters(startDate, dateRange, MIN, MAX, filesGenerator.parseOfficesList());
        filesGenerator.generateAllFiles(transactionInputParameters);

        /*FilesGeneratorExperimental filesGeneratorExperimental = new FilesGeneratorExperimental();
        filesGeneratorExperimental.genItAll(transactionInputParameters);*/

    }
}
