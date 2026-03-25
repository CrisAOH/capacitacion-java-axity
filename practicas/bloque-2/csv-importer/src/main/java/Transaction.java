package main.java;

public class Transaction {
    private final int id;
    private final TxType type;
    private final Money amount;
    private final String description;

    public Transaction(int id, TxType type, Money amount, String description) {
        if (id <= 0) {
            throw new IllegalArgumentException("The transaction's ID cannot be 0 or less.");
        }

        if (type == null) {
            throw new IllegalArgumentException("A type for the transaction must be specified.");
        }

        if (amount == null) {
            throw new IllegalArgumentException("The transaction must have a specified amount.");
        }

        if (description == null || description.isBlank()) {
            description = "";
        }

        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public TxType getType() {
        return type;
    }

    public Money getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}