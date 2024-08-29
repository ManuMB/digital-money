package com.manumb.digital_money_service.business.accounts.transactions;

import com.manumb.digital_money_service.business.accounts.Account;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    private Double amount;
    private LocalDateTime transactionDate;


    public Transaction() {
    }

    public Transaction(Account account, Double amount, LocalDateTime transactionDate) {
        this.account = account;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Transaction(Long id, Account account, Double amount, LocalDateTime transactionDate) {
        this.id = id;
        this.account = account;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
