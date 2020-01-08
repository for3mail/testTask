package taskTwo;

import common.Transaction;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

/*Создает отчеты*/
class ReportGenerator {

    private SortedMap <LocalDate, BigDecimal> datesAnalyzer;
    private ConcurrentHashMap <String, BigDecimal> salesPointAnalyzer;
    private LinkedHashMap <String, BigDecimal> sortedSalesPointsData;
    private String [] currentRecords;
    private List<String> listOfFiles;
    private String datesReportFileName;
    private String salesPointReportFileName;
    private Transaction transaction;

    ReportGenerator(String datesReportFileName, String salesPointReportFileName, List<String> listOfFiles) {
        transaction = new Transaction();
        this.listOfFiles = listOfFiles;
        this.datesReportFileName = datesReportFileName;
        this.salesPointReportFileName = salesPointReportFileName;
        currentRecords = new String[5];
        datesAnalyzer = Collections.synchronizedSortedMap(new TreeMap<LocalDate, BigDecimal>());
        salesPointAnalyzer = new ConcurrentHashMap<String, BigDecimal>();
    }

    // 1. Обрабатывает поочередно все исходные файлы и сохраняет результат в HashMap
    void generateAllReports(){
        for (String o:listOfFiles) analyzeOneDataFile(o);
        sortSalesPointsReport();
        writeReportToFile(datesAnalyzer, datesReportFileName);
        writeReportToFile(sortedSalesPointsData, salesPointReportFileName);
    }

    // 2. Читает и обрабатывает один файл с транзакциями
    private void analyzeOneDataFile(String fileName) {
        System.out.println(fileName);
        Consumer<String> streamedAnalyzer = x -> {
            try {
                currentRecords = x.split(" ",5);
                transaction.setLocalDate(LocalDate.parse(currentRecords[0]));
                transaction.setOffice(currentRecords[2]);
                transaction.setTransactionAmount(new BigDecimal(currentRecords[4]));
                analyzeTransaction(transaction);
            } catch (NumberFormatException e) {
                System.out.println("строка " + x + " ошибка размера транзакции");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("строка " + x + " недопустимый формат строки");
            } catch (DateTimeParseException e){
                System.out.println("строка " + x + " недопустимый формат даты");
            }
        };

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            Stream<String> streamOfStrings = bufferedReader.lines();
            streamOfStrings.forEach(s -> streamedAnalyzer.accept(s));
        } catch (IOException e) {
            System.out.println("невозможно прочитать входной файл");
            e.printStackTrace();
        }
    }

    // 3. Добавляет в сумму одну транзакцию
    private void analyzeTransaction(Transaction transaction){
        LocalDate localDate = transaction.getLocalDate();
        String salesPoint = transaction.getOffice();
        BigDecimal amount = transaction.getTransactionAmount();
        datesAnalyzer.merge(localDate, amount, (x,y) -> amount.add((datesAnalyzer.get(localDate))));
        salesPointAnalyzer.merge(salesPoint, amount, (x,y) -> amount.add((salesPointAnalyzer.get(salesPoint))));
    }

    // B.2 сортирует продажи по точкам продаж по убыванию суммы
    private void sortSalesPointsReport(){
        TreeMap <BigDecimal, String> tempTree = new TreeMap<BigDecimal, String>(Collections.reverseOrder());
        sortedSalesPointsData = new LinkedHashMap<String, BigDecimal>
                ();
        salesPointAnalyzer.forEach((a,b) -> tempTree.put(b,a));
        tempTree.forEach((a,b) -> sortedSalesPointsData.put(b,a));
    }

    // B.3 записывает полученный отчет в конечный файл
    private void writeReportToFile(Map reportName, String fileName){
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            reportName.forEach((a, b) -> {
                try {
                    bufferedWriter.write(a + " " + b + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e){
            System.out.println("Невозможно сохранить отчет" + fileName);
            e.printStackTrace();
        }
    }
}
