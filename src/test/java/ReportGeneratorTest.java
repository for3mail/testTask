import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import taskTwo.ReportGenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ReportGeneratorTest {
    private ReportGenerator reportGenerator;

    @Test
    void generateAllReportsTest(){
        List <String> list = Arrays.asList("testDir/testData", "testDir/testData2", "testDir/testData3");
        reportGenerator = new ReportGenerator("testDir/DatesReportTest.txt", "testDir/OfficesReportTest.txt", list);
        reportGenerator.generateAllReports();
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<String> model_dates = new ArrayList<>();
        ArrayList<String> offices = new ArrayList<>();
        ArrayList<String> model_offices = new ArrayList<>();
        try (
                BufferedReader bufferedReader1 = new BufferedReader(new FileReader("testDir/DatesReportTest.txt"));
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader("testDir/model_dates.txt"));
                BufferedReader bufferedReader3 = new BufferedReader(new FileReader("testDir/OfficesReportTest.txt"));
                BufferedReader bufferedReader4 = new BufferedReader(new FileReader("testDir/model_offices.txt"))) {
            bufferedReader1.lines().forEach(dates::add);
            bufferedReader2.lines().forEach(model_dates::add);
            bufferedReader3.lines().forEach(offices::add);
            bufferedReader4.lines().forEach(model_offices::add);
        } catch (IOException e) {
            System.out.println("невозможно прочитать входной файл");
            e.printStackTrace();
        }
        Assertions.assertIterableEquals(model_dates, dates);
        Assertions.assertIterableEquals(model_offices, offices);
    }
}
