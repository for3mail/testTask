package common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/* Класс отвечает за генерацию одного файла с транзакциями*/
public class FileWithTransactions {
    private String fileName;
    private int totalNumberOfTransactions;
    private TransactionInputParameters transactionInputParameters;

    public String getFileName() {
        return fileName;
    }

    public int getTotalNumberOfTransactions() {
        return totalNumberOfTransactions;
    }

    public TransactionInputParameters getTransactionInputParameters() {
        return transactionInputParameters;
    }

    public FileWithTransactions(String fileName, int totalNumberOfTransactions, TransactionInputParameters transactionInputParameters) {
        this.fileName = fileName;
        this.totalNumberOfTransactions = totalNumberOfTransactions;
        this.transactionInputParameters = transactionInputParameters;
    }

    // Генарация и запись одного файла с транзакциями
    public void generateFile(){
        Transaction transaction = new Transaction();
        try (   BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))){
            for (int i = 0; i < totalNumberOfTransactions; i++) {
                transaction.generateRandomTransaction(transactionInputParameters);
                bufferedWriter.write(transaction.getTransactionToString());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
