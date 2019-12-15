package taskTwo;

import java.io.BufferedReader;
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

    private String line;
    private int temp;
    private String tempString;
    private TreeMap tempTree;

    public ReportGenerator(String datesReportFileName, String salesPointReportFileName, List listOfFiles) {
        this.listOfFiles = listOfFiles;
        this.datesReportFileName = datesReportFileName;
        this.salesPointReportFileName = salesPointReportFileName;
        currentRecords = new String[5];
        datesAnalyzer = new TreeMap<LocalDate, BigDecimal>();
        salesPointAnalyzer = new HashMap<String, BigDecimal>();
    }

    // Обрабатывает поочередно все исходные файлы и сохраняет результат в HashMap
    public void generateAllReports(){
        for (int i = 0; i < listOfFiles.size(); i++) {
            analyzeOneDataFile(listOfFiles.get(i).toString());
        }

        sortSalesPointsReport();
        writeReportToFile(datesAnalyzer, datesReportFileName);
        writeReportToFile(sortedSalesPointsData, salesPointReportFileName);
    }

    // Добавляет в сумму одну транзакцию
    void analyzeTransaction(LocalDate localDate, String salesPoint, BigDecimal amount){
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
    void analyzeOneDataFile(String fileName){
        try {
            Path path = Paths.get(fileName);
            BufferedReader reader = Files.newBufferedReader(path);
            while((line = reader.readLine()) != null) {
                temp = 0;
                for (String currentTransaction : line.split(" ")) {
                    currentRecords[temp] = currentTransaction;
                    temp++;
                }
                analyzeTransaction(LocalDate.parse(currentRecords[0]), currentRecords[2], new BigDecimal(currentRecords[4]));
            }

        } catch (Exception e){
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

 // Переделать сортировку через stream()
    void sortReport1(){
/*

        Map<BigDecimal,String> sortedNewMap = salesPointAnalyzer.entrySet().stream().sorted((e1,e2)->
                e1.getValue().getLocation().compareTo(e2.getValue().getLocation()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new))


            salesPointAnalyzer.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                .forEach(addToFile());

        Stream<String> stringStream=  salesPointAnalyzer.entrySet().parallelStream()
                .sorted(Map.Entry.comparingByValue());
        stringStream.
        Files.write(Paths.get("/tmp/numbers.txt"),
                (Iterable<String>)stringStream.
                        mapToObj(String::valueOf)::iterator);

        Iterable<String> iterable = stream::iterator;

        Map sortedSalesPointReport =
                .collect(Collectors.toMap(
                Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

*/

    }

}
