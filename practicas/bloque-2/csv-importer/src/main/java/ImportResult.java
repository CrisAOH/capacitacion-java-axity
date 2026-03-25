package main.java;

import java.util.List;

public class ImportResult {
    private final List<Transaction> transactions;
    private final List<String> errors;
    private final Money totalIn;
    private final Money totalOut;

    public ImportResult(List<Transaction> transactions, List<String> errors, Money totalIn, Money totalOut) {
        if (transactions == null) {
            throw new IllegalArgumentException("Transactions cannot be null.");
        }

        if (errors == null) {
            throw new IllegalArgumentException("Errors cannot be null.");
        }

        if (totalIn == null) {
            throw new IllegalArgumentException("TotalIn cannot be null.");
        }

        if (totalOut == null) {
            throw new IllegalArgumentException("TotalOut cannot be null.");
        }

        this.transactions = List.copyOf(transactions);
        this.errors = List.copyOf(errors);
        this.totalIn = totalIn;
        this.totalOut = totalOut;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<String> getErrors() {
        return errors;
    }

    public Money getTotalIn() {
        return totalIn;
    }

    public Money getTotalOut() {
        return totalOut;
    }
}
