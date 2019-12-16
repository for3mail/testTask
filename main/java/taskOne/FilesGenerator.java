package taskOne;

import common.FileWithTransactions;
import common.TransactionInputParameters;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FilesGenerator {
    private String args [];
    private List<String> offices;
    private int numberOfFiles;
    private int totalNumberOfTransactions;
    private int remainder;
    private int numberPerOneFile;
    FileWithTransactions fileWithTransactions [];

    public FilesGenerator(String[] args) {
        fileWithTransactions = new FileWithTransactions[args.length-2];
        this.args = args;
        numberOfFiles = args.length-2;
        totalNumberOfTransactions = Integer.parseInt(args[1]);
        remainder= totalNumberOfTransactions % numberOfFiles;
        numberPerOneFile = (totalNumberOfTransactions-remainder)/numberOfFiles;
    }

    public void generateAllFiles(TransactionInputParameters transactionInputParameters){
        for (int i = 0; i < numberOfFiles; i++) {
            if (i==numberOfFiles-1) numberPerOneFile += remainder;
            fileWithTransactions [i] = new FileWithTransactions();
            fileWithTransactions[i].generateFile(numberPerOneFile, args[i+2], transactionInputParameters);
        }
    }

    List <String> parseOfficesList(){
        offices = new ArrayList();
        try {
            Path path = Paths.get(args[0]);
            BufferedReader reader = Files.newBufferedReader(path);
            String line;
            while((line = reader.readLine()) != null) {
                offices.add(line);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return offices;
    }

}
