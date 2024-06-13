package main.java.entity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CustomerCSVReader {

    private String path;

    // CSV path
    public CustomerCSVReader(String path) {
        this.path = path;
    }

    // CSV file method - should I place this in the main file?
    public void readCSV() {
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                System.out.println("Test output: " + values[0]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
