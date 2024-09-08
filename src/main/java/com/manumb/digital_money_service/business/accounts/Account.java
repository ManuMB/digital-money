package com.manumb.digital_money_service.business.accounts;

import com.manumb.digital_money_service.business.accounts.cards.Card;
import com.manumb.digital_money_service.business.accounts.transactions.Transaction;
import com.manumb.digital_money_service.business.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "accounts")
@Getter @Setter @AllArgsConstructor @RequiredArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String cvu;

    @Column(unique = true)
    private String alias;

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

}
