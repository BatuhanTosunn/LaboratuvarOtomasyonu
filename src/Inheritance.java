import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Inheritance extends FileOperations{
    public void reader(ArrayList<String[]> list) throws IOException {
        File file = new File("src/chems.txt");
        FileReader read1 = new FileReader(file);
        BufferedReader read2 = new BufferedReader(read1);

        String line;
        while ((line = read2.readLine()) != null) {
            if (!(line.isEmpty())) {
                String[] adding;
                adding = line.split("/");
                list.add(adding);
            }
        }
    }
}
