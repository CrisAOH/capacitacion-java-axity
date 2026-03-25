package main.java;

import java.io.IOException;

public class Main {
    static void main(String[] args) throws IOException {
        Importer importer = new Importer();
        ImportResult result = importer.importProcess(args[0]);

        System.out.println("Correct: " + result.getTransactions().size());
        System.out.println("Incorrect: " + result.getErrors().size());
        System.out.println("In: " + result.getTotalIn().getValue().toString());
        System.out.println("Out: " + result.getTotalOut().getValue().toString());

        for (int i = 0; i < result.getErrors().size(); i++) {
            System.out.println(result.getErrors().get(i));
        }
    }
}