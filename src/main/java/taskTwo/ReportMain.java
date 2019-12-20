package taskTwo;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ReportMain {
    public static void main(String[] args) throws IOException {

        // Чтение входных данных
        List<String> listOfFiles = Arrays.asList(args);
        listOfFiles = listOfFiles.subList(2, listOfFiles.size());

        // Запуск генерации отчетов
        ReportGenerator reportGenerator = new ReportGenerator(args[0], args[1], listOfFiles);
        reportGenerator.generateAllReports();

    }
}
