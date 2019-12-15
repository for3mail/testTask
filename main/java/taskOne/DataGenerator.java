package taskOne;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DataGenerator {

    private String fileName;
    private int totalTransactionsNumber;
    private StringBuilder stringBuilder;
    private RandomAccessFile stream;
    private FileChannel channel;
    private ByteBuffer buffer;
    private byte[] strBytes;
    private final BigDecimal MAX;
    private final BigDecimal MIN;
    private BigDecimal range;
    private BigDecimal transactionAmount;
    private int transactionNumber;
    private LocalDate startDate;

    public DataGenerator(String fileName, int totalTransactionsNumber) {
        MAX = new BigDecimal("100000.50");
        MIN = new BigDecimal("10000.12");
        range = MAX.subtract(MIN);
        this.totalTransactionsNumber = totalTransactionsNumber;
        this.fileName = fileName;
        startDate = LocalDate.of(LocalDate.now().getYear()-1, 1, 1);
        stringBuilder = new StringBuilder();
    }

    // создает один файл с транзакциями
    public void generateFile(ArrayList offices, int transactionNumber){

        try {
            System.out.println(fileName);
            stream = new RandomAccessFile(fileName, "rw");
            channel = stream.getChannel();
            this.transactionNumber = transactionNumber+1;
            for (int i = 0; i < totalTransactionsNumber; i++) {
                transactionAmount = MIN.add(range.multiply(new BigDecimal(Math.random())));
                transactionAmount = transactionAmount.setScale(2, BigDecimal.ROUND_FLOOR);
                stringBuilder.setLength(0);
                stringBuilder.append(startDate.plusDays((long)(Math.random()*365))) // учесть високосный год
                        .append(" ")
                        .append(LocalTime.of(0,0,0).plusSeconds((long)(Math.random()*86400)))
                        .append(" ")
                        .append(offices.get((int) (Math.random() * offices.size())))
                        .append(" ")
                        .append(this.transactionNumber++)
                        .append(" ")
                        .append(transactionAmount.toString())
                        .append("\n");
                strBytes = stringBuilder.toString().getBytes();
                buffer = ByteBuffer.allocate(strBytes.length);
                buffer.put(strBytes);
                buffer.flip();
                channel.write(buffer);
            }
            stream.close();
            channel.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

    }
}
