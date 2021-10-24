package template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import template.BufferedReaderCallback;

public class Calculator {

    public int calcSum(String path) throws IOException {
//        return fileReadTemplate(path, new BufferedReaderCallback() {
//
//            @Override
//            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
//                int sum = 0;
//                String line = null;
//                while ((line = br.readLine()) != null) {
//                    sum += Integer.valueOf(line);
//                }
//                return sum;
//            }
//        });
        return lineReadTemplate(path, new LineCallback() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return value + Integer.valueOf(line);
            }
        }, 0);
    }

    public int calcMultiply(String path) throws IOException {
//        return fileReadTemplate(path, new BufferedReaderCallback() {
//
//            @Override
//            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
//                int result = 1;
//                String line = null;
//                while ((line = br.readLine()) != null) {
//                    result *= Integer.valueOf(line);
//                }
//                return result;
//            }
//        });
        return lineReadTemplate(path, new LineCallback() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return value * Integer.valueOf(line);
            }
        }, 1);
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

    public Integer lineReadTemplate(String filePath, LineCallback callback, int initVal) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            Integer result = initVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                result = callback.doSomethingWithLine(line, result);
            }
            return result;
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
