package taskTwo;

import common.Transaction;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

/*Создает отчеты*/
public class ReportGenerator {

    private TreeMap datesAnalyzer;
    private HashMap salesPointAnalyzer;
    private LinkedHashMap sortedSalesPointsData;
    private String [] currentRecords;
    private List listOfFiles;
    private String datesReportFileName;
    private String salesPointReportFileName;
    private RandomAccessFile stream;
    private FileChannel channel;
    private ByteBuffer buffer;
    private byte strBytes [];
    private int temp;
    private String tempString;
    private TreeMap tempTree;
    private Transaction transaction;

    public interface Consumer<T> {
        void accept(T t);
    }

    public ReportGenerator(String datesReportFileName, String salesPointReportFileName, List listOfFiles) {
        transaction = new Transaction();
        this.listOfFiles = listOfFiles;
        this.datesReportFileName = datesReportFileName;
        this.salesPointReportFileName = salesPointReportFileName;
        currentRecords = new String[5];
        datesAnalyzer = new TreeMap<LocalDate, BigDecimal>();
        salesPointAnalyzer = new HashMap<String, BigDecimal>();
    }

    // Обрабатывает поочередно все исходные файлы и сохраняет результат в HashMap
    void generateAllReports(){
        for (int i = 0; i < listOfFiles.size(); i++) {
            analyzeOneDataFile(listOfFiles.get(i).toString());
        }
        sortSalesPointsReport();
        writeReportToFile(datesAnalyzer, datesReportFileName);
        writeReportToFile(sortedSalesPointsData, salesPointReportFileName);
    }

    // Добавляет в сумму одну транзакцию
    private void analyzeTransaction(Transaction transaction){
        LocalDate localDate = transaction.getLocalDate();
        String salesPoint = transaction.getOffice();
        BigDecimal amount = transaction.getTransactionAmount();
        if ((datesAnalyzer.get(localDate))==null) datesAnalyzer.put(localDate, new BigDecimal("0"));
        if ((salesPointAnalyzer.get(salesPoint))==null) salesPointAnalyzer.put(salesPoint, new BigDecimal("0"));
        datesAnalyzer.put(localDate, amount.add((BigDecimal)(datesAnalyzer.get(localDate))));
        salesPointAnalyzer.put(salesPoint, amount.add((BigDecimal)(salesPointAnalyzer.get(salesPoint))));
    }

    // записывает полученный отчет в конечный файл
    private void writeReportToFile(Map analyzerName, String fileName){
        try {
            stream = new RandomAccessFile(fileName, "rw");
            channel = stream.getChannel();
            Iterator hmIterator = analyzerName.entrySet().iterator();
            while (hmIterator.hasNext()) {
                Map.Entry mapElement = (Map.Entry)hmIterator.next();
                tempString = mapElement.getKey() + " " + mapElement.getValue() + "\n";
                strBytes = tempString.getBytes();
                buffer = ByteBuffer.allocate(strBytes.length);
                buffer.put(strBytes);
                buffer.flip();
                channel.write(buffer);
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Читает и обрабатывает один файл с транзакциями
    private void analyzeOneDataFile(String fileName) {
        Consumer<String> streamedAnalyzer = x -> {
            temp = 0;
            for (String currentTransaction : x.split(" ")) {
                currentRecords[temp] = currentTransaction;
                temp++;
            }
            transaction.setLocalDate(LocalDate.parse(currentRecords[0]));
            transaction.setOffice(currentRecords[2]);
            transaction.setTransactionAmount(new BigDecimal(currentRecords[4]));
            analyzeTransaction(transaction);
        };
        try {
            Path path = Paths.get(fileName);
            Stream<String> streamOfStrings = Files.lines(path);
            streamOfStrings.forEach(s -> streamedAnalyzer.accept(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // сортирует продажи по точкам продаж по убыванию суммы
    void sortSalesPointsReport(){
        tempTree = new TreeMap<BigDecimal, String>(Collections.reverseOrder());
        sortedSalesPointsData = new LinkedHashMap<String, BigDecimal>();

        Iterator hmIterator = salesPointAnalyzer.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            tempTree.put(mapElement.getValue(), mapElement.getKey());
        }
        Iterator hmIterator2 = tempTree.entrySet().iterator();
        while (hmIterator2.hasNext()) {
            Map.Entry mapElement2 = (Map.Entry)hmIterator2.next();
            sortedSalesPointsData.put(mapElement2.getValue(), mapElement2.getKey());
        }
    }
}
