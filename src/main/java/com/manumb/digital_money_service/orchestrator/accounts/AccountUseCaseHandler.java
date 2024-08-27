package com.manumb.digital_money_service.orchestrator.accounts;

import com.manumb.digital_money_service.business.accounts.Account;
import com.manumb.digital_money_service.business.accounts.AccountService;
import com.manumb.digital_money_service.business.accounts.dto.ResponseGetBalanceAccount;
import org.springframework.stereotype.Component;

@Component
public class AccountUseCaseHandler implements AccountUseCaseOrchestrator{
    private final AccountService accountService;

    public AccountUseCaseHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public ResponseGetBalanceAccount getBalance(Long id) {
        Account account = accountService.findById(id);
        return new ResponseGetBalanceAccount(account.getBalance());
    }
}
