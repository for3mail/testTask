package common;


import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/* Класс отвечает за генерацию одного файла с транзакциями*/

public class FileWithTransactions {
    private String fileName;
    private int totalNumberOfTransactions;
    private int startNumber;

    // Генарация и запись одного файла с транзакциями
    public void generateFile(int totalNumberOfTransactions, String fileName, TransactionInputParameters transactionInputParameters){
        this.fileName= fileName;
        this.totalNumberOfTransactions = totalNumberOfTransactions;
        ByteBuffer buffer;
        byte[] strBytes;
        Transaction transaction = new Transaction();
        try (   RandomAccessFile stream = new RandomAccessFile(fileName, "rw");
                FileChannel channel = stream.getChannel()){

            for (int i = 0; i < totalNumberOfTransactions; i++) {
                transaction.generateRandomTransaction(transactionInputParameters);
                strBytes = transaction.getTransactionToString().getBytes();
                buffer = ByteBuffer.allocate(strBytes.length);
                buffer.put(strBytes);
                buffer.flip();
                channel.write(buffer);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
