package com.manumb.digital_money_service.business.accounts;

import com.manumb.digital_money_service.business.accounts.cards.Card;
import com.manumb.digital_money_service.business.accounts.transactions.Transaction;
import com.manumb.digital_money_service.business.users.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double balance;
    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL)
    private List<Transaction> transactionsSent;
    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL)
    private List<Transaction> transactionsReceived;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Card> cards;


    public Account() {
    }

    public Account(Long id, Double balance) {
        this.id = id;
        this.balance = balance;
    }

    public Account(Long id, Double balance, List<Transaction> transactionsSent, List<Transaction> transactionsReceived, User user, List<Card> cards) {
        this.id = id;
        this.balance = balance;
        this.transactionsSent = transactionsSent;
        this.transactionsReceived = transactionsReceived;
        this.user = user;
        this.cards = cards;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Transaction> getTransactionsSent() {
        return transactionsSent;
    }

    public void setTransactionsSent(List<Transaction> transactionsSent) {
        this.transactionsSent = transactionsSent;
    }

    public List<Transaction> getTransactionsReceived() {
        return transactionsReceived;
    }

    public void setTransactionsReceived(List<Transaction> transactionsReceived) {
        this.transactionsReceived = transactionsReceived;
    }
}
