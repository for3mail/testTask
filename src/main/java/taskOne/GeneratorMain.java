package taskOne;

import common.TransactionInputParameters;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.time.LocalDate.now;

public class GeneratorMain {
    public static void main(String[] args) {
        // заглушка на вход
        Scanner scanner3 = new Scanner(System.in);
        System.out.println("введите данные");
        String test = scanner3.next();
        List<String> argsList= new ArrayList<>();
        for (String argument : test.split(" ")) {
            argsList.add(argument);
        }
        String [] args2 = new String[argsList.size()];

        //args2 = argsList.toArray((new String[argsList.size()]));
        System.out.println("args2.length " + args2.length);


        // Определение входных условий задачи
        BigDecimal MIN = new BigDecimal("10000.12");
        BigDecimal MAX = new BigDecimal("100000.50");
        LocalDate startDate = LocalDate.of(now().getYear()-1, 1, 1);
        LocalDate current1January = LocalDate.of(now().getYear(), 1, 1);
        System.out.println("startDate " + startDate);
        System.out.println("current1January " + current1January);


        Period period = Period.between(startDate, current1January);
        int dateRange = period.getMonths();
        System.out.println("Period" + Period.between(startDate, LocalDate.of(now().getYear(), 1, 1)));
        System.out.println("date range:" + dateRange);

        // Проверка параметров запуска
        Scanner scanner = new Scanner(args[1]);
        File f = new File(args[0]);
        Scanner scanner1;
        while (args.length < 3 || !scanner.hasNextInt() || !f.exists()){
            scanner1 = new Scanner(System.in);
            System.out.println("Ошибка параметров запуска. Пожалуйста повторите ввод всех параметров");
            int temp=0;
            for (String argument : scanner.next().split(" ")) {
                args[temp] = argument;
                temp++;
            }
            scanner = new Scanner(args[1]);
            f = new File(args[0]);
        }

        // создание файлов со случайными транзакциями
        FilesGenerator filesGenerator = new FilesGenerator(args);
        TransactionInputParameters transactionInputParameters = new TransactionInputParameters(startDate, dateRange, MIN, MAX, filesGenerator.parseOfficesList());
        filesGenerator.generateAllFiles(transactionInputParameters);

    }
}
