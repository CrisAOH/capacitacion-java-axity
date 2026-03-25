package main.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Importer {
    public ImportResult importProcess(String fileName) throws IOException {
        Path file = pathTraversalSecurity(fileName);

        try(BufferedReader reader = Files.newBufferedReader(file)) {
            List<Transaction> transactions = new ArrayList<>();
            List<String> errors = new ArrayList<>();
            String line;
            int lineNumber = 1;
            Money totalIn = null;
            Money totalOut = null;
            TransactionValidationResult result;

            reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                lineNumber = lineNumber + 1;
                result = processLine(line, lineNumber);

                if (!result.isValid()) {
                    errors.add(result.getErrorMessage());
                    continue;
                }

                Transaction transaction = result.getTransaction();
                transactions.add(transaction);
                if (transaction.getType() == TxType.IN){
                    totalIn = totalAccumulation(totalIn, transaction);
                } else if (transaction.getType() == TxType.OUT) {
                    totalOut = totalAccumulation(totalOut, transaction);
                }
            }

            if (totalIn == null) {
                totalIn = new Money(new BigDecimal(0), "MXN");
            }

            if (totalOut == null) {
                totalOut  = new Money(new BigDecimal(0), "MXN");
            }

            return new ImportResult(transactions, errors, totalIn, totalOut);
        }
    }

    private Money totalAccumulation(Money total, Transaction transaction) {
        if (total == null) {
            return transaction.getAmount();
        }

        return total.add(transaction.getAmount());
    }

    private Path pathTraversalSecurity(String fileName) throws IOException {
        String baseDir = System.getenv("IMPORT_BASE_DIR");

        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("The file name must not be empty.");
        }

        if (baseDir == null || baseDir.isBlank()) {
            throw new IllegalArgumentException("The IMPORT_BASE_DIR env variable is not defined.");
        }

        Path basePath = Paths.get(baseDir).toRealPath();
        Path filePath = basePath.resolve(fileName).normalize();

        if (!Files.isRegularFile(filePath)) {
            throw new FileNotFoundException("The file does not exist.");
        }

        if (!filePath.startsWith(basePath)) {
            throw new SecurityException("Path traversal detected.");
        }

        return filePath;
    }

    private TransactionValidationResult processLine(String csvLine, int lineNumber) {
        try {
            if (csvLine == null || csvLine.isBlank()) {
                throw new IllegalArgumentException("CSV line is empty.");
            }

            String[] fields = csvLine.split(",");

            if (fields.length != 5) {
                throw new IllegalArgumentException("The line must have 5 fields.");
            }

            int id = Integer.parseInt(fields[0].trim());
            TxType type = Enum.valueOf(TxType.class, fields[1].toUpperCase().trim());
            BigDecimal amount = new BigDecimal(fields[2].trim());
            String currency = fields[3].toUpperCase().trim();
            String description = fields[4].trim();
            Money money = new Money(amount, currency);
            Transaction transaction = new Transaction(id, type, money, description);

            return TransactionValidationResult.Success(transaction);
        } catch (Exception e) {
            String message = "Line " + lineNumber +
                    ": " +
                    e.getMessage();

            return TransactionValidationResult.Failure(message);
        }
    }
}
