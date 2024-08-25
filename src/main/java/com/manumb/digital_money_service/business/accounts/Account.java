package com.manumb.digital_money_service.business.accounts;

import com.manumb.digital_money_service.business.users.User;
import jakarta.persistence.*;

@Entity
@Table(name = "Accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double balance;
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private User user;

    public Account(Long id, Double balance) {
        this.id = id;
        this.balance = balance;
    }

    public Account() {
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
}
