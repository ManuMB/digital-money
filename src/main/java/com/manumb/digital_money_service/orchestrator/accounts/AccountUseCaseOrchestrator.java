package com.manumb.digital_money_service.orchestrator.accounts;

import com.manumb.digital_money_service.business.accounts.dto.RequestUpdateAccountInfo;
import com.manumb.digital_money_service.business.accounts.dto.ResponseGetAccountInfo;
import com.manumb.digital_money_service.business.accounts.dto.ResponseGetBalanceAccount;

public interface AccountUseCaseOrchestrator {
    void updateAlias(Long id, RequestUpdateAccountInfo request);
    ResponseGetAccountInfo getAccountInfo(Long id);
    ResponseGetBalanceAccount getBalance(Long id);
}
