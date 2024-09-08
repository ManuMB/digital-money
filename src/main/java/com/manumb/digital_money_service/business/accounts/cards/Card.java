package com.manumb.digital_money_service.business.accounts.cards;

import com.manumb.digital_money_service.business.accounts.Account;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "cards")
@Getter @Setter @AllArgsConstructor @RequiredArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_holder")
    private String cardHolder;

    @Column(name = "card_number")
    private String cardNumber;

    private String cvv;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
