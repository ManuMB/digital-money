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
    public void saveAccount(Account account) {
        accountSqlRepository.save(account);
    }

    @Override
    public Account findById(Long id) {
        return accountSqlRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account with id " + id + " not found"));
    }

    @Override
    public Account findByAliasOrCvu(String identifier) {
        return accountSqlRepository.findByAliasOrCvu(identifier)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

    @Override
    public void updateBalance(Long accountId, Double newBalance) {
        accountSqlRepository.updateBalance(accountId, newBalance);
    }
}
