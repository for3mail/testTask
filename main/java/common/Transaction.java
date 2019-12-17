package common;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

    public class Transaction {
    private StringBuilder stringBuilder;
    private String transactionToString;
    private static int transactionNumber;
    private BigDecimal transactionAmount;
    private LocalDate localDate;
    private LocalTime localTime;
    private String office;

    public Transaction() {
        stringBuilder = new StringBuilder();
    }

        public void setTransactionAmount(BigDecimal transactionAmount) {
            this.transactionAmount = transactionAmount;
        }

        public void setLocalDate(LocalDate localDate) {
            this.localDate = localDate;
        }

        public void setOffice(String office) {
            this.office = office;
        }

        public BigDecimal getTransactionAmount() {
            return transactionAmount;
        }

        public LocalDate getLocalDate() {
            return localDate;
        }

        public String getOffice() {
            return office;
        }

        // Генерация случайной транзакции
    synchronized Transaction generateRandomTransaction(TransactionInputParameters inputParameters){
        localDate = inputParameters.getStartDate().plusDays((long)(Math.random()*inputParameters.getDateRange()));
        localTime = LocalTime.of(0,0,0).plusSeconds((long)(Math.random()*86400));
        office = inputParameters.getOffices().get((int) (Math.random() * inputParameters.getOffices().size()));
        transactionAmount = inputParameters.getMIN().add(inputParameters.getRANGE().multiply(new BigDecimal(Math.random())));
        transactionAmount = transactionAmount.setScale(2, BigDecimal.ROUND_FLOOR);

        stringBuilder.setLength(0);
        stringBuilder
                .append(localDate)
                .append(" ")
                .append(localTime)
                .append(" ")
                .append(office)
                .append(" ")
                .append(incrementTransactionNumber())
                .append(" ")
                .append(transactionAmount.toString())
                .append("\n");
        transactionToString = stringBuilder.toString();
        return this;
    };

    String getTransactionToString() {
        return transactionToString;
    }

    // учет порядкового номера транзакции
    private synchronized int incrementTransactionNumber(){
        return ++transactionNumber;
    }
}
