import org.junit.jupiter.api.*;
import taskOne.FilesGenerator;

import java.util.ArrayList;
import java.util.Arrays;


public class FilesGeneratorTest {
    private FilesGenerator filesGenerator;

    @BeforeEach
    void filesGeneratorInit(){
        String [] args = new String[4];
        args[0]= "officesTest.txt";
        args[1]= "1";
        filesGenerator = new FilesGenerator(args);
    }
    @Test
    void parseOfficesListTest(){
        ArrayList<String> expectedList = new ArrayList<String>(Arrays.asList("a1","b2","c3", "d4"));
        Assertions.assertIterableEquals(expectedList, filesGenerator.parseOfficesList());

    }
}
