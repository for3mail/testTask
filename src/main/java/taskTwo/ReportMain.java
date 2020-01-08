package taskTwo;

import common.InputValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReportMain {
    public static void main(String[] args) {

        //Заглушка для ввода из командной строки
/*        args = new String[12];
        args [0] = "report_dates.txt";
        args [1] = "report_offices.txt";
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


        // Чтение входных данных
        List<String> list = Arrays.asList(args);
        List<String> listOfFiles = list.subList(2, list.size());

        // Валидация входных параметров
        InputValidator inputValidator = new InputValidator(args);
        int minSize =3;
        if (args.length < minSize) inputValidator.requestNewInput("Мало входных данных");
        int [] temp1 = new int[args.length];
        for (int i = 0; i < temp1.length; i++) temp1[i]=i;
        inputValidator.validate(
                minSize,
                Arrays.copyOfRange(temp1, 0, 2),
                Arrays.copyOfRange(temp1,2, args.length),
                new int [0]);

        // Запуск генерации отчетов
        ReportGenerator reportGenerator = new ReportGenerator(args[0], args[1], listOfFiles);
        reportGenerator.generateAllReports();

    }
}
