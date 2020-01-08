import common.FileWithTransactions;
import common.Transaction;
import common.TransactionInputParameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

public class FileWithTransactionsTest {
    private FileWithTransactions fileWithTransactions;
    private TransactionInputParameters transactionInputParameters;
    private Transaction transaction;
    private String [] currentRecords;

    @Test
    void generateFileTest() {
        String fileName = "testFileName";
        currentRecords = new String[5];
        HashMap<Integer, Integer> uniqTransactions = new HashMap<>();
        transaction = new Transaction();
        ArrayList<String> officesArray = new ArrayList<>(Arrays.asList("office1","office2","office3", "office4"));
        transactionInputParameters = new TransactionInputParameters(
                LocalDate.of(2019,01,01),
                365,
                BigDecimal.valueOf(10000.12),
                BigDecimal.valueOf(100000.50),
                officesArray);
        fileWithTransactions = new FileWithTransactions(fileName, 100, transactionInputParameters);

        fileWithTransactions.generateFile();
        Assertions.assertTrue(Files.exists(Paths.get("testFileName")));


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            Stream<String> streamOfStrings = bufferedReader.lines();
            streamOfStrings.forEach(s -> {
                currentRecords = s.split(" ", 5);

                Assertions.assertTrue(LocalDate.parse(currentRecords[0]).toEpochDay() >=17897
                                && LocalDate.parse(currentRecords[0]).toEpochDay()<18262);
                // проверить уникальность транзакций
                Assertions.assertTrue(new BigDecimal(currentRecords[4]).compareTo(new BigDecimal(10000.12))>0);
                Assertions.assertTrue(new BigDecimal(currentRecords[4]).compareTo(new BigDecimal(100000.50))<0);
            });
        } catch (Exception e) {
            System.out.println("Test failed: ");
            e.printStackTrace();
        }
    }
}
