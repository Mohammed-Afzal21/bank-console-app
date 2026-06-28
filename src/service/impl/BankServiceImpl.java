package service.impl;

import domain.Account;
import domain.Customer;
import domain.Transaction;
import domain.Type;
import exception.AccountNotFoundException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import repository.AccountRepository;
import repository.CustomerRepository;
import repository.TransactionRepository;
import service.BankService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepository = new AccountRepository();
    private final TransactionRepository transactionRepository = new TransactionRepository();
    private final CustomerRepository customerRepository = new CustomerRepository();

    @Override
    public String openAccount(String name, String email, String accountType) {
        String customerId = UUID.randomUUID().toString();

        Customer customer = new Customer(customerId, name, email);
        customerRepository.save(customer);

        String accountNumber = getAccountNumber();

        Account account = new Account(accountNumber, accountType, 0.0, customerId);

        accountRepository.saveAccount(account);

        return accountNumber;
    }

    @Override
    public List<Account> listAccounts() {
        return accountRepository.findAll().stream()
                .sorted(Comparator.comparing(Account::getAccountNumber))
                .toList();
    }

    @Override
    public void deposit(String accountNumber, double amount, String note) {

        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountNumber));
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than 0");
        }
        account.setBalance(account.getBalance() + amount);

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                Type.DEPOSIT,
                account.getAccountNumber(),
                amount,
                LocalDateTime.now(),
                note
        );

        transactionRepository.add(transaction);
    }

    @Override
    public void withdraw(String accountNumber, double amount, String note) {

        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountNumber));

        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than 0");
        }

        if (account.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                Type.WITHDRAW,
                account.getAccountNumber(),
                amount,
                LocalDateTime.now(),
                note
        );

        transactionRepository.add(transaction);
    }

    @Override
    public void transfer(String fromAcc, String toAcc, double amount, String note) {

        if (fromAcc.equals(toAcc)) {
            throw new IllegalArgumentException("Cannot transfer to same account");
        }
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than 0");
        }
        Account from = accountRepository.findByNumber(fromAcc)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + fromAcc));

        Account to = accountRepository.findByNumber(toAcc)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + toAcc));

        if (from.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient balance");
        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        Transaction fromTransaction = new Transaction(
                UUID.randomUUID().toString(),
                Type.TRANSFER_OUT,
                from.getAccountNumber(),
                amount,
                LocalDateTime.now(),
                note
        );

        transactionRepository.add(fromTransaction);

        Transaction toTransaction = new Transaction(
                UUID.randomUUID().toString(),
                Type.TRANSFER_IN,
                to.getAccountNumber(),
                amount,
                LocalDateTime.now(),
                note
        );

        transactionRepository.add(toTransaction);
    }

    @Override
    public List<Transaction> getStatement(String account) {
        return transactionRepository.findByAccount(account)
                .stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp))
                .toList();
    }

    @Override
    public List<Account> searchAccountsByCustomerName(String q) {

        String query = (q == null) ? "" : q.toLowerCase();

        return customerRepository.findAll().stream()
                .filter(c -> c.getName().toLowerCase().contains(query))
                .flatMap(c -> accountRepository.findByCustomerId(c.getId()).stream())
                .sorted(Comparator.comparing(Account::getAccountNumber))
                .toList();
    }

    private String getAccountNumber() {
        int size = accountRepository.findAll().size() + 1;
        return String.format("Ac%06d", size);
    }
}