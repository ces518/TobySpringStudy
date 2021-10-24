package template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import template.BufferedReaderCallback;

public class Calculator {

    public int calcSum(String path) throws IOException {
        return fileReadTemplate(path, new BufferedReaderCallback() {

            @Override
            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
                int sum = 0;
                String line = null;
                while ((line = br.readLine()) != null) {
                    sum += Integer.valueOf(line);
                }
                return sum;
            }
        });
    }

    public int calcMultiply(String path) throws IOException {
        return fileReadTemplate(path, new BufferedReaderCallback() {
            @Override
            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
                int result = 1;
                String line = null;
                while ((line = br.readLine()) != null) {
                    result *= Integer.valueOf(line);
                }
                return result;
            }
        });
    }

    public Integer fileReadTemplate(String filePath, BufferedReaderCallback callback)
        throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            return callback.doSomethingWithReader(br);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
