package taskOne;

import common.FileWithTransactions;
import common.TransactionInputParameters;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*Класс отвечает за поочередное создание файлов с транзакциями*/

public class FilesGenerator {
    private String args [];
    private int numberOfFiles;
    private int remainder;
    private int numberPerOneFile;
    private FileWithTransactions fileWithTransactions [];

    public FilesGenerator(String[] args) {
        fileWithTransactions = new FileWithTransactions[args.length-2];
        this.args = args;
        numberOfFiles = args.length-2;
        int totalNumberOfTransactions = Integer.parseInt(args[1]);
        remainder= totalNumberOfTransactions % numberOfFiles;
        numberPerOneFile = (totalNumberOfTransactions-remainder)/numberOfFiles;
    }

    void generateAllFiles(TransactionInputParameters transactionInputParameters){
        for (int i = 0; i < numberOfFiles; i++) {
            if (i==numberOfFiles-1) numberPerOneFile += remainder;
            fileWithTransactions [i] = new FileWithTransactions(args[i+2], numberPerOneFile, transactionInputParameters);
        }
        Stream<FileWithTransactions> stream = Arrays.stream(fileWithTransactions);
        stream.parallel().forEach(FileWithTransactions::generateFile);
    }

    public ArrayList <String> parseOfficesList(){
        ArrayList<String> offices = new ArrayList<String>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(args[0]))){
            offices = reader.lines().collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e){
            e.printStackTrace();
        }
        return offices;
    }
}
