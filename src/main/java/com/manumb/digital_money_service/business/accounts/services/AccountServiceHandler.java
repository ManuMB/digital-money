package com.manumb.digital_money_service.business.accounts.services;

import com.manumb.digital_money_service.business.accounts.Account;
import com.manumb.digital_money_service.business.accounts.AccountService;
import com.manumb.digital_money_service.business.exceptions.NotFoundException;
import com.manumb.digital_money_service.persistence.AccountSqlRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceHandler implements AccountService {
    private final AccountSqlRepository accountSqlRepository;

    public AccountServiceHandler(AccountSqlRepository accountSqlRepository) {
        this.accountSqlRepository = accountSqlRepository;
    }


    @Override
    public Account findById(Long id) {
        return accountSqlRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account with id " + id + " not found"));
    }
}
