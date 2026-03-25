package main.java;

import java.math.BigDecimal;

public class Money {
    private final BigDecimal value;
    private final String currency;

    public Money(BigDecimal value, String currency) {
        if (value == null) {
            throw new IllegalArgumentException("Value must not be null.");
        }

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Value cannot be less than 0.");
        }

        if (currency == null || currency.isEmpty()) {
            throw new IllegalArgumentException("Currency must not be empty.");
        }

        this.value = value;
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    public Money add(Money other) {
        if (other == null) {
            throw new IllegalArgumentException("Money object cannot be null.");
        }

        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot add Money with different currencies");
        }

        return new Money(this.value.add(other.value), this.currency);
    }
}