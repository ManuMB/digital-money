package com.manumb.digital_money_service.orchestrator.accounts;

import com.manumb.digital_money_service.business.accounts.dto.ResponseGetBalanceAccount;

public interface AccountUseCaseOrchestrator {
    ResponseGetBalanceAccount getBalance(Long id);
}
