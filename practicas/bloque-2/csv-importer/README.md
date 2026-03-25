# Secure CSV Importer

Hands-on lab where you’ll build a robust and secure CSV importer from scratch, applying principles of OOP, functional design, secure development, and CI/CD. No frameworks, no external dependencies: just pure Java and professional best practices.

## Objective

Build a Java console application that reads CSV files from a specified directory, validates and processes financial transactions, and generates a detailed summary of valid and invalid records with totals for income and expenses.

## Acceptance Criteria

- Implement path validation to prevent path traversal
- Apply object-oriented design using the Money, Transaction and Importer classes
- Process CSV files using a functional pipeline: parse → validate → accumulate
- Generate a summary: valid/invalid records and IN/OUT
  totals
- Include testing without JUnit and CI/CD using GitHub Actions
- Apply Checkstyle for code quality
