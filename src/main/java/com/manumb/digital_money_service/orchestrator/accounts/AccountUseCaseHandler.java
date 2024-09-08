package com.manumb.digital_money_service.orchestrator.accounts;

import com.manumb.digital_money_service.business.accounts.Account;
import com.manumb.digital_money_service.business.accounts.AccountService;
import com.manumb.digital_money_service.business.accounts.dto.ResponseGetBalanceAccount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccountUseCaseHandler implements AccountUseCaseOrchestrator{
    private final AccountService accountService;

    @Override
    public ResponseGetBalanceAccount getBalance(Long id) {
        Account account = accountService.findById(id);
        return new ResponseGetBalanceAccount(account.getBalance());
    }
}
