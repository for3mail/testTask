package common;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Arrays;

public class InputValidator {
    private String [] args;
    private int minSize;

    public InputValidator(String [] args) {
        this.args = args;
    }

    public void validate(int minSize, int [] checkAbilityToCreate, int [] checkExistence, int [] checkNumber) {
        this.minSize=minSize;
        Arrays.stream(checkAbilityToCreate).forEach(element -> {
            File file = new File(args[element]);
            try {
                file.createNewFile();
                file.delete();
            } catch (IOException e) {
                requestNewInput("Некорректное имя файла " + args[element]);
            }
        });

        Arrays.stream(checkExistence).forEach(element -> {
            try {
                if (!Files.exists(Paths.get(args[element])))
                    requestNewInput("Файл " + args[element] + " отсутствует");
                if (!(Files.size(Paths.get(args[element])) > 0))
                    requestNewInput("Файл " + args[element] + " пустой");
            } catch (InvalidPathException e) {
                requestNewInput("Проблема чтения файла " + args[element]);
            } catch (IOException e) {
                requestNewInput("Проблема чтения файла " + args[element]);
        }
        });

        Arrays.stream(checkNumber).forEach(element -> {
            if (!args[element].matches("\\d+"))
                requestNewInput("Количество транзакций указано неверно");
            if (BigInteger.valueOf(Integer.MAX_VALUE).compareTo(new BigInteger(args[element])) < 0)
                requestNewInput("транзакций должно быть не больше " + Integer.MAX_VALUE);
        });
    }

    public void requestNewInput(String msg){
        System.out.println(msg);
        System.exit(2);
/*        System.out.println(msg + " повторите ввод");
        ArrayList<String> arrayList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in, "UTF-8");
        String s= scanner.nextLine();
        int temp=0;
        for (String currentParametr : s.split(" ")) {
            arrayList.add(currentParametr);
            temp++;
        }
        args = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) args[i] = arrayList.get(i);
        if (args.length < minSize) requestNewInput("Мало входных данных");
        int [] temp1 = new int[args.length];
        for (int i = 0; i < temp1.length; i++) temp1[i]=i;
        validate(minSize,
                Arrays.copyOfRange(temp1, 2, args.length),
                Arrays.copyOfRange(temp1,0,1),
                Arrays.copyOfRange(temp1,1,2));*/
    }
}
