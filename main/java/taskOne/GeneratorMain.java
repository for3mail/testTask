package taskOne;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GeneratorMain {
    public static void main(String[] args) {

        String line;
        ArrayList<String> offices = new ArrayList();
        int firstNumberOfTransactionInFile;

        int numberOfFiles = args.length-2;
        int totalNumberOfTransactions = Integer.parseInt(args[1]);
        int remainder= totalNumberOfTransactions % numberOfFiles;
        int numberPerOneFile = (totalNumberOfTransactions-remainder)/numberOfFiles;
        DataGenerator [] dataGenerators = new DataGenerator[numberOfFiles];

        try {
            Path path = Paths.get("offices.txt");
            BufferedReader reader = Files.newBufferedReader(path);
            while((line = reader.readLine()) != null) {
                offices.add(line);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        for (int i = 0; i < numberOfFiles; i++) {
            firstNumberOfTransactionInFile = numberPerOneFile;
            if (i==numberOfFiles-1) numberPerOneFile += remainder;

            dataGenerators[i] = new DataGenerator(args[i+2], numberPerOneFile);
            dataGenerators[i].generateFile(offices, i*firstNumberOfTransactionInFile);
        }
    }
}
