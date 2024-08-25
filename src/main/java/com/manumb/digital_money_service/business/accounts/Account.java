package com.manumb.digital_money_service.business.accounts;

import com.manumb.digital_money_service.business.accounts.cards.Card;
import com.manumb.digital_money_service.business.users.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double balance;
    @Column(name = "user_id")
    private Long userId;


    public Account(Long id, Double balance) {
        this.id = id;
        this.balance = balance;
    }

    public Account(Long id, Double balance, Long userId) {
        this.id = id;
        this.balance = balance;
        this.userId = userId;
    }

    public Account() {
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
