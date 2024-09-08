package com.manumb.digital_money_service.orchestrator.accounts;

import com.manumb.digital_money_service.business.accounts.Account;
import com.manumb.digital_money_service.business.accounts.AccountService;
import com.manumb.digital_money_service.business.accounts.dto.RequestUpdateAccountInfo;
import com.manumb.digital_money_service.business.accounts.dto.ResponseGetAccountInfo;
import com.manumb.digital_money_service.business.accounts.dto.ResponseGetBalanceAccount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccountUseCaseHandler implements AccountUseCaseOrchestrator {

    private final AccountService accountService;

    @Override
    public void updateAlias(Long id, RequestUpdateAccountInfo request) {
        accountService.updateAlias(id, request.alias());
    }

    @Override
    public ResponseGetAccountInfo getAccountInfo(Long id) {
        Account account = accountService.findById(id);
        return new ResponseGetAccountInfo(
                account.getCvu(),
                account.getAlias()
        );
    }

    @Override
    public ResponseGetBalanceAccount getBalance(Long id) {
        Account account = accountService.findById(id);
        return new ResponseGetBalanceAccount(account.getBalance());
    }
}
