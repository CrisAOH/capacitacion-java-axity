package main.java;

public class TransactionValidationResult {
    private final Transaction transaction;
    private final String errorMessage;

    private TransactionValidationResult(Transaction transaction, String errorMessage) {
        if (transaction == null && errorMessage == null) {
            throw new IllegalArgumentException("You must send at least one parameter.");
        }

        if (transaction != null && errorMessage != null) {
            throw new IllegalArgumentException("At least one parameter must be null.");
        }

        this.transaction = transaction;
        this.errorMessage = errorMessage;
    }

    public static TransactionValidationResult Success(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("You must send a transaction.");
        }

        return new TransactionValidationResult(transaction, null);
    }

    public static TransactionValidationResult Failure(String errorMessage) {
        if (errorMessage == null || errorMessage.isBlank()) {
            throw new IllegalArgumentException("You must send an error message.");
        }

        return new TransactionValidationResult(null, errorMessage);
    }

    public boolean isValid() {
        if (transaction == null) {
            return false;
        }

        return true;
    }

    public Transaction getTransaction() {
        if (transaction == null) {
            throw new IllegalArgumentException("The transaction is not valid.");
        }
        return transaction;
    }

    public String getErrorMessage() {
        if (errorMessage == null) {
            throw new IllegalArgumentException("No error present, result is valid");
        }
        return errorMessage;
    }
}